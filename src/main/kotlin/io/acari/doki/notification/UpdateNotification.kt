package io.acari.doki.notification

import com.intellij.ide.plugins.PluginManagerCore.*
import com.intellij.notification.*
import com.intellij.openapi.project.Project

val UPDATE_MESSAGE: String = """
      What's New?<br>
      <ul>
        <li>Added 5 new themes!
            <ul>
                <li>Re:Zero - Emilia (light/dark)</li>
                <li>DanganRonpa - Mioda Ibuki (light/dark)</li>
                <li>Hatsune Miku (dark)</li>
            </ul>
        </li>
        <li>Fixed all reported exceptions.</li>
      </ul>
      <br>Please see the <a href="https://github.com/Unthrottled/doki-theme-jetbrains/blob/master/changelog/CHANGELOG.md">Changelog</a> for more details.
      <br>
      Thanks again for downloading <b>The Doki Theme</b>! •‿•<br>
""".trimIndent()

const val CURRENT_VERSION = "7.0.2"

object UpdateNotification {

  private val notificationManager by lazy {
    SingletonNotificationManager(
      NotificationGroup("Doki Updates",
      NotificationDisplayType.STICKY_BALLOON, true),
      NotificationType.INFORMATION)
  }

  fun sendMessage(
    title: String,
    message: String,
    project: Project? = null
  ) {
    notificationManager.notify(
      title,
      message,
      project = project,
      listener = NotificationListener.URL_OPENING_LISTENER
    )
  }

  fun display(project: Project) {
    val pluginName =
      getPlugin(
      getPluginOrPlatformByClassName(UpdateNotification::class.java.canonicalName)
      )?.name
    notificationManager.notify(
      "$pluginName updated to v${CURRENT_VERSION}",
      UPDATE_MESSAGE,
      project,
      NotificationListener.URL_OPENING_LISTENER
    )
  }

  fun displayRestartMessage(){
    notificationManager.notify(
      "Please restart your IDE",
      "In order for the change to take effect, please restart your IDE. Thanks! ~"
    )
  }

    fun displayFileColorInstallMessage() {
      notificationManager.notify(
        "File Colors Installed",
        """File colors will remain in your IDE after uninstalling the plugin.
          |To remove them, un-check this action or remove them at "Settings -> Appearance -> File Colors". 
        """.trimMargin()
      )

    }

    fun displayAnimationInstallMessage() {
      notificationManager.notify(
        "Theme Transition Animation Enabled",
        """The animations will remain in your IDE after uninstalling the plugin.
          |To remove them, un-check this action or remove them at "Help -> Find Action -> ide.intellij.laf.enable.animation". 
        """.trimMargin()
      )
    }
}