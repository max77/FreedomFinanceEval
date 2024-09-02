package com.max77.freedomfinanceeval.repository.prices.network

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive

fun JsonElement?.equalsToString(s: String) =
    (this as? JsonPrimitive)?.let { it.isString && it.content == s } ?: false