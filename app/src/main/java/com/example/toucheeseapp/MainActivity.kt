package com.example.toucheeseapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.toucheeseApp.ui.theme.ToucheeseAppTheme
import com.example.toucheeseapp.data.network.ToucheeseServer
import com.example.toucheeseapp.ui.navigation.ToucheeseApp
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var myAPI: ToucheeseServer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            ToucheeseAppTheme {
                    ToucheeseApp(myAPI)
            }
        }
    }
}
