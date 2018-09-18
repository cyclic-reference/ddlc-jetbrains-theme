package com.chrisrm.idea.themes.literature.club.dark

import com.chrisrm.idea.themes.literature.club.DokiDokiTheme
import java.util.stream.Stream

class OnlyPlayWithMeTheme : DokiDokiTheme("natsuki.dark", "Only Play With Me", true, "Natsuki") {

  override fun getBackgroundColorString(): String = "240E1E"

  override fun getClubMember(): String = "natsuki_dark.png"

  override fun joyfulClubMember(): String = "natsuki_dark_joy.png"

  override fun getSecondaryBackgroundColorString(): String = "330A2B                          "

  override fun getSecondaryForegroundColorString(): String = "BB47A8"

  override fun getSelectionForegroundColorString(): String = "D671B0"

  override fun getSelectionBackgroundColorString(): String = "5D2960"

  override fun getTreeSelectionBackgroundColorString(): String = "B54E8E"

  override fun getMenuBarSelectionForegroundColorString(): String = "ffffff"

  override fun getMenuBarSelectionBackgroundColorString(): String = "9D0064"

  override fun getNotificationsColorString(): String = "562447"

  override fun getSelectionBackground(): String = "FF4800"

  override fun getContrastColorString(): String = "56324C"

  override fun getButtonBackgroundColor(): String = contrastColorString

  override fun getButtonForegroundColor(): String = "E85EDB"

  override fun getForegroundColorString(): String = "DC508F"

  override fun getTextColorString(): String = "FF78BA"

  override fun getEditorTabColorString(): String = contrastColorString

  override fun getNonProjectFileScopeColor(): String = "2a1b1f"

  override fun getTestScope(): String = "0f2a00"

  override fun getSecondBorderColorString(): String = "5A1943"

  override fun getDisabledColorString(): String = "232323"

