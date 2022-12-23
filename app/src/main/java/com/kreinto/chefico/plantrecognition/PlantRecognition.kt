package com.kreinto.chefico.plantrecognition

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.IOException


class PlantRecognition {

  class PlantResult {
    val commonNames: List<String> = emptyList()
  }

  companion object {
    const val apiUrl =
      "https://my-api.plantnet.org/v2/identify/all?api-key=2b10sKsQmbu8L0oorDT3I09UO&lang=it"

    fun recognize(file: File, onResult: (result: Map<String, Any>) -> Unit) {
      val client = OkHttpClient()
      val formBody = MultipartBody.Builder()
        .setType(MultipartBody.FORM)
        .addFormDataPart(
          "images",
          file.name,
          file.asRequestBody("image/jpeg".toMediaType())
        )
        .build()

      println(file.name)
      println(file.asRequestBody("image/jpeg".toMediaType()).contentLength())
      println(file.asRequestBody("image/jpeg".toMediaType()).contentType())

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
          response.body?.string()
            ?.let { onResult(gson.fromJson(it, object : TypeToken<Map<String, Any>>() {}.type)) }
        }
      })
    }
  }
}
