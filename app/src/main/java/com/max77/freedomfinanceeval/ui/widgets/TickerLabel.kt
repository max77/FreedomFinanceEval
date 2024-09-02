package com.max77.freedomfinanceeval.ui.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.max77.freedomfinanceeval.ui.theme.StocksTheme

@Composable
internal fun TickerLabel(
    name: String,
    modifier: Modifier = Modifier,
    iconUrl: String? = null,
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        val painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data(iconUrl)
                .size(Size.ORIGINAL)
                .build()
        )

        if (painter.state is AsyncImagePainter.State.Success
            && painter.intrinsicSize.let { it.width > 2 && it.height > 2 }
        ) {
            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        } else {
            Spacer(modifier = Modifier.size(4.dp))
        }

        Text(
            text = name,
            color = StocksTheme.colors.primary,
            style = StocksTheme.typography.tickerStyle,
            maxLines = 1,
        )
    }
}

@Preview
@Composable
fun PreviewTickerLabel() {
    StocksTheme {
        Surface {
            TickerLabel(
                "GAZP",
                iconUrl = "https://tradernet.com/logos/get-logo-by-ticker?ticker=gazp",
                modifier = Modifier.size(width = 200.dp, height = 40.dp)
            )
        }
    }
}