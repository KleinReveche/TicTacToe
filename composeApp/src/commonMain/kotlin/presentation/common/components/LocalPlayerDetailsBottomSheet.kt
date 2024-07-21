package presentation.common.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import domain.model.LocalPlayer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocalPlayerDetailsBottomSheet(
  modifier: Modifier = Modifier,
  onDismissRequest: () -> Unit,
  sheetState: SheetState,
  localPlayer: LocalPlayer,
  isVsComputer: Boolean = false,
) {
  ModalBottomSheet(
    modifier = modifier,
    onDismissRequest = onDismissRequest,
    sheetState = sheetState,
  ) {
    Column(
      modifier = modifier.fillMaxWidth().padding(20.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center,
    ) {
      val padding = 30.dp
      val totalVsComputerWin =
        localPlayer.playerVsComputerEasyWin +
          localPlayer.playerVsComputerNormalWin +
          localPlayer.playerVsComputerInsaneWin
      val totalVsComputerLoss =
        localPlayer.playerVsComputerEasyLoss +
          localPlayer.playerVsComputerNormalLoss +
          localPlayer.playerVsComputerInsaneLoss
      val totalVsComputerDraw =
        localPlayer.playerVsComputerEasyDraw +
          localPlayer.playerVsComputerNormalDraw +
          localPlayer.playerVsComputerInsaneDraw

      Header(text = localPlayer.name)

      Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
      ) {
        Column(
          Modifier.weight(0.3f),
          horizontalAlignment = Alignment.CenterHorizontally,
          verticalArrangement = Arrangement.Center,
        ) {
          Text(
            text = if (isVsComputer) "Wins vs AI" else "Wins vs Player",
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
          )
          Text(
            text = if (isVsComputer) "$totalVsComputerWin" else "${localPlayer.playerVsPlayerWin}",
            fontSize = 32.sp,
          )
        }

        Column(
          Modifier.weight(0.3f),
          horizontalAlignment = Alignment.CenterHorizontally,
          verticalArrangement = Arrangement.Center,
        ) {
          Text(
            text = if (isVsComputer) "Draws vs AI" else "Draws vs Player",
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
          )
          Text(
            text =
              if (isVsComputer) "$totalVsComputerDraw" else "${localPlayer.playerVsPlayerDraw}",
            fontSize = 32.sp,
          )
        }

        Column(
          Modifier.weight(0.3f),
          horizontalAlignment = Alignment.CenterHorizontally,
          verticalArrangement = Arrangement.Center,
        ) {
          Text(
            text = if (isVsComputer) "Losses vs AI" else "Losses vs Player",
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
          )
          Text(
            text =
              if (isVsComputer) "$totalVsComputerLoss" else "${localPlayer.playerVsPlayerLoss}",
            fontSize = 32.sp,
          )
        }
      }

      HorizontalDivider(modifier.padding(5.dp))
      Text(
        text = "Wins:",
        modifier = modifier.fillMaxWidth().padding(padding, 0.dp),
        textAlign = TextAlign.Start,
      )
      Text(text = "Against other Players - ${localPlayer.playerVsPlayerWin}")
      Text(text = "Against Easy AI - ${localPlayer.playerVsComputerEasyWin}")
      Text(text = "Against Normal AI - ${localPlayer.playerVsComputerNormalWin}")
      Text(text = "Against Insane AI - ${localPlayer.playerVsComputerInsaneWin}")
      HorizontalDivider(modifier.padding(5.dp))
      Text(
        text = "Losses:",
        modifier = modifier.fillMaxWidth().padding(padding, 0.dp),
        textAlign = TextAlign.Start,
      )
      Text(text = "Against other Players - ${localPlayer.playerVsPlayerLoss}")
      Text(text = "Against Easy AI - ${localPlayer.playerVsComputerEasyLoss}")
      Text(text = "Against Normal AI - ${localPlayer.playerVsComputerNormalLoss}")
      Text(text = "Against Insane AI - ${localPlayer.playerVsComputerInsaneLoss}")
      HorizontalDivider(modifier.padding(5.dp))
      Text(
        text = "Draws:",
        modifier = modifier.fillMaxWidth().padding(padding, 0.dp),
        textAlign = TextAlign.Start,
      )
      Text(text = "Against other Players - ${localPlayer.playerVsPlayerDraw}")
      Text(text = "Against Easy AI - ${localPlayer.playerVsComputerEasyDraw}")
      Text(text = "Against Normal AI - ${localPlayer.playerVsComputerNormalDraw}")
      Text(text = "Against Insane AI - ${localPlayer.playerVsComputerInsaneDraw}")
    }
  }
}
