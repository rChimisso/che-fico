package com.kreinto.chefico.views.camera

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.IOException
import java.util.*


class PlantRecognition {

  data class PlantSpeciesData(
    @SerializedName("scientificName") val scientificName: String? = null,
    @SerializedName("family") val family: PlantFamilyData? = null,
    @SerializedName("commonNames") val commonNames: List<String>? = null,
  )

  data class PlantFamilyData(
    @SerializedName("scientificName") val scientificName: String? = null
  )

  data class PlantResultsData(
    @SerializedName("score") val score: Double? = null,
    @SerializedName("species") val species: PlantSpeciesData? = null,
  )

  data class PlantRecognitionData(
    @SerializedName("bestMatch") var bestMatch: String? = null,
    @SerializedName("results") var results: List<PlantResultsData>? = null
  ) {
    fun isValid() = bestMatch != null && results != null
  }

  companion object {
    private const val apiUrl =
      "https://my-api.plantnet.org/v2/identify/all?api-key=2b10sKsQmbu8L0oorDT3I09UO&lang=it"
    val InvalidData = PlantRecognitionData(null, null)

    fun recognize(file: File, onResult: (result: PlantRecognitionData) -> Unit) {
      val client = OkHttpClient()
      val formBody = MultipartBody.Builder()
        .setType(MultipartBody.FORM)
        .addFormDataPart(
          "images",
          file.name,
          file.asRequestBody("image/jpeg".toMediaType())
        )
        .build()
      val request = Request.Builder()
        .header("content-type", "multipart/form-data;")
        .url(apiUrl)
        .post(formBody)
        .build()
      val call = client.newCall(request)
      call.enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
          e.printStackTrace()
        }

        override fun onResponse(call: Call, response: Response) {
          val gson = Gson()
          response.body?.string()?.let {
            onResult(
              gson.fromJson(
                it,
                PlantRecognitionData::class.java
              )
            )
          }
        }
      })
    }
  }
}
