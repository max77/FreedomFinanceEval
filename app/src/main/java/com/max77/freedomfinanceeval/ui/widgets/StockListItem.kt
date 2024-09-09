package com.max77.freedomfinanceeval.ui.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.max77.freedomfinanceeval.R
import com.max77.freedomfinanceeval.ui.theme.StocksTheme
import com.max77.freedomfinanceeval.ui.viewmodel.StockListItemInfo
import com.max77.freedomfinanceeval.ui.viewmodel.StockPriceChangeDirection

@Composable
internal fun StockListItem(
    info: StockListItemInfo,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.padding(4.dp)
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                TickerLabel(
                    name = info.tickerName,
                    iconUrl = info.tickerIconUrl,
                    modifier = Modifier.weight(1f)
                )
                StockPercentageChangeIndicator(
                    price = info.priceChangePercent ?: 0.0,
                    priceChangeDirection = info.stockPriceChangeDirection
                        ?: StockPriceChangeDirection.Zero,
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.Bottom) {
                TickerSubtitle(
                    exchangeName = info.exchangeName,
                    stockName = info.stockName,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 4.dp)
                        .alignByBaseline()
                )
                StockPercentageSubtitle(
                    price = info.lastTradePrice ?: 0.0,
                    delta = info.priceChangePoints ?: 0.0,
                    numDigits = info.numDigits,
                    modifier = Modifier.alignByBaseline()
                )
            }
        }
        Icon(
            painter = painterResource(R.drawable.arrow_right),
            contentDescription = null,
            tint = StocksTheme.colors.secondary,
            modifier = Modifier.padding(end = 16.dp)
        )
    }
}

@Preview
@Composable
fun PreviewStockListItem() {
    StocksTheme {
        Surface {
            StockListItem(
                StockListItemInfo(
                    "GAZP",
                    exchangeName = "XXX",
                    stockName = "BlaBqwejkfboewqirufhouwehbrfouwebgrofuweoufyoewuryfwela"
                ),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}