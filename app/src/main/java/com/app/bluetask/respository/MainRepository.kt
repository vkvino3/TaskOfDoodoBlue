package com.app.bluetask.respository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.app.bluetask.client.ApiClient

import com.app.bluetask.client.ApiInterface
import com.app.bluetask.model.BitCoinResponse
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainRepository(private val apiInterface: ApiInterface) {
    private val bitCoinResponse= MutableLiveData<BitCoinResponse>()
    fun getBitcoin(): MutableLiveData<BitCoinResponse> {
        apiInterface.getBitcoinList().enqueue(object : Callback<BitCoinResponse> {
            override fun onResponse(call: Call<BitCoinResponse>, response: Response<BitCoinResponse>) {
                if(response.isSuccessful && response.body()!=null){
                    bitCoinResponse.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<BitCoinResponse>, t: Throwable) {

            }

        })
        return bitCoinResponse
    }

}