  override fun getPropertyStream(): Stream<Pair<String, String>> {
    return Stream.of(
        Pair("natsuki.dark.background", "231921"),
        Pair("Panel.background", "2B2827"),
        Pair("Menu.foreground", "ffffff"),
        Pair("PopupMenu.background", "874C57"),
        Pair("Menu.background", "874C57"),
        Pair("MenuBar.background", "874C57"),
        Pair("Menu.acceleratorForeground", "ffffff"),
        Pair("MenuBar.foreground", "ffffff"),
        Pair("Button.mt.foreground", "ff6fd5"),
        Pair("MenuItem.selectionForeground", "ffffff"),
        Pair("MenuItem.selectionBackground", "AA041A"),
        Pair("MenuItem.foreground", "FFFFFF"),
        Pair("Menu.selectionForeground", "ffffff"),
        Pair("Menu.selectionBackground", "AA041A"),
        Pair("Notifications.background", "B17FB1"),
        Pair("Notifications.borderColor", "ffc4ff"),
        Pair("Table.selectionForeground", "000000"),
        Pair("Table.selectionBackground", "CB7BCA"),
        Pair("Table.highlightOuter", "FFD3F6"),
        Pair("Autocomplete.selectionBackground", "462325"),
        Pair("Autocomplete.selectionForeground", "FB67B7"),
        Pair("Autocomplete.selectionForegroundGreyed", "FFFFFF"),
        Pair("Autocomplete.background", "401925"),
        Pair("Autocomplete.foreground", "FC99D0"),
        Pair("Autocomplete.selectedGreyedForeground", "F844D2"),
        Pair("Autocomplete.prefixForeground", "F4B1F8"),
        Pair("Autocomplete.selectedPrefixForeground", "FFEAFD"),
        Pair("Autocomplete.selectionUnfocus", "542A30"),
        Pair("Button.mt.color2", "9B3685"),
        Pair("Button.mt.primary.color", "7E436E"),
        Pair("Button.mt.selection.color1", "B9589F"),
        Pair("Button.mt.selection.color2", "B3B2B2"),
        Pair("Button.mt.background", "9B3685"),
        Pair("Button.mt.selectedForeground", "FFFFFF"),
        Pair("Button.mt.color1", "9B3685"),
        Pair("ToolBar.background", "9B3685"),
        Pair("EditorPane.caretForeground", "9B3685"),
        Pair("SearchEverywhere.background", "CC91C9"),
        Pair("SearchEverywhere.foreground", "AE36B6"),
        Pair("SearchEverywhere.shortcutForeground", "C1B7C5"),
        Pair("natsuki.dark.textBackground", "4A1D3B"),
        Pair("natsuki.dark.foreground", "A76E9F"),
        Pair("natsuki.dark.textForeground", "A76E9F"),
        Pair("natsuki.dark.caretForeground", "FFCC00"),
        Pair("natsuki.dark.inactiveBackground", "4A1D3B"),
        Pair("natsuki.dark.selectionForeground", "FFFFFF"),
        Pair("natsuki.dark.selectionBackgroundInactive", "D2D4D5"),
        Pair("natsuki.dark.selectionInactiveBackground", "D2D4D5"),
        Pair("natsuki.dark.selectionForegroundInactive", "A76E9F"),
        Pair("natsuki.dark.selectionInactiveForeground", "A76E9F"),
        Pair("List.background", "330A2B"),
        Pair("ToolWindow.header.background", "4A1D3B"),
        Pair("ToolWindow.header.active.background", "532B55"),
        Pair("ToolWindow.header.border.background", "532B55"),
        Pair("ToolWindow.header.tab.selected.background", "CFABCB"),
        Pair("ToolWindow.header.tab.selected.active.background", "CE89C9"),
        Pair("ToolWindow.header.tab.selected.active.foreground", "663960"),
        Pair("ToolWindow.header.closeButton.background", "4A1D3B"),
        Pair("Panel.background", "2B2827")


//        Pair("natsuki.dark.background", "231921"),
//        Pair("Menu.foreground", "ffffff"),
//        Pair("PopupMenu.background", "874C57"),
//        Pair("Menu.background", "874C57"),
//        Pair("MenuBar.background", "874C57"),
//        Pair("Menu.acceleratorForeground", "ffffff"),
//        Pair("MenuBar.foreground", "ffffff"),
//        Pair("Button.mt.foreground", "ff6fd5"),
//        Pair("MenuItem.selectionForeground", "ffffff"),
//        Pair("MenuItem.selectionBackground", "AA041A"),
//        Pair("MenuItem.foreground", "FFFFFF"),
//        Pair("Menu.selectionForeground", "ffffff"),
//        Pair("Menu.selectionBackground", "AA041A"),
//        Pair("Notifications.background", "B17FB1"),
//        Pair("Notifications.borderColor", "ffc4ff"),
//        Pair("Table.selectionForeground", "000000"),
//        Pair("Table.selectionBackground", "CB7BCA"),
//        Pair("Table.highlightOuter", "FFD3F6"),
//        Pair("Autocomplete.selectionBackground", "462325"),
//        Pair("Autocomplete.selectionForeground", "FB67B7"),
//        Pair("Autocomplete.selectionForegroundGreyed", "FFFFFF"),
//        Pair("Autocomplete.background", "401925"),
//        Pair("Autocomplete.foreground", "FC99D0"),
//        Pair("Autocomplete.selectedGreyedForeground", "F844D2"),
//        Pair("Autocomplete.prefixForeground", "F4B1F8"),
//        Pair("Autocomplete.selectedPrefixForeground", "FFEAFD"),
//        Pair("Autocomplete.selectionUnfocus", "542A30"),
//        Pair("Button.mt.color2", "9B3685"),
//        Pair("Button.mt.primary.color", "7E436E"),
//        Pair("Button.mt.selection.color1", "B9589F"),
//        Pair("Button.mt.selection.color2", "B3B2B2"),
//        Pair("Button.mt.background", "9B3685"),
//        Pair("Button.mt.selectedForeground", "FFFFFF"),
//        Pair("Button.mt.color1", "9B3685"),
//        Pair("ToolBar.background", "9B3685"),
//        Pair("EditorPane.caretForeground", "9B3685"),
//        Pair("SearchEverywhere.background", "CC91C9"),
//        Pair("SearchEverywhere.foreground", "AE36B6"),
//        Pair("SearchEverywhere.shortcutForeground", "C1B7C5"),
//
//        Pair("MenuBar.disabledBackground", "BFBEBD"),
//        Pair("MenuItem.disabledForeground", "BFBEBD"),
//        Pair("Label.disabledForeground", "BFBEBD"),
//        Pair("ActiveToolBar.background", "FF4800"),
//        Pair("natsuki.dark.textBackground", "4A1D3B"),
//        Pair("natsuki.dark.foreground", "A76E9F"),
//        Pair("natsuki.dark.textForeground", "A76E9F"),
//        Pair("natsuki.dark.caretForeground", "FFCC00"),
//        Pair("natsuki.dark.inactiveBackground", "4A1D3B"),
//        Pair("natsuki.dark.selectionForeground", "FFFFFF"),
//        Pair("natsuki.dark.selectionBackgroundInactive", "D2D4D5"),
//        Pair("natsuki.dark.selectionInactiveBackground", "D2D4D5"),
//        Pair("natsuki.dark.selectionForegroundInactive", "A76E9F"),
//        Pair("natsuki.dark.selectionInactiveForeground", "A76E9F"),
//        Pair("window", "953343"),
//        Pair("activeCaption", "4A1D3B"),
//        Pair("inactiveCaption", "D2D4D5"),
//        Pair("Caret.width", "4"),
//        Pair("text", "D493CB"),
//        Pair("textText", "D493CB"),
//        Pair("textInactiveText", "D493CB"),
//        Pair("infoText", "D493CB"),
//        Pair("control", "4A1D3B"),
//        Pair("controlText", "D493CB"),
//        Pair("link.foreground", "CB94C4"),
//        Pair("OptionPane.messageForeground", "FFFFFF"),
//        Pair("Menu.maxGutterIconWidth", "18"),
//        Pair("Menu.acceleratorSelectionForeground", "FFFFFF"),
//        Pair("MenuItem.maxGutterIconWidth", "18"),
//        Pair("MenuItem.acceleratorForeground", "B0BEC5"),
//        Pair("MenuItem.acceleratorSelectionForeground", "FFFFFF"),
//        Pair("PopupMenu.translucentBackground", "4A1D3B"),
//        Pair("EditorPane.inactiveBackground", "4A1D3B"),
//        Pair("EditorPane.inactiveForeground", "D493CB"),
//        Pair("ScrollBar.thumb", "F4F4F4"),
//        Pair("Table.background", "4A1D3B"),
//        Pair("Table.gridColor", "2A373E"),
//        Pair("Table.sortIconColor", "A76E9F"),
//        Pair("Table.stripedBackground", "4A1D3B"),
//        Pair("Table.focusCellBackground", "F4F4F4"),
//        Pair("Table.highlightInner", "8BD9D1"),
//        Pair("Table.shadowOuter", "72B4B8"),
//        Pair("Table.shadowInner", "78BEC2"),
//        Pair("TitledBorder.titleColor", "A76E9F"),
//        Pair("MenuBar.shadow", "4A1D3B"),
//        Pair("MenuBar.darcula.borderColor", "2A373E"),
//        Pair("MenuBar.darcula.borderShadowColor", "2A373E"),
//        Pair("TabbedPane.highlight", "DFD0E8"),
//        Pair("TabbedPane.light", "444444"),
//        Pair("TabbedPane.selected", "DFD0E8"),
//        Pair("TabbedPane.selectHighlight", "3c3f41"),
//        Pair("TabbedPane.darkShadow", "DFD0E8"),
//        Pair("TabbedPane.shadow", "4A1D3B"),
//        Pair("TabbedPane.borderColor", "4A1D3B"),
//        Pair("Separator.foreground", "953343"),
//        Pair("Focus.color", "674B67"),
//        Pair("TextField.background", "4A1D3B"),
//        Pair("TextField.separatorColor", "674B67"),
//        Pair("TextField.separatorColorDisabled", "99659c"),
//        Pair("TextField.selectedSeparatorColor", "CF8BD6"),
//        Pair("TextField.inactiveForeground", "99659c"),
//        Pair("TextField.selectionBackground", "612964"),
//        Pair("TextField.selectionForeground", "D6D6D6"),
//        Pair("TextField.darcula.error.borderWidth", "2"),
//        Pair("PasswordField.background", "4A1D3B"),
//        Pair("PasswordField.inactiveForeground", "99659c"),
//        Pair("PasswordField.selectionBackground", "612964"),
//        Pair("PasswordField.selectionForeground", "D6D6D6"),
//        Pair("ProgressBar.foreground", "F2C5E9"),
//        Pair("ProgressBar.color", "F2C5E9"),
//        Pair("ProgressBar.halfColor", "E3BEE1"),
//        Pair("FormattedTextField.background", "4A1D3B"),
//        Pair("TextArea.selectionForeground", "304349"),
//        Pair("TextArea.background", "4A1D3B"),
//        Pair("CheckBox.darcula.inactiveFillColor", "674B67"),
//        Pair("CheckBox.darcula.borderColor1", "CB94C4"),
//        Pair("CheckBox.darcula.borderColor2", "CB94C4"),
//        Pair("CheckBox.darcula.disabledBorderColor1", "2A373E"),
//        Pair("CheckBox.darcula.disabledBorderColor2", "2A373E"),
//        Pair("CheckBox.darcula.backgroundColor1", "4A1D3B"),
//        Pair("CheckBox.darcula.backgroundColor1.selected", "CB94C4"),
//        Pair("CheckBox.darcula.backgroundColor2", "4A1D3B"),
//        Pair("CheckBox.darcula.backgroundColor2.selected", "CB94C4"),
//        Pair("CheckBox.darcula.checkSignColor", "4A1D3B"),
//        Pair("CheckBox.darcula.checkSignColorDisabled", "787878"),
//        Pair("CheckBox.darcula.shadowColor", "4A1D3B"),
//        Pair("CheckBox.darcula.shadowColorDisabled", "4A1D3B"),
//        Pair("CheckBox.darcula.focusedArmed.backgroundColor1", "4A1D3B"),
//        Pair("CheckBox.darcula.focusedArmed.backgroundColor1.selected", "CB94C4"),
//        Pair("CheckBox.darcula.focusedArmed.backgroundColor2", "4A1D3B"),
//        Pair("CheckBox.darcula.focusedArmed.backgroundColor2.selected", "CB94C4"),
//        Pair("CheckBox.darcula.focused.backgroundColor1", "4A1D3B"),
//        Pair("CheckBox.darcula.focused.backgroundColor1.selected", "CB94C4"),
//        Pair("CheckBox.darcula.focused.backgroundColor2", "4A1D3B"),
//        Pair("CheckBox.darcula.focused.backgroundColor2.selected", "CB94C4"),
//        Pair("CheckBoxMenuItem.borderPainted", "false"),
//        Pair("ComboBox.background", "4A1D3B"),
//        Pair("ComboBox.disabledBackground", "4A1D3B"),
//        Pair("ComboBox.disabledForeground", "D2D4D5"),
//        Pair("ComboBox.squareButton", "true"),
//        Pair("ComboBox.arrowFillColor", "4A1D3B"),
//        Pair("RadioButton.darcula.selectionEnabledColor", "C299B3"),
//        Pair("RadioButton.darcula.selectionDisabledColor", "4A1D3B"),
//        Pair("RadioButton.darcula.selectionEnabledShadowColor", "C299B3"),
//        Pair("RadioButton.darcula.selectionDisabledShadowColor", "C299B3"),
//        Pair("RadioButtonMenuItem.borderPainted", "false"),
//        Pair("StatusBar.topColor", "4A1D3B"),
//        Pair("StatusBar.top2Color", "4A1D3B"),
//        Pair("StatusBar.bottomColor", "4A1D3B"),
//        Pair("Button.disabledText", "D53056"),
//        Pair("Button.background", "4A1D3B"),
//        Pair("Button.foreground", "B0BEC4"),
//        Pair("Button.darcula.color1", "4A1D3B"),
//        Pair("Button.darcula.color2", "4A1D3B"),
//        Pair("Button.darcula.selection.color1", "99659c"),
//        Pair("Button.darcula.selection.color2", "99659c"),
//        Pair("Button.darcula.selectedButtonForeground", "A0B0B8"),
//        Pair("Button.darcula.disabledText.shadow", "4A1D3B"),
//        Pair("ToolTip.background", "4A1D3B"),
//        Pair("Spinner.background", "4A1D3B"),
//        Pair("Spinner.editorBorderPainted", "false"),
//        Pair("SplitPane.highlight", "4A1D3B"),
//        Pair("Hyperlink.linkColor", "C299B3"),
//        Pair("SidePanel.background", "4A1D3B"),
//        Pair("Tree.rightChildIndent", "6"),
//        Pair("Tree.selectionBackground", "C351C2"),
//        Pair("Tree.selectionForeground", "ffffff"),
//        Pair("Tree.foreground", "A76E9F"),
//        Pair("DialogWrapper.southPanelDivider", "4A1D3B"),
//        Pair("OnePixelDivider.background", "4A1D3B"),
//        Pair("MemoryIndicator.usedColor", "CB94C4"),
//        Pair("MemoryIndicator.unusedColor", "E3BEE1"),
//        Pair("Notifications.errorBackground", "FF5370"),
//        Pair("Notifications.warnBackground", "FFCB6B"),
//        Pair("Notifications.infoBackground", "E8ABDD"),
//        Pair("Dialog.titleColor", "4A1D3B"),
//        Pair("Label.foreground", "A76E9F"),
//        Pair("Label.selectedDisabledForeground", "A76E9F"),
//        Pair("Label.selectedForeground", "FFFFFF"),
//        Pair("ToolWindow.header.background", "4A1D3B"),
//        Pair("ToolWindow.header.active.background", "532B55"),
//        Pair("ToolWindow.header.border.background", "532B55"),
//        Pair("ToolWindow.header.tab.selected.background", "CFABCB"),
//        Pair("ToolWindow.header.tab.selected.active.background", "CE89C9"),
//        Pair("ToolWindow.header.tab.selected.active.foreground", "663960"),
//        Pair("ToolWindow.header.closeButton.background", "4A1D3B"),
//        Pair("ToolWindow.tab.verticalPadding", "5"),
//        Pair("material.background", "4A1D3B"),
//        Pair("material.foreground", "A76E9F"),
//        Pair("material.primaryColor", "B0BEC5"),
//        Pair("material.tab.backgroundColor", "4A1D3B"),
//        Pair("material.tab.borderColor", "CB94C4"),
//        Pair("material.tab.borderThickness", "2"),
//        Pair("material.contrast", "7B3466"),
//        Pair("material.disabled", "BFBEBD"),
//        Pair("material.mergeCommits", "976699"),
//        Pair("material.branchColor", "A76E9F"),
//        Pair("material.tagColor", "9E7993")
    )
  }
}