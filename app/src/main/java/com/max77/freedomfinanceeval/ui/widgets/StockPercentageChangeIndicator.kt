package com.max77.freedomfinanceeval.ui.widgets

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.max77.freedomfinanceeval.R
import com.max77.freedomfinanceeval.ui.theme.StocksTheme
import com.max77.freedomfinanceeval.ui.viewmodel.StockPriceChangeDirection
import kotlinx.coroutines.launch

private const val animationDuration = 1000
private const val transitionDuration = 200

@Composable
internal fun StockPercentageChangeIndicator(
    price: Double,
    priceChangeDirection: StockPriceChangeDirection,
    modifier: Modifier = Modifier
) {
    val colors = StocksTheme.colors
    var shouldAnimate by rememberSaveable(price) { mutableStateOf(true) }

    val textColor = remember(price) {
        when {
            price >= 0 -> colors.percentUpColor
            else -> colors.percentDownColor
        }
    }
    val hlColor = remember(price) {
        when (priceChangeDirection) {
            StockPriceChangeDirection.Up -> colors.percentUpColor
            StockPriceChangeDirection.Down -> colors.percentDownColor
            StockPriceChangeDirection.Zero -> Color.Transparent
        }
    }

    val hlColorAnimation = remember(price) { Animatable(Color.Transparent) }
    val textColorAnimation = remember(price) { Animatable(textColor) }

    LaunchedEffect(price) {
        if (!shouldAnimate) return@LaunchedEffect
        shouldAnimate = false

        println("XXX price: $price, priceChangeDirection: $priceChangeDirection")

        launch {
            hlColorAnimation.animateTo(
                targetValue = Color.Transparent,
                animationSpec = keyframes {
                    durationMillis = animationDuration
                    hlColor at transitionDuration
                    hlColor at animationDuration - transitionDuration
                }
            )
        }

        if (priceChangeDirection != StockPriceChangeDirection.Zero) {
            launch {
                textColorAnimation.animateTo(
                    targetValue = textColorAnimation.value,
                    animationSpec = keyframes {
                        durationMillis = animationDuration
                        colors.percentDefaultColor at transitionDuration
                        colors.percentDefaultColor at animationDuration - transitionDuration
                    }
                )
            }
        }
    }

    Surface(
        shape = StocksTheme.shapes.percentHighlightShape,
        color = hlColorAnimation.value,
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.stock_change_percent, price),
            color = textColorAnimation.value,
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