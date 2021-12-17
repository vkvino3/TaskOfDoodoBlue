package com.app.bluetask.respository

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.app.bluetask.client.ApiClient

import com.app.bluetask.client.ApiInterface
import com.app.bluetask.model.BitCoinResponse
import com.app.bluetask.model.DataItem
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainRepository(private val apiInterface: ApiInterface) {
    private val bitCoinResponse= MutableLiveData<BitCoinResponse>()
    private val errorResponse= MutableLiveData<String>()
    fun getBitcoin(): MutableLiveData<BitCoinResponse> {
        apiInterface.getBitcoinList().enqueue(object : Callback<BitCoinResponse> {
            override fun onResponse(call: Call<BitCoinResponse>, response: Response<BitCoinResponse>) {
                if(response.isSuccessful && response.body()!=null){
                    bitCoinResponse.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<BitCoinResponse>, t: Throwable) {
                errorResponse.postValue(t.message)
            }

        })
        return bitCoinResponse
    }

    fun getErrorResponse(): MutableLiveData<String> {
        return errorResponse
    }

    fun getSearchCoin(data: String) : MutableLiveData<ArrayList<DataItem>> {
        val dataItemList=ArrayList<DataItem>()
        val dataItemMutableLiveData: MutableLiveData<ArrayList<DataItem>> = MutableLiveData()
        if(data.isNotEmpty()){
           for (item in bitCoinResponse.value!!.data as ArrayList<DataItem>){
               if(item.name!!.contains(data,true))
                   dataItemList.add(item)
           }
            dataItemMutableLiveData.postValue(dataItemList)
        }else{
            dataItemMutableLiveData.postValue(bitCoinResponse.value?.data as ArrayList<DataItem>)
        }

        Log.d("TAG", "getSearchCoin: ${dataItemList.size}")

        return dataItemMutableLiveData
    }

}