package com.max77.freedomfinanceeval.ui.widgets

import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.max77.freedomfinanceeval.R
import com.max77.freedomfinanceeval.ui.theme.StocksTheme

@Composable
internal fun StockPercentageSubtitle(
    price: Double,
    delta: Double,
    modifier: Modifier = Modifier,
) {
    Text(
        text = stringResource(R.string.percent_subtitle, price, delta),
        style = StocksTheme.typography.tickerSubtitleStyle,
        color = StocksTheme.colors.primary,
        maxLines = 1,
        modifier = modifier,
    )
}

@Preview
@Composable
fun PreviewStockPercentageSubtitle() {
    StocksTheme {
        Surface {
            StockPercentageSubtitle(price = 0.015, delta = 0.003)
        }
    }
}