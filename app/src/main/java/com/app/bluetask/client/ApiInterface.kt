package com.app.bluetask.client

import com.app.bluetask.model.BitCoinResponse
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {

    @GET("v2/assets")
    fun getBitcoinList(): Call<BitCoinResponse>

}