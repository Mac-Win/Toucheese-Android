package com.example.toucheeseapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.toucheeseapp.data.network.RetrofitClient
import com.example.toucheeseapp.data.network.ToucheeseServer
import com.example.toucheeseapp.ui.navigation.ToucheeseApp
import com.example.toucheeseapp.ui.theme.ToucheeseAppTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {

    lateinit var myAPI: ToucheeseServer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // RetrofitClient instance 호출
        val retrofit = RetrofitClient.getInstance()
        // Retrofit이 Interface를 구현
        myAPI = retrofit.create(ToucheeseServer::class.java)


        setContent {
            ToucheeseAppTheme {
                Surface {
                    ToucheeseApp(myAPI)
                }
            }
        }
    }
}
