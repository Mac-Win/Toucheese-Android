package com.toucheese.app

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import com.toucheese.app.ui.theme.ToucheeseAppTheme
import com.toucheese.app.data.network.HomeService
import com.toucheese.app.ui.navigation.ToucheeseApp
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var myAPI: HomeService

    @RequiresApi(Build.VERSION_CODES.O)
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
