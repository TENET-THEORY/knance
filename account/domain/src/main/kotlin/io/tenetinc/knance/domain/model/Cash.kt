package io.tenetinc.knance.domain.model

data class Cash(
    val id: Int? = null,
    val amount: Float,
    val currency: String,
    val usdValue: Float? = null,
)
