package uz.itteacher.myapi.model

import com.example.retrofitnagibation.model.Product

data class Products(
    val limit: Int,
    val products: List<Product>,
    val skip: Int,
    val total: Int
)