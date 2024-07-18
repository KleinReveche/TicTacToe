package presentation.navigation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope
import presentation.navigation.ScreenMainViewModel

@Composable
fun LocalVsPlayerDetails(
  modifier: Modifier = Modifier,
  navController: NavHostController,
  scope: CoroutineScope,
  snackbarHostState: SnackbarHostState,
  vm: ScreenMainViewModel,
) {

  Column(
    modifier = modifier.padding(10.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center,
  ) {
    Row(
      horizontalArrangement = Arrangement.SpaceAround,
      verticalAlignment = Alignment.CenterVertically,
    ) {
      Text("Coming Soon!")
    }
  }
}
