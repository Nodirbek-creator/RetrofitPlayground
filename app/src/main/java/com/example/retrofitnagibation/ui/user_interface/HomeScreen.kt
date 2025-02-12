package com.example.retrofitnagibation.ui.user_interface

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.size.Scale
import com.example.retrofitnagibation.model.Product
import com.example.retrofitnagibation.network.ApiService
import kotlinx.coroutines.delay
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.itteacher.myapi.model.Products

@Composable
fun HomeScreen(
    navController: NavHostController,
    apiService: ApiService,
) {
    var products:List<Product> = emptyList()

    apiService.getProducts().enqueue(object : Callback<Products> {
        override fun onResponse(
            call: Call<Products?>,
            response: Response<Products?>
        ) {
            if (response.isSuccessful) {
                products = (response.body()!!.products)
            }
        }

        override fun onFailure(
            call: Call<Products?>,
            t: Throwable
        ) {
            Log.d("TAG", "onFailure: ${t.message}")
        }

    })
    var isLoading by remember { mutableStateOf(false) }

    LaunchedEffect(isLoading) {
        delay(1000)
        if (products.isNotEmpty()) {
            isLoading = true
        }
    }
    Scaffold(
        contentWindowInsets = WindowInsets.systemBars
    ) {pad->
        Column(
            modifier = Modifier.fillMaxSize().padding(pad),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth().fillMaxHeight(0.7f),
            ) {
                items(products){
                    ProductCard(
                        product = it,
                        onClick = {
                            /*todo: move to next screen*/
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ProductCard(
    product: Product,
    onClick:() ->Unit){
    Card(
        onClick = {onClick()},
        modifier = Modifier.width(600.dp).height(300.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                modifier = Modifier.fillMaxWidth().height(200.dp).clip(RoundedCornerShape(16.dp)),
                model = ImageRequest.Builder(LocalContext.current)
                    .memoryCachePolicy(CachePolicy.ENABLED)
                    .diskCachePolicy(CachePolicy.ENABLED)
                    .data(product.images[0])
                    .size(300,200)
                    .scale(Scale.FILL)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Fit,

                )
            Spacer(Modifier.height(12.dp))
            Text(
                text = product.title,
                fontSize = 18.sp,
            )
        }
    }
}