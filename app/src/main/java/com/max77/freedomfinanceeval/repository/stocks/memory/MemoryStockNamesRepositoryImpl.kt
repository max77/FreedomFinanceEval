package com.max77.freedomfinanceeval.repository.stocks.memory

import com.max77.freedomfinanceeval.repository.stocks.StockName
import com.max77.freedomfinanceeval.repository.stocks.StockNamesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class MemoryStockNamesRepositoryImpl : StockNamesRepository {
    override val stockNamesFlow: Flow<List<StockName>> = flowOf(
        listOf(
            "SP500.IDX",
            "AAPL.US",
            "RSTI",
            "GAZP",
            "MRKZ",
            "RUAL",
            "HYDR",
            "MRKS",
            "SBER",
            "FEES",
            "TGKA",
            "VTBR",
            "ANH.US",
            "VICL.US",
            "BU RG.US",
            "NBL.US",
            "YETI.US",
            "WSFS.US",
            "NIO.US",
            "DXC.US",
            "MIC.US",
            "HSBC.US",
            "EXPN.EU",
            "GSK.EU",
            "SHP.EU",
            "MAN.EU ",
            "DB1.EU",
            "MUV2.EU",
            "TATE.EU",
            "KGF.EU",
            "MGGT.EU",
            "SGGD.EU"
        )
    )
}