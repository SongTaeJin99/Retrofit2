package com.example.retrofit2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.retrofit2.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var retrofit: INetworkService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setinit()

        binding.btn.setOnClickListener {
            getData()
        }

    }
    private fun setinit() {
        retrofit = RetrofitConnection.getRetrofit().create(INetworkService::class.java)
    }
    private fun getData() {
        val value = binding.edt.text
        val userCall = retrofit.doGetUser(value.toString())

        userCall.enqueue(object : Callback<UserModel> {
            override fun onResponse(call: Call<UserModel>, response: Response<UserModel>) {

                val user = response.body()
                Toast.makeText(this@MainActivity, "데이터 호출에 성공하셨습니다.", Toast.LENGTH_SHORT).show()
                binding.resultTv.text =
                    "성 : ${user?.data?.lastName} 이름 : ${user?.data?.firstName} \n이메일 : ${user?.data?.email}"

            }

            override fun onFailure(call: Call<UserModel>, t: Throwable) {

                Toast.makeText(this@MainActivity, "데이터 호출에 실패하였습니다.", Toast.LENGTH_SHORT).show()
                call.cancel()

            }

        })
    }

}