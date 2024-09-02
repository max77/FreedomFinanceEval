package com.max77.freedomfinanceeval.ui.widgets

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.max77.freedomfinanceeval.R
import com.max77.freedomfinanceeval.ui.theme.StocksTheme
import com.max77.freedomfinanceeval.ui.viewmodel.StockListItemInfo
import com.max77.freedomfinanceeval.ui.viewmodel.StockPriceChangeDirection

@Composable
internal fun StockListItem(
    info: StockListItemInfo,
    modifier: Modifier = Modifier,
) {
    ConstraintLayout(
        modifier = modifier
            .height(50.dp)
            .padding(4.dp)
    ) {
        val (ticker, tickerSub, percent, stockChange, arrow) = createRefs()

        Icon(
            painter = painterResource(R.drawable.arrow_right),
            contentDescription = null,
            tint = StocksTheme.colors.secondary,
            modifier = Modifier.constrainAs(arrow) {
                end.linkTo(parent.end, margin = 16.dp)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            }
        )

        TickerLabel(
            name = info.tickerName,
            iconUrl = info.tickerIconUrl,
            modifier = Modifier
                .constrainAs(ticker) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    end.linkTo(percent.start, margin = 8.dp)
                    width = Dimension.fillToConstraints
                }
        )

        StockPercentageChangeIndicator(
            price = info.priceChangePercent ?: 0.0,
            priceChangeDirection = info.stockPriceChangeDirection ?: StockPriceChangeDirection.Zero,
            modifier = Modifier.constrainAs(percent) {
                end.linkTo(arrow.start, margin = 8.dp)
                top.linkTo(parent.top)
            }
        )

        TickerSubtitle(
            exchangeName = info.exchangeName,
            stockName = info.stockName,
            modifier = Modifier.constrainAs(tickerSub) {
                start.linkTo(parent.start, margin = 4.dp)
                end.linkTo(stockChange.start, margin = 8.dp)
                bottom.linkTo(parent.bottom)
                width = Dimension.fillToConstraints
            }
        )

        StockPercentageSubtitle(
            price = info.lastTradePrice ?: 0.0,
            delta = info.priceChangePoints ?: 0.0,
            numDigits = info.numDigits,
            modifier = Modifier.constrainAs(stockChange) {
                end.linkTo(percent.end)
                bottom.linkTo(parent.bottom)
            }
        )
    }
}

@Preview
@Composable
fun PreviewStockListItem() {
    StocksTheme {
        Surface {
            StockListItem(
                StockListItemInfo("GAZP", exchangeName = "XXX", stockName = "BlaBla"),
                modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth()
            )
        }
    }
}