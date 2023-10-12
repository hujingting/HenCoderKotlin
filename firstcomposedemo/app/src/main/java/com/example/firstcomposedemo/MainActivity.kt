package com.example.firstcomposedemo

import android.media.Image
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val list = mutableListOf<Message>()
            list.add(Message("Android", "Jetpack Compose"))
            list.add(Message("iOS", "Swift"))
            list.add(Message("windows", "C"))
            list.add(Message("Mac OS", "OC"))
            list.add(Message("Mac OS", "OC"))
            list.add(Message("Mac OS", "OC"))
            list.add(Message("Mac OS", "OC"))
            list.add(Message("Mac OS", "OC"))
            list.add(Message("Mac OS", "OC"))
            list.add(Message("Mac OS", "OC"))
            list.add(Message("Mac OS", "OC"))
//            Conversions(messages = list)
//            Greeting(Message("Android", "Jetpack Compose"))
        }
    }
}

@Composable
fun Conversions(messages: List<Message>) {
    LazyColumn {

        items(messages) { message ->
            Greeting(msg = message)
        }
    }
}

@Preview
@Composable
fun PreviewConversation() {

}

data class Message(val name : String, val des: String)

@Composable
fun Greeting(msg: Message) {

    Row(
        Modifier
            .fillMaxSize()
            .padding(all = 8.dp)
            .background(Color.White)) {

        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = "test image",
            modifier = Modifier
                .padding(8.dp)
                .size(80.dp)
                .clip(CircleShape)
                .clickable {

                }
        )
        
        Spacer(modifier = Modifier.width(28.dp))

        Column() {
            Text(
                text = msg.name,color = Color.Red)
            Spacer(modifier = Modifier.width(14.dp))
            Text(text = msg.des)
            
            Button(onClick = { /*TODO*/ }) {
                Text(text = "test")
            }
        }
    }
}