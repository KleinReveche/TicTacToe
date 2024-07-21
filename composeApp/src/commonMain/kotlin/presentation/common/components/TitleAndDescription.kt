package presentation.common.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TitleAndDescription(title: String, description: String? = null) {
  Column {
    Text(
      text = title,
      modifier = Modifier.padding(15.dp, 10.dp, 15.dp, if (description == null) 10.dp else 0.dp),
      fontSize = 18.sp,
      fontWeight = FontWeight.SemiBold,
      textAlign = TextAlign.Left,
    )
    if (description != null) {
      Text(
        text = description,
        modifier = Modifier.padding(15.dp, 0.dp, 15.dp, 10.dp),
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal,
        textAlign = TextAlign.Left,
      )
    }
  }
}
