package com.nanaya.adjump

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nanaya.adjump.ui.theme.ADJumpTheme

class MainActivity : ComponentActivity() {
  private var isServiceEnabled by mutableStateOf(false)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      ADJumpTheme {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
          Box(modifier = Modifier.fillMaxSize()){
            MainPage(modifier=Modifier.align(Alignment.Center))
          }

        }
      }
    }
  }

  @Preview
  @Composable
  fun Preview(){
    MainPage(Modifier)
  }


  @Composable
  fun MainPage(modifier: Modifier){
    Row(modifier=modifier,verticalAlignment = Alignment.CenterVertically) {
      Text(text = "服务状态", fontSize = 24.sp)
      Spacer(modifier =Modifier.width(4.dp))
      Switch(checked = isServiceEnabled, onCheckedChange ={
        if(it){
          startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
        }
      } )
    }
  }

  override fun onResume() {
    super.onResume()
    isServiceEnabled=ADJumpService.isEnabled
  }
}
