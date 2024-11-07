package com.example.stress.modal

data class HomeIssueList(
    val name: String = "",
    val imageurl: String=""
)


// Exercise.kt
data class Exer(
    val name: String = "", // For exercise name
)

// Food.kt
data class Food(
    val name: String = "" // For food name
)



data class ExerDetail(
    val name:String="",
    val imageurl: String="",
    val description: String=""
)


data class FoodDetail(
    val name:String="",
    val imageurl: String="",
    val eatingtime: String=""
    )

data class GraphDetails(
    val name: String="",
    val definition: String = "",
    val imageurl: String = "",
    val phyexercises: List<Exer> = listOf(),
    val menexercises: List<Exer> = listOf(),
    val foodsVeg: List<Food> = listOf(),
    val foodsNonVeg: List<Food> = listOf()
)

