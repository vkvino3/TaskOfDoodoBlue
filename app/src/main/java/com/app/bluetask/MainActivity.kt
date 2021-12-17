package com.app.bluetask

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.app.bluetask.client.ApiClient
import com.app.bluetask.client.ApiInterface
import com.app.bluetask.databinding.ActivityMainBinding
import com.app.bluetask.databinding.BitcoinItemBinding
import com.app.bluetask.model.BitCoinResponse
import com.app.bluetask.model.DataItem
import com.app.bluetask.respository.MainRepository
import com.app.bluetask.viewModel.BitcoinViewModel
import com.app.bluetask.viewModel.MyViewModelFactory
import com.app.bluetask.viewModel.ViewModelClass

class MainActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {
    private val TAG = "MainActivity"
    private lateinit var binding: ActivityMainBinding
    lateinit var bitcoinViewModel: BitcoinViewModel
    private var dataItemList: ArrayList<DataItem> = ArrayList()
    private val retrofitInstance=ApiClient().getClient()

    private lateinit var adapter: BitcoinAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_main)
        bitcoinViewModel=ViewModelProvider(this,MyViewModelFactory(MainRepository(retrofitInstance.create(ApiInterface::class.java)))).get(BitcoinViewModel::class.java)

        bitcoinViewModel.fetchBitcoin().observe(this, Observer {
            Log.d(TAG, "onCreate: bit coin response ")
            val bitCoinResponse=it
            binding.swiperLayout.isRefreshing=false
            dataItemList.clear()
            dataItemList.addAll(bitCoinResponse.data as ArrayList<DataItem>)
            adapter.setDataItem(dataItemList)
            adapter.notifyDataSetChanged()
        })
        bitcoinViewModel.getErrorResponse().observe(this, Observer {
            Toast.makeText(this@MainActivity,it,Toast.LENGTH_LONG).show()
            binding.swiperLayout.isRefreshing=false
        })
        adapter=BitcoinAdapter(dataItemList)
        binding.recyclerView.setHasFixedSize(false)
        binding.recyclerView.addItemDecoration(DividerItemDecoration(this,LinearLayoutManager.VERTICAL))
        binding.recyclerView.layoutManager=LinearLayoutManager(this)
        binding.recyclerView.adapter=adapter
        binding.swiperLayout.setOnRefreshListener(this)
        binding.searchCoins.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                bitcoinViewModel.fetchSearchCoin(binding.searchCoins.text.toString()).observe(this@MainActivity, Observer {
                    Log.d(TAG, "onCreate: bit coin response "+it.size)
                    val bitCoinResponse=it
                    binding.swiperLayout.isRefreshing=false
                    dataItemList.clear()
                    dataItemList.addAll(bitCoinResponse as ArrayList<DataItem>)
                    adapter.setDataItem(dataItemList)
                    adapter.notifyDataSetChanged()
                })
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
    }

    inner class BitcoinAdapter(private var arrayList: ArrayList<DataItem>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        fun setDataItem(arrayList: ArrayList<DataItem>){
            this.arrayList=arrayList
        }

        inner class BitcoinViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val itemBinding=BitcoinItemBinding.bind(itemView.rootView)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return BitcoinViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.bitcoin_item,parent,false))
        }

        @SuppressLint("SetTextI18n")
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            if(holder is BitcoinViewHolder){
                holder.itemBinding.serialNo.text = "${position+1}"
                holder.itemBinding.item=arrayList[position]
                holder.itemBinding.viewModelClass= ViewModelClass(arrayList[position])
                holder.itemBinding.executePendingBindings()
            }
        }

        override fun getItemCount(): Int {
            return  arrayList.size
        }
    }

    override fun onRefresh() {
        binding.swiperLayout.isRefreshing=true
        bitcoinViewModel.fetchBitcoin()
    }


}