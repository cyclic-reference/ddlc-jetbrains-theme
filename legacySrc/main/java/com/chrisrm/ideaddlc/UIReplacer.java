/*
 *  The MIT License (MIT)
 *
 *  Copyright (c) 2019 Chris Magnussen and Elior Boukhobza
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 *
 *
 */

package com.chrisrm.ideaddlc;

import com.chrisrm.ideaddlc.ui.MTActionButtonLook;
import com.chrisrm.ideaddlc.ui.MTNavBarUI;
import com.chrisrm.ideaddlc.ui.MTScrollUI;
import com.chrisrm.ideaddlc.utils.MTUI;
import com.chrisrm.ideaddlc.utils.StaticPatcher;
import com.intellij.codeInsight.lookup.impl.LookupCellRenderer;
import com.intellij.ide.actions.Switcher;
import com.intellij.ide.navigationToolbar.ui.NavBarUIManager;
import com.intellij.ide.plugins.PluginManagerConfigurableNew;
import com.intellij.openapi.actionSystem.ex.ActionButtonLook;
import com.intellij.openapi.options.newEditor.SettingsTreeView;
import com.intellij.openapi.util.SystemInfo;
import com.intellij.openapi.util.registry.Registry;
import com.intellij.openapi.wm.impl.status.MemoryUsagePanel;
import com.intellij.ui.CaptionPanel;
import com.intellij.ui.ColorUtil;
import com.intellij.ui.Gray;
import com.intellij.ui.JBColor;
import com.intellij.ui.SimpleTextAttributes;
import com.intellij.ui.tabs.FileColorManagerImpl;
import com.intellij.ui.tabs.TabsUtil;
import com.intellij.ui.tabs.UiDecorator;
import com.intellij.ui.tabs.impl.JBTabsImpl;
import com.intellij.util.ObjectUtils;
import com.intellij.util.containers.ContainerUtil;
import com.intellij.util.ui.JBUI;
import com.intellij.util.ui.JBValue;
import com.intellij.util.ui.UIUtil;
import com.intellij.vcs.log.VcsLogStandardColors;
import com.intellij.vcs.log.ui.highlighters.CurrentBranchHighlighter;
import com.intellij.vcs.log.ui.highlighters.MergeCommitsHighlighter;
import io.acari.DDLC.DDLCConfig;
import io.acari.DDLC.LegacySupportUtility;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;

@SuppressWarnings("FeatureEnvy")
public enum UIReplacer {
  DEFAULT;

  public static void patchUI() {
    try {
      patchAutocomplete();
      patchTabs();
      patchTables();
      patchGrays();
      patchIdeaActionButton();
      patchScrollbars();
      patchVCS();
      patchScopes();
      patchNavBar();
      patchIdeaActionButton();
      patchOnMouseOver();
      patchPluginPage();
      patchAndroid();
      patchAttributes();
    } catch (final ClassNotFoundException | IllegalAccessException | NoSuchFieldException e) {
      e.printStackTrace();
    }
  }

  private static void patchOnMouseOver() throws NoSuchFieldException, IllegalAccessException {
    LegacySupportUtility.INSTANCE.invokeVoidMethodSafely(UIUtil.class,
        "getListSelectionBackground", () ->
            StaticPatcher.setFinalStatic(Switcher.class, "ON_MOUSE_OVER_BG_COLOR",
                UIUtil.getListSelectionBackground(true)),
        () -> {
        }, boolean.class);

  }

  /**
   * Set the color of even rows in tables
   */
  static void patchTables() throws NoSuchFieldException, IllegalAccessException {
    if (MTConfig.getInstance().isMaterialTheme()) {
      StaticPatcher.setFinalStatic(UIUtil.class, "DECORATED_ROW_BG_COLOR", UIManager.get("Table.stripeColor"));
      StaticPatcher.setFinalStatic(UIUtil.class, "UNFOCUSED_SELECTION_COLOR", UIManager.get("Table.stripeColor"));
    }
  }

