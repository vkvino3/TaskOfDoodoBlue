package com.app.bluetask.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.bluetask.respository.MainRepository

class MyViewModelFactory constructor(private val mainRepository: MainRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(BitcoinViewModel::class.java)) {
            BitcoinViewModel(this.mainRepository) as T
        } else {
            throw IllegalArgumentException("View not found")
        }
    }
}