package com.app.bluetask.viewModel

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.app.bluetask.model.DataItem
import java.text.DecimalFormat

class ViewModelClass(val dataItem: DataItem) : BaseObservable() {

    @Bindable
    fun getPrice(): String{
        return "$${dataItem.priceUsd!!}"
    }

    @Bindable
    fun getChangePrice(): String{
       //return dataItem.changePercent24Hr!!
        return String.format("%.2f",dataItem.changePercent24Hr!!.toFloat())
    }



}