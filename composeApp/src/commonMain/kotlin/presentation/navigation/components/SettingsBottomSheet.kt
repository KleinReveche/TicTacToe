package presentation.navigation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SheetState
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import domain.model.AppSetting
import domain.model.AppSettings
import domain.repository.AppSettingRepository
import getPlatform
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import presentation.common.components.Header
import presentation.common.components.TitleAndDescription
import presentation.theme.UIColorTypes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsBottomSheet(
  modifier: Modifier = Modifier,
  sheetState: SheetState,
  onDismissRequest: () -> Unit,
) {
  val coroutineScope = rememberCoroutineScope()
  val appSettingRepository = koinInject<AppSettingRepository>()
  val isAndroidPlatformWithDynamicColorSupport =
    getPlatform().name == "Android" && getPlatform().version.toInt() >= 31
  val themeOptions =
    UIColorTypes.entries
      .map { it.name }
      .toMutableList()
      .apply { if (isAndroidPlatformWithDynamicColorSupport) add(0, "Dynamic") }

  val oledOptions = listOf("On", "Off")
  val currentOledOption =
    appSettingRepository
      .getAppSetting(AppSettings.OLED)
      .collectAsState(AppSetting(AppSettings.OLED, "false"))
      .value
      ?.value ?: "false"

  val currentUiColorTypeFromDb =
    appSettingRepository
      .getAppSetting(AppSettings.UI_COLOR_TYPE)
      .collectAsState(AppSetting(AppSettings.UI_COLOR_TYPE, UIColorTypes.Default.name))
      .value
      ?.value
  val currentUiColorType =
    UIColorTypes.entries.find { it.name == currentUiColorTypeFromDb } ?: UIColorTypes.Default
  val isDynamicColorAndroid =
    (appSettingRepository
        .getAppSetting(AppSettings.DYNAMIC_COLOR_ANDROID)
        .collectAsState(AppSetting(AppSettings.DYNAMIC_COLOR_ANDROID, "true"))
        .value
        ?.value ?: "true")
      .toBoolean() && isAndroidPlatformWithDynamicColorSupport
  val currentThemeIndex =
    if (isDynamicColorAndroid) 0 else themeOptions.indexOf(currentUiColorType.name)

  ModalBottomSheet(onDismissRequest = onDismissRequest, sheetState = sheetState) {
    var expanded by remember { mutableStateOf(false) }
    Column(
      modifier = modifier.fillMaxWidth().padding(15.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center,
    ) {
      Header(text = "Settings")

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
      ) {
        TitleAndDescription("Theme", "Choose overall app theme.")

        ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = it }) {
          TextField(
            modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable),
            value = UIColorTypes.entries[currentThemeIndex].name,
            onValueChange = {},
            readOnly = true,
            singleLine = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
          )
          ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            themeOptions.forEachIndexed { index, themeOption ->
              DropdownMenuItem(
                text = { Text(themeOption) },
                onClick = {
                  if (index == 0 && isAndroidPlatformWithDynamicColorSupport) {
                    coroutineScope.launch {
                      appSettingRepository.upsertAppSetting(
                        AppSetting(AppSettings.DYNAMIC_COLOR_ANDROID, "true")
                      )
                    }
                  } else {
                    val selectedTheme =
                      if (isAndroidPlatformWithDynamicColorSupport) {
                        UIColorTypes.entries[index - 1]
                      } else {
                        UIColorTypes.entries[index]
                      }
                    coroutineScope.launch {
                      appSettingRepository.upsertAppSetting(
                        AppSetting(AppSettings.UI_COLOR_TYPE, selectedTheme.name)
                      )
                      appSettingRepository.upsertAppSetting(
                        AppSetting(AppSettings.DYNAMIC_COLOR_ANDROID, "false")
                      )
                    }
                  }
                },
                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
              )
            }
          }
        }
      }

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
      ) {
        TitleAndDescription("OLED Mode", "Enable true black for dark mode.")

        SingleChoiceSegmentedButtonRow {
          oledOptions.forEachIndexed { index, oledOption ->
            SegmentedButton(
              shape = SegmentedButtonDefaults.itemShape(count = oledOptions.size, index = index),
              onClick = {
                coroutineScope.launch {
                  appSettingRepository.upsertAppSetting(
                    AppSetting(AppSettings.OLED, (!currentOledOption.toBoolean()).toString())
                  )
                }
              },
              selected = index == (if (currentOledOption.toBoolean()) 1 else 0),
            ) {
              Text(text = oledOption, modifier = Modifier.padding(5.dp, 0.dp))
            }
          }
        }
      }

      Text(
        text = "${getPlatform().name} ${getPlatform().version}",
        modifier = Modifier.fillMaxWidth().padding(10.dp),
        fontSize = 10.sp,
        fontWeight = FontWeight.Normal,
        textAlign = TextAlign.Center,
      )
    }
  }
}
