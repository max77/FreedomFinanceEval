package com.max77.freedomfinanceeval.ui.widgets

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.max77.freedomfinanceeval.R
import com.max77.freedomfinanceeval.ui.theme.StocksTheme
import com.max77.freedomfinanceeval.ui.viewmodel.StockPriceChangeDirection

@Composable
internal fun StockPercentageChangeIndicator(
    price: Double,
    priceChangeDirection: StockPriceChangeDirection,
    modifier: Modifier = Modifier
) {
    val colors = StocksTheme.colors

    val textColor = when {
        priceChangeDirection != StockPriceChangeDirection.Zero -> colors.percentDefaultColor
        price > 0 -> colors.percentUpColor
        else -> colors.percentDownColor
    }

    val hlColor by animateColorAsState(
        targetValue = when (priceChangeDirection) {
            StockPriceChangeDirection.Up -> colors.percentUpColor
            StockPriceChangeDirection.Down -> colors.percentDownColor
            StockPriceChangeDirection.Zero -> Color.Transparent
        },
        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing),
        label = "hlcolor",
    )

    Surface(
        shape = StocksTheme.shapes.percentHighlightShape,
        color = hlColor,
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.stock_change_percent, price),
            color = textColor,
            style = StocksTheme.typography.percentStyle,
            modifier = Modifier
                .padding(horizontal = 2.dp)
                .wrapContentHeight(align = Alignment.CenterVertically)
        )
    }
}

@Preview
@Composable
fun PreviewStockPercentageChangeIndicator() {
    StocksTheme {
        StockPercentageChangeIndicator(
            1.4,
            priceChangeDirection = StockPriceChangeDirection.Up,
            modifier = Modifier.height(40.dp)
        )
    }
}