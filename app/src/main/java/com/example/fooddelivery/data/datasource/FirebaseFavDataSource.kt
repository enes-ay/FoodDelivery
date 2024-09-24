package com.example.fooddelivery.data.datasource

import androidx.lifecycle.MutableLiveData
import com.example.fooddelivery.data.model.Yemekler
import com.google.firebase.firestore.CollectionReference

class FirebaseFavDataSource (val collectionFavFoods: CollectionReference) {

    var favFoodsList = MutableLiveData<List<Yemekler>>()

    fun saveFavFood(yemekAdi : String, yemekFiyat: Int, yemekId: Int, yemekResimAdi: String){
        val newFood = Yemekler(yemekAdi, yemekFiyat, yemekId, yemekResimAdi)
        collectionFavFoods.document().set(newFood)
    }

    fun deleteFavFood(yemekId: String){
        collectionFavFoods.document(yemekId).delete()
    }

    fun getFavFoods() : MutableLiveData<List<Yemekler>>{
        collectionFavFoods.addSnapshotListener { value, error ->
            if(value!=null){
                val favList = ArrayList<Yemekler>()

                for (d in value.documents){
                    val food = d.toObject(Yemekler::class.java)
                    if(food!=null){
                        food.yemek_id = d.id.toInt()
                        favList.add(food)
                    }
                }
                favFoodsList.value = favList
            }
        }
        return favFoodsList
    }
}