package com.max77.freedomfinanceeval

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.LifecycleStartEffect
import com.max77.freedomfinanceeval.ui.theme.StocksTheme
import com.max77.freedomfinanceeval.ui.viewmodel.StockListItemInfo
import com.max77.freedomfinanceeval.ui.viewmodel.StocksScreenViewmodel
import com.max77.freedomfinanceeval.ui.viewmodel.UiState
import com.max77.freedomfinanceeval.ui.widgets.StockListItem
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StocksTheme {
                StockPricesScreen()
            }
        }
    }
}

@Composable
fun StockPricesScreen(viewmodel: StocksScreenViewmodel = koinViewModel()) {
    val uiState by viewmodel.uiState.collectAsState()

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Surface(modifier = Modifier.padding(innerPadding)) {
            // local val 's' to guarantee the smart cast
            when (val s = uiState) {
                is UiState.Data -> StockPriceList(s.data)
                is UiState.Error -> ErrorMessage(s.message)
                is UiState.Loading -> LoadingScreen()
            }
        }
    }

    LifecycleStartEffect("start") {
        viewmodel.startUpdates()

        onStopOrDispose {
            viewmodel.stopUpdates()
        }
    }
}

@Composable
fun StockPriceList(stocks: List<StockListItemInfo>) {
    LazyColumn {
        itemsIndexed(stocks) { idx, item ->
            StockListItem(
                item,
                modifier = Modifier.fillMaxWidth()
            )
            if (idx < stocks.lastIndex)
                HorizontalDivider()
        }
    }
}

@Composable
fun ErrorMessage(message: String) {
    val context = LocalContext.current
    val messageText = stringResource(R.string.error_message, message)

    LaunchedEffect("error") {
        Toast.makeText(context, messageText, Toast.LENGTH_LONG).show()
    }
}

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}
