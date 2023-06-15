package com.kreinto.chefico.views.camera

import android.graphics.Bitmap
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.*

/**
 * Handle plant recognition.
 *
 */
class PlantRecognition {
  object PlantOrgan {
    const val leaf: String = "leaf"
    const val flower: String = "flower"
    const val fruit: String = "fruit"
  }

  private data class WikiMediaQueryData(
    @SerializedName("query") val query: WikiMediaPages? = null
  )

  private data class WikiMediaPages(
    @SerializedName("pages") val pages: List<PlantDescriptionData>? = null
  )

  data class PlantDescriptionData(
    @SerializedName("fullurl") val fullUrl: String? = null,
    @SerializedName("extract") val extract: String? = null,
    @SerializedName("title") val title: String? = null,
  )

  class PlantSpeciesData(
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
    @SerializedName("results") var results: List<PlantResultsData>? = null
  ) {
    fun isValid() = results != null
  }

  companion object {
    private const val plantNetApi =
      "https://my-api.plantnet.org/v2/identify/all?api-key=2b10sKsQmbu8L0oorDT3I09UO&lang=it"
    val InvalidData = PlantRecognitionData(null)
    private const val wikiMediaApi = "https://it.wikipedia.org/w/api.php"

    fun fetchPlantDescription(
      plantName: String,
      onResult: (url: String, result: List<PlantDescriptionData>) -> Unit
    ) {
      OkHttpClient().newCall(
        Request.Builder()
          .url(wikiMediaApi)
          .post(
            FormBody.Builder()
              .add("action", "query")
              .add("format", "json")
              .add("prop", "categoryinfo|info|extracts")
              .add("generator", "search")
              .add("redirects", "1")
              .add("utf8", "1")
              .add("formatversion", "2")
              .add("inprop", "url")
              .add("exsentences", "5")
              .add("exlimit", "1")
              .add("exintro", "1")
              .add("explaintext", "1")
              .add("exsectionformat", "plain")
              .add("gsrsearch", plantName)
              .add("gsrlimit", "1")
              .build()
          )
          .build()
      ).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
          e.printStackTrace()
        }

        override fun onResponse(call: Call, response: Response) {
          val gson = Gson()
          val url = response.request.url.toUrl().toString()
          response.body?.string()?.let {
            val result = gson.fromJson(
              it,
              WikiMediaQueryData::class.java
            )

            if (result.query?.pages != null) {
              onResult(url, result.query.pages)
            } else {
              onResult(url, listOf())
            }
          }
        }
      })
    }

    fun recognize(image: Bitmap, organ: String, onResult: (result: PlantRecognitionData) -> Unit) {
      val stream = ByteArrayOutputStream()
      image.compress(Bitmap.CompressFormat.JPEG, 100, stream)

      val client = OkHttpClient()
      val formBody = MultipartBody.Builder()
        .setType(MultipartBody.FORM)
        .addFormDataPart(
          "images",
          "plant.jpg",
          stream.toByteArray().toRequestBody("image/jpg".toMediaTypeOrNull(), 0, stream.size())
        )
        .addFormDataPart(
          "organs",
          organ,
        )
        .build()
      val request = Request.Builder()
        .header("content-type", "multipart/form-data;")
        .url(plantNetApi)
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
            println(it)

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