  static void patchGrays() throws NoSuchFieldException, IllegalAccessException {
    if (MTConfig.getInstance().isMaterialTheme()) {
      // Replace Gray with a clear and transparent color
      final Gray gray = Gray._85;
      final Color alphaGray = gray.withAlpha(1);
      StaticPatcher.setFinalStatic(Gray.class, "_85", alphaGray);
      StaticPatcher.setFinalStatic(Gray.class, "_40", alphaGray);
      StaticPatcher.setFinalStatic(Gray.class, "_145", alphaGray);
      StaticPatcher.setFinalStatic(Gray.class, "_201", alphaGray);

      // Quick info border
      StaticPatcher.setFinalStatic(Gray.class, "_90", gray.withAlpha(25));


      // tool window color
      final boolean dark = DDLCConfig.getInstance().getSelectedTheme().isDark();
      StaticPatcher.setFinalStatic(Gray.class, "_15", dark ? Gray._15.withAlpha(255) : Gray._200.withAlpha(15));
    }
  }


  /**
   * Patch the autocomplete color with the accent color
   */
  static void patchAutocomplete() throws IllegalAccessException {
    if (!MTConfig.getInstance().isMaterialTheme()) {
      return;
    }

    final Color autoCompleteBackground = MTUI.Panel.getSecondaryBackground();
    try {
      final Field backgroundColorField = LookupCellRenderer.class.getDeclaredField("BACKGROUND_COLOR");
      StaticPatcher.setFinalStatic(backgroundColorField, autoCompleteBackground);
    } catch (final NoSuchFieldException | IllegalAccessException e) {
      System.err.println("Unable to patch completion popup: " + e.getLocalizedMessage());
    }
  }

  private static void patchAndroid() throws NoSuchFieldException, IllegalAccessException {
    final Color panelBackground = MTUI.Panel.getBackground();
    final Color contrastBackground = MTUI.Panel.getContrastBackground();
    final Color secondaryBackground = MTUI.Panel.getSecondaryBackground();
    final Color highlightBackground = MTUI.Panel.getHighlightBackground();

    try {
      final Class<?> uiUtils = Class.forName("com.android.tools.idea.assistant.view.UIUtils");
      StaticPatcher.setFinalStatic(uiUtils, "AS_STANDARD_BACKGROUND_COLOR", panelBackground);
      StaticPatcher.setFinalStatic(uiUtils, "BACKGROUND_COLOR", panelBackground);
      StaticPatcher.setFinalStatic(uiUtils, "SECONDARY_COLOR", secondaryBackground);

      final Class<?> wizardConstants = Class.forName("com.android.tools.idea.wizard.WizardConstants");
      StaticPatcher.setFinalStatic(wizardConstants, "ANDROID_NPW_HEADER_COLOR", panelBackground);

      final Class<?> navColorSet = Class.forName("com.android.tools.idea.naveditor.scene.NavColorSet");
      StaticPatcher.setFinalStatic(navColorSet, "BACKGROUND_COLOR", contrastBackground);
      StaticPatcher.setFinalStatic(navColorSet, "FRAME_COLOR", contrastBackground);
      StaticPatcher.setFinalStatic(navColorSet, "HIGHLIGHTED_FRAME_COLOR", highlightBackground);
      StaticPatcher.setFinalStatic(navColorSet, "SUBDUED_FRAME_COLOR", highlightBackground);
      StaticPatcher.setFinalStatic(navColorSet, "SUBDUED_BACKGROUND_COLOR", panelBackground);
      StaticPatcher.setFinalStatic(navColorSet, "COMPONENT_BACKGROUND_COLOR", secondaryBackground);
      StaticPatcher.setFinalStatic(navColorSet, "LIST_MOUSEOVER_COLOR", secondaryBackground);
      StaticPatcher.setFinalStatic(navColorSet, "PLACEHOLDER_BACKGROUND_COLOR", secondaryBackground);

      final Class<?> studioColors = Class.forName("com.android.tools.adtui.common.StudioColorsKt");
      StaticPatcher.setFinalStatic(studioColors, "primaryPanelBackground", contrastBackground);
      StaticPatcher.setFinalStatic(studioColors, "secondaryPanelBackground", panelBackground);
      StaticPatcher.setFinalStatic(studioColors, "border", panelBackground);
      StaticPatcher.setFinalStatic(studioColors, "borderLight", secondaryBackground);

    } catch (final ClassNotFoundException e) {
      //      e.printStackTrace();
    }
  }

