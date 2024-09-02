package com.max77.freedomfinanceeval.ui.widgets

import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.max77.freedomfinanceeval.ui.theme.StocksTheme

@Composable
internal fun StockPercentageSubtitle(
    price: Double,
    delta: Double,
    numDigits: Int,
    modifier: Modifier = Modifier,
) {
    val format = "%.${numDigits}f ( %+.${numDigits}f )"

    Text(
        text = String.format(format, price, delta),
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
            StockPercentageSubtitle(price = 0.015, delta = 0.003, 2)
        }
    }
}