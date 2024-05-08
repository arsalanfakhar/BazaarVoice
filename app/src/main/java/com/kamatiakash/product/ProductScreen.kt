package com.kamatiakash.product

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kamatiakash.speech_to_text_in_compose.home.MainViewModel
import com.kamatiakash.speech_to_text_in_compose.model.VoiceDataDto
import com.kamatiakash.speech_to_text_in_compose.model.VoiceResponseDto
import com.kamatiakash.speech_to_text_in_compose.model.VoiceResponseTypes

// Sample data class representing a product
data class ScreenData(
    val firstData: String,
    val secondData: String,
    val thirdData: String
)

@Composable
fun ProductScreen(data: VoiceResponseDto,mainViewModel: MainViewModel) {

    var screenTitle = ""
    var dataList: List<ScreenData> = emptyList()

    if (data.type == VoiceResponseTypes.product) {

        screenTitle = "Best deals"

        dataList = data.data.map {
            ScreenData(it.productName, it.description, it.price)
        }


    } else if (data.type == VoiceResponseTypes.order_status) {
        screenTitle = "Order Status"

        dataList = data.data.map {
            ScreenData(it.orderNumber, it.status, it.expectedDelivery)
        }
    }




    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(screenTitle) },
                backgroundColor = Color.Blue
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Products",
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            ProductList(products = dataList)
        }
    }

}

@Composable
fun ProductList(products: List<ScreenData>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(products) { product ->
            ProductItem(product = product)
        }
    }
}

@Composable
fun ProductItem(product: ScreenData) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = product.firstData,
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = product.secondData,
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = product.thirdData,
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(bottom = 4.dp)
            )
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun PreviewProductScreen() {
//    val products = listOf(
//        Product(
//            name = "Product 1",
//            title = "Title 1",
//            description = "Description 1",
//            price = 19.99
//        ),
//        Product(
//            name = "Product 2",
//            title = "Title 2",
//            description = "Description 2",
//            price = 29.99
//        ),
//        Product(
//            name = "Product 3",
//            title = "Title 3",
//            description = "Description 3",
//            price = 39.99
//        )
//    )
//    ProductScreen(products = products)
//}