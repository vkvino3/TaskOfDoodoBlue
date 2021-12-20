package com.app.bluetask.respository

import com.app.bluetask.client.ApiInterface


class MainRepository(private val apiInterface: ApiInterface) {

    fun getBitCoinService() =apiInterface.getBitcoinList()

}