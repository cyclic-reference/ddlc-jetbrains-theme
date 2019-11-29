package io.acari.doki.icon

import com.intellij.ui.ColorUtil.toHex
import com.intellij.ui.JBColor.namedColor
import com.intellij.util.SVGLoader
import org.w3c.dom.Element
import java.awt.Color
import java.net.URL

class ColorPatcher(
  private val otherColorPatcherProvider: (URL?) -> (Element) -> Unit = { {} }
) : SVGLoader.SvgElementColorPatcherProvider {
  override fun forURL(url: URL?): SVGLoader.SvgElementColorPatcher {
    val self = this
    val otherPatcher = otherColorPatcherProvider(url)
    return object : SVGLoader.SvgElementColorPatcher {
      override fun patchColors(svg: Element) {
        self.patchColors(svg, otherPatcher)
      }

      override fun digest(): ByteArray? {
        return null
      }
    }
  }

  fun patchColors(
    svg: Element,
    otherPatcher: (Element) -> Unit
  ) {
    otherPatcher(svg)
    patchChildren(
      svg,
      otherPatcher
    )
  }

  private fun patchChildren(svg: Element, otherPatcher: (Element) -> Unit) {
    when (svg.getAttribute("accentTint")) {
      "fill" -> svg.setAttribute("fill", getAccentColor())
      "stroke" -> svg.setAttribute("stroke", getAccentColor())
      "both" -> {
        val accentColor = getAccentColor()
        svg.setAttribute("stroke", accentColor)
        svg.setAttribute("fill", accentColor)
      }
    }

    val nodes = svg.childNodes
    val length = nodes.length
    for (i in 0 until length) {
      val item = nodes.item(i)
      if (item is Element) {
        patchColors(item, otherPatcher)
      }
    }
  }

  private fun getAccentColor() =
    "#${toHex(namedColor("Doki.Accent.color", Color.CYAN))}"
}