  /**
   * Theme scrollbars
   */
  @SuppressWarnings("OverlyLongMethod")
  static void patchScrollbars() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
    final boolean isTransparentScrollbars = MTConfig.getInstance().isThemedScrollbars();
    final boolean accentScrollbars = MTConfig.getInstance().isAccentScrollbars();
    try {
      final Class<?> scrollPainterClass = Class.forName("com.intellij.ui.components.ScrollPainter");

      if (isTransparentScrollbars) {
        final Color transparentColor = UIManager.getColor("ScrollBar.thumb");

        StaticPatcher.setFinalStatic(scrollPainterClass, "x0D", transparentColor);
        StaticPatcher.setFinalStatic(scrollPainterClass, "xA6", transparentColor);

        // Set transparency in windows and linux
        final Gray gray = Gray.xA6;
        final Color alphaGray = gray.withAlpha(60);
        StaticPatcher.setFinalStatic(Gray.class, "xA6", alphaGray);
        StaticPatcher.setFinalStatic(Gray.class, "x00", alphaGray);

        // Transparency in mac
        StaticPatcher.setFinalStatic(Gray.class, "x80", alphaGray);
        StaticPatcher.setFinalStatic(Gray.class, "x26", alphaGray);

        // only work from 2018.1
        if (SystemInfo.isMac) {
          // Control the base opacity and the delta opacity
          Registry.get("mac.editor.thumb.default.alpha.base").setValue(0);
          Registry.get("mac.editor.thumb.default.alpha.delta").setValue(102);
          Registry.get("mac.editor.thumb.darcula.alpha.base").setValue(0);
          Registry.get("mac.editor.thumb.darcula.alpha.delta").setValue(102);

          // control the difference between active and idle
          Registry.get("mac.editor.thumb.default.fill.min").setValue(102);
          Registry.get("mac.editor.thumb.default.fill.max").setValue(150);
          Registry.get("mac.editor.thumb.darcula.fill.min").setValue(102);
          Registry.get("mac.editor.thumb.darcula.fill.max").setValue(163);
        } else {
          Registry.get("win.editor.thumb.default.alpha.base").setValue(0);
          Registry.get("win.editor.thumb.default.alpha.delta").setValue(102);
          Registry.get("win.editor.thumb.darcula.alpha.base").setValue(0);
          Registry.get("win.editor.thumb.darcula.alpha.delta").setValue(102);

          Registry.get("win.editor.thumb.default.fill.min").setValue(102);
          Registry.get("win.editor.thumb.default.fill.max").setValue(150);
          Registry.get("win.editor.thumb.darcula.fill.min").setValue(102);
          Registry.get("win.editor.thumb.darcula.fill.max").setValue(150);
        }
      } else {
        // only work from 2018.1
        if (SystemInfo.isMac) {
          Registry.get("mac.editor.thumb.default.alpha.base").setValue(102);
          Registry.get("mac.editor.thumb.default.alpha.delta").setValue(120);
          Registry.get("mac.editor.thumb.darcula.alpha.base").setValue(128);
          Registry.get("mac.editor.thumb.darcula.alpha.delta").setValue(127);

          Registry.get("mac.editor.thumb.default.fill.min").setValue(90);
          Registry.get("mac.editor.thumb.default.fill.max").setValue(50);
          Registry.get("mac.editor.thumb.darcula.fill.min").setValue(133);
          Registry.get("mac.editor.thumb.darcula.fill.max").setValue(150);
        } else {
          Registry.get("win.editor.thumb.default.alpha.base").setValue(120);
          Registry.get("win.editor.thumb.default.alpha.delta").setValue(135);
          Registry.get("win.editor.thumb.darcula.alpha.base").setValue(128);
          Registry.get("win.editor.thumb.darcula.alpha.delta").setValue(127);

          Registry.get("win.editor.thumb.default.fill.min").setValue(193);
          Registry.get("win.editor.thumb.default.fill.max").setValue(163);
          Registry.get("win.editor.thumb.darcula.fill.min").setValue(133);
          Registry.get("win.editor.thumb.darcula.fill.max").setValue(150);
        }
      }

      final Color accent;
      accent = accentScrollbars ? ColorUtil.fromHex(MTConfig.getInstance().getAccentColor()) : Gray.xA6;

      final MTScrollUI myScrollPainter = new MTScrollUI(2, 0.28f, 0.27f, accent, accent);
      final Class<?> scrollPainterClass1 = Class.forName("com.intellij.ui.components.ScrollPainter$Thumb");
      final Class<?> scrollPainterClass2 = Class.forName("com.intellij.ui.components.ScrollPainter$EditorThumb");
      final Class<?> scrollPainterClass3 = Class.forName("com.intellij.ui.components.ScrollPainter$EditorThumb$Mac");

      StaticPatcher.setFinalStatic(scrollPainterClass, "x0D", accent);
      StaticPatcher.setFinalStatic(scrollPainterClass, "xA6", accent);

      StaticPatcher.setFinalStatic(scrollPainterClass1, "DARCULA", myScrollPainter);
      StaticPatcher.setFinalStatic(scrollPainterClass1, "DEFAULT", myScrollPainter);

      StaticPatcher.setFinalStatic(scrollPainterClass2, "DARCULA", myScrollPainter);
      StaticPatcher.setFinalStatic(scrollPainterClass2, "DEFAULT", myScrollPainter);

      StaticPatcher.setFinalStatic(scrollPainterClass3, "DARCULA", myScrollPainter);
      StaticPatcher.setFinalStatic(scrollPainterClass3, "DEFAULT", myScrollPainter);
    } catch (Throwable ignored) {

    }
  }

  /**
   * Theme up tags and lines of the VCS log
   *
   * @deprecated Remove in 2019.1
   */
  public static void patchVCS() throws NoSuchFieldException, IllegalAccessException {
    if (MTConfig.getInstance().isMaterialTheme()) {
      final Color color = ObjectUtils.notNull(UIManager.getColor("material.mergeCommits"), new ColorUIResource(0x00000000));
      final Color commitsColor = new JBColor(color, color);

      final Field[] fields = CurrentBranchHighlighter.class.getDeclaredFields();
      final Object[] objects = Arrays.stream(fields)
          .filter(field -> field.getType().equals(JBColor.class))
          .toArray();

      StaticPatcher.setFinalStatic((Field) objects[0], commitsColor);

      final Field[] fields2 = MergeCommitsHighlighter.class.getDeclaredFields();
      final Object[] objects2 = Arrays.stream(fields2)
          .filter(field -> field.getType().equals(JBColor.class))
          .toArray();

      final Color accentColor = ColorUtil.fromHex(MTConfig.getInstance().getAccentColor());
      final Color mergeCommitsColor = new JBColor(accentColor, accentColor);
      StaticPatcher.setFinalStatic((Field) objects2[0], mergeCommitsColor);

      final Color branchColor = ObjectUtils.notNull(UIManager.getColor("material.branchColor"), new ColorUIResource(0x9f79b5));
      final Color tagColor = ObjectUtils.notNull(UIManager.getColor("material.tagColor"), new ColorUIResource(0x7a7a7a));

      StaticPatcher.setFinalStatic(VcsLogStandardColors.Refs.class, "BRANCH", accentColor);
      StaticPatcher.setFinalStatic(VcsLogStandardColors.Refs.class, "BRANCH_REF", branchColor);
      StaticPatcher.setFinalStatic(VcsLogStandardColors.Refs.class, "TAG", tagColor);
    }
  }

  /**
   * Very clever way to theme excluded files color
   */
  public static void patchScopes() throws NoSuchFieldException, IllegalAccessException {
    if (!MTConfig.getInstance().isMaterialTheme()) {
      return;
    }

    final Color disabledColor = DDLCConfig.getInstance().getSelectedTheme().getTheme().getExcludedColor();

    final Map<String, Color> ourDefaultColors = ContainerUtil.<String, Color>immutableMapBuilder()
        .put("Sea", UIManager.getColor("FileColor.Blue")) //NON-NLS
        .put("Forest", UIManager.getColor("FileColor.Green"))//NON-NLS
        .put("Spice", UIManager.getColor("FileColor.Orange"))//NON-NLS
        .put("Crimson", UIManager.getColor("FileColor.Rose"))//NON-NLS
        .put("DeepPurple", UIManager.getColor("FileColor.Violet"))//NON-NLS
        .put("Amber", UIManager.getColor("FileColor.Yellow"))//NON-NLS
        .put("Theme", disabledColor)//NON-NLS
        .build();

    final Field[] fields = FileColorManagerImpl.class.getDeclaredFields();
    final Object[] objects = Arrays.stream(fields)
        .filter(field -> field.getType().equals(Map.class))
        .toArray();

    StaticPatcher.setFinalStatic((Field) objects[0], ourDefaultColors);
  }

  /**
   * Replace NavBar with MTNavBar
   */
  public static void patchNavBar() throws NoSuchFieldException, IllegalAccessException {
    if (MTConfig.getInstance().isMaterialDesign()) {
      StaticPatcher.setFinalStatic(NavBarUIManager.class, "DARCULA", new MTNavBarUI());
      StaticPatcher.setFinalStatic(NavBarUIManager.class, "COMMON", new MTNavBarUI());
    }
  }

  /**
   * Replace IdeaActionButton with MTIdeaActionButton
   */
  public static void patchIdeaActionButton() throws NoSuchFieldException, IllegalAccessException {
    if (MTConfig.getInstance().isMaterialDesign()) {
      StaticPatcher.setFinalStatic(ActionButtonLook.class, "SYSTEM_LOOK", new MTActionButtonLook());
    }
  }

  public static void patchPluginPage() {
    if (!MTConfig.getInstance().isMaterialTheme()) {
      return;
    }
    final Color accentColor = ColorUtil.fromHex(MTConfig.getInstance().getAccentColor());

    LegacySupportUtility.INSTANCE.invokeClassSafely(
        "com.intellij.ide.plugins.PluginManagerConfigurableNew",
        () -> {
          StaticPatcher.setFinalStatic(PluginManagerConfigurableNew.class, "MAIN_BG_COLOR", UIUtil.getPanelBackground());
          StaticPatcher.setFinalStatic(PluginManagerConfigurableNew.class, "DisabledColor", UIUtil.getLabelDisabledForeground());
          StaticPatcher.setFinalStatic(PluginManagerConfigurableNew.class, "WhiteForeground", UIUtil.getLabelForeground());
          StaticPatcher.setFinalStatic(PluginManagerConfigurableNew.class, "WhiteBackground", UIUtil.getLabelBackground());
          StaticPatcher.setFinalStatic(PluginManagerConfigurableNew.class, "BlueColor", accentColor);
          StaticPatcher.setFinalStatic(PluginManagerConfigurableNew.class, "GreenColor", accentColor);
          StaticPatcher.setFinalStatic(PluginManagerConfigurableNew.class, "GreenFocusedBackground", ColorUtil.brighter(accentColor, 4));

          final Class<?> CellPluginComponentCls = Class.forName("com.intellij.ide.plugins.PluginManagerConfigurableNew$CellPluginComponent");
          StaticPatcher.setFinalStatic(CellPluginComponentCls, "HOVER_COLOR", UIUtil.getListSelectionBackground());
          StaticPatcher.setFinalStatic(CellPluginComponentCls, "GRAY_COLOR", UIUtil.getLabelForeground());
        }
    );
  }

  /**
   * New implementation for tabs height
   */
  private static void patchTabs() throws NoSuchFieldException, IllegalAccessException {
    final int tabsHeight = MTConfig.getInstance().getTabsHeight() + 10;
    try {
      final Class<?> tabsClass = Class.forName("com.intellij.ui.tabs.impl.SingleHeightTabs");
      StaticPatcher.setFinalStatic(tabsClass, "UNSCALED_PREF_HEIGHT", tabsHeight);
    } catch (final ClassNotFoundException e) {
      patchTabsOld();
    }
  }

  public static void patchTabsOld() throws NoSuchFieldException, IllegalAccessException {
      final int baseHeight = 9;
      final int tabsHeight = Math.max(MTConfig.getInstance().getTabsHeight() / 2 - baseHeight, 0);
      StaticPatcher.setFinalStatic(TabsUtil.class, "TAB_VERTICAL_PADDING", new JBValue.Float(tabsHeight));
      StaticPatcher.setFinalStatic(TabsUtil.class, "NEW_TAB_VERTICAL_PADDING", tabsHeight);

      StaticPatcher.setFinalStatic(JBTabsImpl.class, "ourDefaultDecorator",
          (UiDecorator) () -> new UiDecorator.UiDecoration(null, JBUI.insets(-1 * TabsUtil.NEW_TAB_VERTICAL_PADDING, 8)));
  }

  static void patchAttributes() {
    if (!MTConfig.getInstance().isMaterialTheme()) {
      return;
    }
    try {
      StaticPatcher.setFinalStatic(JBColor.class, "GRAY", MTUI.Label.getLabelInfoForeground());
      StaticPatcher.setFinalStatic(JBColor.class, "LIGHT_GRAY", MTUI.Label.getSelectedForeground());
      StaticPatcher.setFinalStatic(JBColor.class, "DARK_GRAY", MTUI.Label.getLabelDisabledForeground());

      StaticPatcher.setFinalStatic(SimpleTextAttributes.class, "DARK_TEXT", new SimpleTextAttributes(
          SimpleTextAttributes.STYLE_PLAIN,
          MTUI.Label.getLabelDisabledForeground()));

      StaticPatcher.setFinalStatic(SimpleTextAttributes.class, "SIMPLE_CELL_ATTRIBUTES", new SimpleTextAttributes(
          SimpleTextAttributes.STYLE_PLAIN,
          MTUI.Label.getLabelInfoForeground()));

      StaticPatcher.setFinalStatic(SimpleTextAttributes.class, "EXCLUDED_ATTRIBUTES", new SimpleTextAttributes(
          SimpleTextAttributes.STYLE_PLAIN,
          MTUI.Label.getLabelDisabledForeground()));

      StaticPatcher.setFinalStatic(SimpleTextAttributes.class, "GRAY_ATTRIBUTES", new SimpleTextAttributes(
          SimpleTextAttributes.STYLE_PLAIN,
          MTUI.Label.getLabelInfoForeground()));
      StaticPatcher.setFinalStatic(SimpleTextAttributes.class, "GRAY_SMALL_ATTRIBUTES", new SimpleTextAttributes(
          SimpleTextAttributes.STYLE_SMALLER,
          MTUI.Label.getLabelInfoForeground()));
      StaticPatcher.setFinalStatic(SimpleTextAttributes.class, "GRAY_ITALIC_ATTRIBUTES", new SimpleTextAttributes(
          SimpleTextAttributes.STYLE_ITALIC,
          MTUI.Label.getLabelInfoForeground()));

      StaticPatcher.setFinalStatic(SimpleTextAttributes.class, "SYNTHETIC_ATTRIBUTES",
          new SimpleTextAttributes(
              SimpleTextAttributes.STYLE_PLAIN,
              MTUI.Panel.getLinkForeground()
          )
      );

    } catch (final NoSuchFieldException | IllegalAccessException e) {
      e.printStackTrace();
    }
  }
}

