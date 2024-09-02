package com.max77.freedomfinanceeval.ui.widgets

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.max77.freedomfinanceeval.R
import com.max77.freedomfinanceeval.ui.theme.StocksTheme

@Composable
internal fun TickerSubtitle(
    exchangeName: String?,
    stockName: String?,
    modifier: Modifier = Modifier,
) {
    val separator = stringResource(R.string.ticker_subtitle_separator)
    val text = remember(exchangeName, stockName) {
        listOfNotNull(
            exchangeName,
            stockName
        ).joinToString(separator = separator)
    }

    Text(
        text = text,
        style = StocksTheme.typography.tickerSubtitleStyle,
        color = StocksTheme.colors.secondary,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier,
    )
}

@Preview
@Composable
fun PreviewTickerSubtitle() {
    StocksTheme {
        Surface {
            TickerSubtitle(
                exchangeName = "22132",
                stockName = "ncqdcqwull",
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}