package com.max77.freedomfinanceeval.ui.widgets

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
import com.max77.freedomfinanceeval.ui.viewmodel.StockPriceChangeDirection

@Composable
internal fun StockListItem(
    tickerName: String,
    exchangeName: String?,
    stockName: String?,
    tickerIconUrl: String?,
    stockPriceChangePercent: Double,
    stockPriceChangeDirection: StockPriceChangeDirection,
    lastBidPrice: Double,
    lastBidDelta: Double,
    modifier: Modifier = Modifier,
) {
    ConstraintLayout(modifier = modifier
        .height(50.dp)
        .padding(4.dp)) {
        val (ticker, tickerSub, stockChange, stockSub, arrow) = createRefs()

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
            name = tickerName,
            iconUrl = tickerIconUrl,
            modifier = Modifier
                .constrainAs(ticker) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    end.linkTo(stockChange.start, margin = 8.dp)
                    width = Dimension.fillToConstraints
                }
        )

        StockPercentageChangeIndicator(
            price = stockPriceChangePercent,
            priceChangeDirection = stockPriceChangeDirection,
            modifier = Modifier.constrainAs(stockChange) {
                end.linkTo(arrow.start, margin = 8.dp)
                top.linkTo(parent.top)
            }
        )

        TickerSubtitle(
            exchangeName = exchangeName,
            stockName = stockName,
            modifier = Modifier.constrainAs(tickerSub) {
                start.linkTo(parent.start, margin = 4.dp)
                end.linkTo(stockSub.start, margin = 8.dp)
                bottom.linkTo(parent.bottom)
                width = Dimension.fillToConstraints
            }
        )

        StockPercentageSubtitle(
            price = lastBidPrice,
            delta = lastBidDelta,
            modifier = Modifier.constrainAs(stockSub) {
                end.linkTo(stockChange.end)
                bottom.linkTo(parent.bottom)
            }
        )
    }
}

@Preview
@Composable
fun PreviewStockListItem() {
    StocksTheme {
        Surface() {
            StockListItem(
                tickerName = "GAZP",
                exchangeName = "MCX",
                stockName = "Газпром",
                tickerIconUrl = "https://tradernet.com/logos/get-logo-by-ticker?ticker=gazp",
                stockPriceChangePercent = 3.18,
                stockPriceChangeDirection = StockPriceChangeDirection.Up,
                lastBidPrice = 11.234,
                lastBidDelta = 1.334,
                modifier = Modifier
                    .height(50.dp)
                    .width(240.dp)
            )
        }
    }
}