package com.fgomes.trademap_clone.extension

import java.text.NumberFormat
import java.util.*

fun Double?.formatarMoeda(): String = NumberFormat.getCurrencyInstance(Locale("pt", "BR")).format(this) ?: "R$ 0,00"