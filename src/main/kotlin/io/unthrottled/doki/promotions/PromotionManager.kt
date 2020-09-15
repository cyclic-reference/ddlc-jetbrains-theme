package io.unthrottled.doki.promotions

import com.google.gson.GsonBuilder
import com.intellij.ide.plugins.PluginManagerCore
import com.intellij.openapi.application.ApplicationNamesInfo
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.extensions.PluginId
import com.intellij.util.io.exists
import io.unthrottled.doki.assets.AssetCategory
import io.unthrottled.doki.assets.AssetManager
import io.unthrottled.doki.assets.AssetManager.ASSETS_SOURCE
import io.unthrottled.doki.assets.LocalStorageService.createDirectories
import io.unthrottled.doki.config.ThemeConfig
import io.unthrottled.doki.integrations.RestClient.performGet
import io.unthrottled.doki.stickers.StickerLevel
import io.unthrottled.doki.util.runSafely
import io.unthrottled.doki.util.runSafelyWithResult
import io.unthrottled.doki.util.toOptional
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.StandardOpenOption
import java.time.Duration
import java.time.Instant
import java.util.Optional
import java.util.UUID

const val MOTIVATOR_PLUGIN_ID = "zd.zero.waifu-motivator-plugin"

object PromotionManager {

  private val log = Logger.getInstance(PromotionManager::class.java)

  private val gson = GsonBuilder()
    .setPrettyPrinting()
    .create()

  private var initialized = false

  private val ledgerPath = AssetManager.constructGlobalAssetPath(
    AssetCategory.PROMOTION,
    "ledger.json"
  ).orElseGet {
    AssetManager.constructLocalAssetPath(
      AssetCategory.PROMOTION,
      "ledger.json"
    )
  }

  private val lockPath = AssetManager.constructGlobalAssetPath(
    AssetCategory.PROMOTION,
    "lock.json"
  ).orElseGet {
    AssetManager.constructLocalAssetPath(
      AssetCategory.PROMOTION,
      "lock.json"
    )
  }

  private val promotionLedger: PromotionLedger =
    if (ledgerPath.exists()) {
      readLedger()
    } else {
      PromotionLedger(UUID.randomUUID(), mutableMapOf())
    }

  private fun readLedger(): PromotionLedger {
    return try {
      Files.newInputStream(ledgerPath)
        .use {
          gson.fromJson(
            InputStreamReader(it, StandardCharsets.UTF_8),
            PromotionLedger::class.java
          )
        }
    } catch (e: Throwable) {
      log.warn("Unable to read promotion ledger for raisins.", e)
      PromotionLedger(UUID.randomUUID(), mutableMapOf())
    }
  }

  fun registerPromotion(newVersion: String) {
    if (initialized.not()) {
      promotionRegistry(newVersion)
    }
  }

  private fun promotionRegistry(newVersion: String) {
    val versionInstallDates = promotionLedger.versionInstallDates
    if (versionInstallDates.containsKey(newVersion).not()) {
      versionInstallDates[newVersion] = Instant.now()
      persistLedger()
    } else {
//      val latestInstallDate = versionInstallDates[newVersion]!!
//      if (Duration.between(latestInstallDate, Instant.now()).toDays() > 2) {
      setupPromotion()
//      }
    }
  }

  private fun setupPromotion() {
    if (isMotivatorInstalled().not() && shouldPromote() && isOnline()) {
      try {
        if (acquireLock()) {
          MotivatorPluginPromotion {
            // mark promoted
          }
        }
      } finally {
        releaseLock()
      }
    }
  }

  private fun isOnline(): Boolean =
    performGet("$ASSETS_SOURCE/misc/am-i-online.txt")
      .map { it.trim() == "yes" }
      .orElse(false)

  private val id: String
    get() = ApplicationNamesInfo.getInstance().fullProductNameWithEdition

  private fun releaseLock() {
    if (holdingLock()) {
      breakLock()
    }
  }

  private fun holdingLock(): Boolean =
    readLock()
      .map { it.lockedBy == id }
      .orElse(false)

  private fun acquireLock(): Boolean =
    when {
      Files.notExists(lockPath) -> lockPromotion()
      canBreakLock() -> breakAndLockPromotion()
      else -> false
    }

  private fun canBreakLock(): Boolean =
    readLock()
      .map {
        it.lockedBy == id || Duration.between(
          it.lockedDate, Instant.now()
        ).toHours() > 1
      }
      .orElse(true)

  private fun breakAndLockPromotion(): Boolean =
    if (breakLock()) {
      lockPromotion()
    } else {
      false
    }

  private fun breakLock(): Boolean =
    if (Files.exists(lockPath)) {
      runSafelyWithResult({
        Files.delete(lockPath)
        true
      }) {
        log.warn("Unable to remove previous lock for raisins", it)
        false
      }
    } else {
      true
    }

  private fun lockPromotion(): Boolean =
    writeLock(Lock(id, Instant.now()))

  private fun readLock(): Optional<Lock> =
    runSafelyWithResult({
      Files.newInputStream(lockPath)
        .use {
          gson.fromJson(
            InputStreamReader(it, StandardCharsets.UTF_8),
            Lock::class.java
          )
        }.toOptional()
    }) {
      log.warn("Unable to read promotion ledger for raisins.", it)
      Optional.empty()
    }

  private fun writeLock(lock: Lock): Boolean =
    runSafelyWithResult({
      Files.newBufferedWriter(lockPath, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)
        .use {
          it.write(gson.toJson(lock))
        }
      true
    }) {
      log.warn("Unable to write promotion ledger for raisins.", it)
      false
    }

  // todo: has been promoted as well
  private fun shouldPromote(): Boolean =
    ThemeConfig.instance.currentStickerLevel == StickerLevel.ON

  private fun isMotivatorInstalled(): Boolean =
    PluginManagerCore.isPluginInstalled(
      PluginId.getId(MOTIVATOR_PLUGIN_ID)
    )

  private fun persistLedger() {
    if (ledgerPath.exists().not()) {
      createDirectories(ledgerPath)
    }

    try {
      Files.newBufferedWriter(
        ledgerPath,
        StandardOpenOption.CREATE,
        StandardOpenOption.TRUNCATE_EXISTING
      ).use {
        it.write(
          gson.toJson(promotionLedger)
        )
      }
    } catch (e: Throwable) {
      log.warn("Unable to persist ledger for raisins", e)
    }
  }
}

data class Lock(
  val lockedBy: String,
  val lockedDate: Instant
)

data class PromotionLedger(
  val user: UUID,
  val versionInstallDates: MutableMap<String, Instant>
)