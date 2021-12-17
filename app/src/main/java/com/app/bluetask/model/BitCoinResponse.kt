package com.app.bluetask.model

import com.google.gson.annotations.SerializedName

data class BitCoinResponse(

	@field:SerializedName("data")
	val data: List<DataItem?>? = null,

	@field:SerializedName("timestamp")
	val timestamp: Long? = null
)

data class DataItem(

	@field:SerializedName("symbol")
	val symbol: String? = null,

	@field:SerializedName("volumeUsd24Hr")
	val volumeUsd24Hr: String? = null,

	@field:SerializedName("marketCapUsd")
	val marketCapUsd: String? = null,

	@field:SerializedName("priceUsd")
	val priceUsd: String? = null,

	@field:SerializedName("vwap24Hr")
	val vwap24Hr: String? = null,

	@field:SerializedName("changePercent24Hr")
	val changePercent24Hr: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("explorer")
	val explorer: String? = null,

	@field:SerializedName("rank")
	val rank: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("maxSupply")
	val maxSupply: String? = null,

	@field:SerializedName("supply")
	val supply: String? = null
)
