package com.app.bluetask.viewModel

import android.os.Build
import android.text.Html
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.app.bluetask.model.DataItem
import java.text.DecimalFormat

class ViewModelClass(val dataItem: DataItem) : BaseObservable() {

    @Bindable
    fun getPrice(): String{
        return "$${DecimalFormat("###,###.##").format(dataItem.priceUsd!!.toDouble())}"
       // return "$${dataItem.priceUsd!!}"
    }

    @Bindable
    fun getChangePrice(): String{
       //return dataItem.changePercent24Hr!!
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            String.format("%.2f",dataItem.changePercent24Hr!!.toFloat())+Html.fromHtml("&#37;",Html.FROM_HTML_MODE_LEGACY)
        } else {
            String.format("%.2f",dataItem.changePercent24Hr!!.toFloat())+Html.fromHtml("&#37;")
        }
    }



}