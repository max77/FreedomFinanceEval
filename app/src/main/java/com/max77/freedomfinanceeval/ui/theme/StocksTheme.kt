package com.max77.freedomfinanceeval.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Immutable
data class StocksColors(
    val percentUpColor: Color,
    val percentDownColor: Color,
    val percentDefaultColor: Color,
    val primary: Color,
    val secondary: Color,
)

@Immutable
data class StocksShapes(
    val percentHighlightShape: Shape
)

@Immutable
data class StocksTypography(
    val tickerStyle: TextStyle,
    val tickerSubtitleStyle: TextStyle,
    val percentSubtitleStyle: TextStyle,
    val percentStyle: TextStyle,
)

val LocalStocksColors = staticCompositionLocalOf {
    StocksColors(
        percentUpColor = Color.Unspecified,
        percentDownColor = Color.Unspecified,
        percentDefaultColor = Color.Unspecified,
        primary = Color.Unspecified,
        secondary = Color.Unspecified,
    )
}

val LocalStocksShapes = staticCompositionLocalOf {
    StocksShapes(
        percentHighlightShape = RoundedCornerShape(ZeroCornerSize)
    )
}

val LocalStocksTypography = staticCompositionLocalOf {
    StocksTypography(
        tickerStyle = TextStyle.Default,
        tickerSubtitleStyle = TextStyle.Default,
        percentSubtitleStyle = TextStyle.Default,
        percentStyle = TextStyle.Default,
    )
}


@Composable
fun StocksTheme(
    content: @Composable () -> Unit
) {
    val stocksColors = StocksColors(
        percentUpColor = Color(0xFF84BD55),
        percentDownColor = Color(0xFFE8445B),
        percentDefaultColor = Color.White,
        primary = Color.DarkGray,
        secondary = Color.Gray
    )

    val stocksShapes = StocksShapes(
        percentHighlightShape = RoundedCornerShape(6.dp)
    )

    val stockTypography = StocksTypography(
        tickerStyle = MaterialTheme.typography.bodyMedium.copy(
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        ),
        tickerSubtitleStyle = MaterialTheme.typography.labelMedium,
        percentSubtitleStyle = MaterialTheme.typography.labelMedium,
        percentStyle = MaterialTheme.typography.bodyMedium.copy(
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        ),
    )

    CompositionLocalProvider(
        LocalStocksColors provides stocksColors,
        LocalStocksShapes provides stocksShapes,
        LocalStocksTypography provides stockTypography,
    ) {
        MaterialTheme(
            content = content
        )
    }
}

object StocksTheme {
    val colors
        @Composable
        get() = LocalStocksColors.current

    val shapes
        @Composable
        get() = LocalStocksShapes.current

    val typography
        @Composable
        get() = LocalStocksTypography.current
}