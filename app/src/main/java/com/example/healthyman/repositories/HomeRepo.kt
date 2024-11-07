package com.example.healthyman.repositories

import android.util.Log
import com.example.stress.modal.Exer
import com.example.stress.modal.ExerDetail
import com.example.stress.modal.Food
import com.example.stress.modal.FoodDetail
import com.example.stress.modal.GraphDetails
import com.example.stress.modal.HomeIssueList
import com.google.firebase.firestore.FirebaseFirestore

class HomeRepo {
    private val firestore = FirebaseFirestore.getInstance()

    fun getHomeIssueList(callback: (List<HomeIssueList>) -> Unit){
        firestore.collection("main")
            .get()
            .addOnSuccessListener {result ->
            val lis = result.map { document ->

                    val imgurl = document.getString("imageurl") ?: ""

                    HomeIssueList(name = document.id, imageurl = imgurl)


            }
                callback(lis)

            }
    }


    fun getphyExer(issueName:String,exerciseNumber:String, callback: (ExerDetail) -> Unit){
        val documentRef = firestore.collection("main").document(issueName)
            .collection("phyexercises").document(exerciseNumber)


        documentRef.get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    // Get field values
                    val name = documentSnapshot.getString("name") ?: "No name"
                    val description = documentSnapshot.getString("description") ?: "No definition"
                    val imageUrl = documentSnapshot.getString("imageurl") ?: "No image URL"


                    // Log or display values as needed
                    Log.d("pp", "Name: $name")
                    Log.d("pp", "description  : $description")
                    Log.d("pp", "Image URL: $imageUrl")
                    callback(ExerDetail(name,imageUrl,description))
                } else {
                    Log.d("pp", "No such document")
                }
            }
            .addOnFailureListener { e ->
                Log.e("pp", "Error fetching document", e)
            }
    }

    fun getmenExer(issueName:String,exerciseNumber:String, callback: (ExerDetail) -> Unit){
        val documentRef = firestore.collection("main").document(issueName)
            .collection("menexercises").document(exerciseNumber)


        documentRef.get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    // Get field values
                    val name = documentSnapshot.getString("name") ?: "No name"
                    val description = documentSnapshot.getString("description") ?: "No definition"
                    val imageUrl = documentSnapshot.getString("imageurl") ?: "No image URL"


                    // Log or display values as needed
                    Log.d("pp", "Name: $name")
                    Log.d("pp", "description  : $description")
                    Log.d("pp", "Image URL: $imageUrl")
                    callback(ExerDetail(name,imageUrl,description))
                } else {
                    Log.d("pp", "No such document")
                }
            }
            .addOnFailureListener { e ->
                Log.e("pp", "Error fetching document", e)
            }
    }


    fun getfoodsVeg(issueName:String,foodNumber:String, callback: (FoodDetail) -> Unit){
        val documentRef = firestore.collection("main").document(issueName)
            .collection("foodsVeg").document(foodNumber)


        documentRef.get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    // Get field values
                    val name = documentSnapshot.getString("name") ?: "No name"
                    val eatingtime = documentSnapshot.getString("eatingtime") ?: "No eatingtime"
                    val imageUrl = documentSnapshot.getString("imageurl") ?: "No image URL"


                    // Log or display values as needed
                    Log.d("pp", "Name: $name")
                    Log.d("pp", "eatingtime  : $eatingtime")
                    Log.d("pp", "Image URL: $imageUrl")
                    callback(FoodDetail(name,imageUrl,eatingtime))
                } else {
                    Log.d("pp", "No such document")
                }
            }
            .addOnFailureListener { e ->
                Log.e("pp", "Error fetching document", e)
            }
    }

    fun getfoodsNonVeg(issueName:String,foodNumber:String, callback: (FoodDetail) -> Unit){
        val documentRef = firestore.collection("main").document(issueName)
            .collection("foodsNonVeg").document(foodNumber)


        documentRef.get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    // Get field values
                    val name = documentSnapshot.getString("name") ?: "No name"
                    val eatingtime = documentSnapshot.getString("eatingtime") ?: "No eatingtime"
                    val imageUrl = documentSnapshot.getString("imageurl") ?: "No image URL"


                    // Log or display values as needed
                    Log.d("pp", "Name: $name")
                    Log.d("pp", "eatingtime  : $eatingtime")
                    Log.d("pp", "Image URL: $imageUrl")
                    callback(FoodDetail(name,imageUrl,eatingtime))
                } else {
                    Log.d("pp", "No such document")
                }
            }
            .addOnFailureListener { e ->
                Log.e("pp", "Error fetching document", e)
            }
    }




    fun getGraphDetails(disorderName: String, callback: (GraphDetails) -> Unit) {
        val documentRef = firestore.collection("main").document(disorderName)

        documentRef.get().addOnSuccessListener { document ->
            if (document != null) {
                val nname = document.getString("name") ?: ""
                val definition = document.getString("definition") ?: ""
                val imgurl = document.getString("imageurl") ?: ""
//                val physicalExercises = mutableListOf<Exer>()

                documentRef.collection("phyexercises").get().addOnSuccessListener { result ->
                    val physicalExercises = result.map {doc ->
                        Exer(name = doc.id)
                    }
//                    for (exerciseDoc in result) {
//                        physicalExercises.add(Exer(exerciseDoc.getString("name") ?: ""))
//                    }


//                    val mentalExercises = mutableListOf<Exer>()
                    documentRef.collection("menexercises").get().addOnSuccessListener { result2 ->
//                        for (exerciseDoc in result2) {
//                            mentalExercises.add(Exer(exerciseDoc.getString("name") ?: ""))
//                        }
                        val mentalExercises = result2.map{doc ->
                            Exer(name = doc.id)

                        }

                        documentRef.collection("foodsVeg").get().addOnSuccessListener { result3 ->
                            val foodsVeg = result3.map { doc ->
                                Food(name = doc.id)
                            }

                            documentRef.collection("foodsNonVeg").get().addOnSuccessListener { result4 ->
                                val foodsNonVeg = result4.map { doc ->
                                    Food(name = doc.id)
                                }

                                val disorderDetails = GraphDetails(
                                    name = nname,
                                    definition = definition,
                                    imageurl = imgurl,
                                    phyexercises = physicalExercises,
                                    menexercises = mentalExercises,
                                    foodsVeg = foodsVeg,
                                    foodsNonVeg = foodsNonVeg
                                )
                                Log.d("ff","dat = $disorderDetails")
                                callback(disorderDetails)
                            }
                        }
                    }
                }
            }
        }
    }

}