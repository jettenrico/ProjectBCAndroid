package com.bcafinance.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentTransaction
import com.bcafinance.myapplication.service.NetworkConfig
import com.bcafinance.myapplication.ICallBackNetwork
import com.bcafinance.myapplication.fragment.DetailOrder
import com.bcafinance.myapplication.fragment.ListOrder
import com.bcafinance.myapplication.model.OMDBDetailResponse
import com.bcafinance.myapplication.model.OMDBResponse
import com.bcafinance.myapplication.model.SearchItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showListMovieFragment()
    }

    fun showListMovieFragment(){
        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        ft.add(R.id.frameFragmen, ListOrder.newInstance("",""),"list")
        ft.commit()
    }

    fun searchMovie(title:String, callbackNetwork : ICallBackNetwork) {

        var data : List<SearchItem>? = null
        NetworkConfig().getServiceOMDB().searchMovie(title).enqueue(object :
            Callback<OMDBResponse> {
            override fun onResponse(call: Call<OMDBResponse>, response: Response<OMDBResponse>) {
                Log.d("Response OMDB APi search", response.toString())

                if(response.body()?.search!=null) {
                    data = (response.body()?.search as List<SearchItem>)
                    callbackNetwork.onFinish(data!!)
                }
            }

            override fun onFailure(call: Call<OMDBResponse>, t: Throwable) {
                Log.e("Failed Response", t.message.toString())
                callbackNetwork.onFailed()
            }

        })
    }

    fun searchMoviebyId(idMovie:String, callbackNetwork: DetailOrder) {

        NetworkConfig().getServiceOMDB().searchMoviebyId(idMovie).enqueue(object : Callback<OMDBDetailResponse>{
            override fun onResponse(
                call: Call<OMDBDetailResponse>,
                response: Response<OMDBDetailResponse>
            ) {
                if(response.body() !=null) {
                    callbackNetwork.onFinishDetail(response.body()as OMDBDetailResponse)
                }
            }

            override fun onFailure(call: Call<OMDBDetailResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

}