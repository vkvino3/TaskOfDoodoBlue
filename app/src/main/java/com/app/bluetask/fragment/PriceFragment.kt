package com.app.bluetask.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.app.bluetask.MainActivity
import com.app.bluetask.R
import com.app.bluetask.client.ApiClient
import com.app.bluetask.client.ApiInterface
import com.app.bluetask.databinding.BitcoinItemBinding
import com.app.bluetask.databinding.FragmentPriceBinding
import com.app.bluetask.model.DataItem
import com.app.bluetask.respository.MainRepository
import com.app.bluetask.viewModel.BitcoinViewModel
import com.app.bluetask.viewModel.MyViewModelFactory
import com.app.bluetask.viewModel.ViewModelClass

class PriceFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener  {
    private val TAG = "MainActivity"
    private lateinit var binding: FragmentPriceBinding
    lateinit var bitcoinViewModel: BitcoinViewModel
    private var dataItemList: ArrayList<DataItem> = ArrayList()

    private lateinit var adapter: BitcoinAdapter
    private val retrofitInstance= ApiClient().getClient()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=DataBindingUtil.inflate(inflater, R.layout.fragment_price,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bitcoinViewModel= ViewModelProvider(this,
            MyViewModelFactory(MainRepository(retrofitInstance.create(ApiInterface::class.java)))
        ).get(BitcoinViewModel::class.java)

        bitcoinViewModel.bitCoinResponse.observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "onCreate: bit coin response ")
            val bitCoinResponse=it
            binding.swiperLayout.isRefreshing=false
            dataItemList.clear()
            dataItemList.addAll(bitCoinResponse.data as ArrayList<DataItem>)
            adapter.setDataItem(dataItemList)
            adapter.notifyDataSetChanged()
        })
        bitcoinViewModel.errorResponse.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(),it, Toast.LENGTH_LONG).show()
            binding.swiperLayout.isRefreshing=false
        })
        adapter=BitcoinAdapter(dataItemList)
        binding.recyclerView.setHasFixedSize(false)
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(requireContext(),
                LinearLayoutManager.VERTICAL)
        )
        bitcoinViewModel.totalCost.observe(viewLifecycleOwner, Observer {
            binding.goldMarket.text="Global Market Cap : $it"
        })
        binding.recyclerView.layoutManager= LinearLayoutManager(requireContext())
        binding.recyclerView.adapter=adapter
        binding.swiperLayout.setOnRefreshListener(this)
        binding.searchCoins.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                bitcoinViewModel.getSearchCoin(binding.searchCoins.text.toString()).observe(requireActivity(), Observer {
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
            val itemBinding= BitcoinItemBinding.bind(itemView.rootView)
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