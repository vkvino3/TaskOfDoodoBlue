package com.app.bluetask.viewModel

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.bluetask.model.BitCoinResponse
import com.app.bluetask.respository.MainRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BitcoinViewModel(private val mainRepository: MainRepository) : ViewModel() {

    fun fetchBitcoin(): MutableLiveData<BitCoinResponse> {
        return mainRepository.getBitcoin()
    }
}