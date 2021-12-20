package com.app.bluetask.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.bluetask.model.BitCoinResponse
import com.app.bluetask.model.DataItem
import com.app.bluetask.respository.MainRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DecimalFormat

class BitcoinViewModel(private val mainRepository: MainRepository) : ViewModel() {
private val TAG="BitcoinViewModel"
    init {
        fetchBitcoin()
    }

    val bitCoinResponse= MutableLiveData<BitCoinResponse>()
    val errorResponse= MutableLiveData<String>()
    val totalCost=MutableLiveData<String>()

    fun fetchTotalCost(body: BitCoinResponse) {
        var values: Double=0.0
        for(item in body.data as ArrayList<DataItem>){
            values+=item.priceUsd!!.toDouble()
        }
        totalCost.postValue(DecimalFormat("###,###.##").format(values))
    }

    fun fetchBitcoin(){
        val call=mainRepository.getBitCoinService()
        call.enqueue(object : Callback<BitCoinResponse> {
            override fun onResponse(call: Call<BitCoinResponse>, response: Response<BitCoinResponse>) {
                if(response.isSuccessful && response.body()!=null){
                    bitCoinResponse.postValue(response.body())
                    fetchTotalCost(response.body()!!)
                }else{
                    errorResponse.postValue("You exceeded your 200 request(s) rate limit of your FREE plan")
                }
                Log.d(TAG, "onResponse: ")
            }

            override fun onFailure(call: Call<BitCoinResponse>, t: Throwable) {
                errorResponse.postValue(t.message)
                Log.d(TAG, "onFailure: ")
            }

        })
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