package be.marche.pinpoint.network

import be.marche.pinpoint.data.Coordinates
import be.marche.pinpoint.data.DataResponse
import be.marche.pinpoint.entity.Category
//import kotlinx.serialization.json.Json
import retrofit2.Retrofit
import retrofit2.http.GET
//import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
//import okhttp3.MediaType.Companion.toMediaType
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

private const val BASE_URL =
    "https://apptravaux.marche.be/"

/**
 * Use the Retrofit builder to build a retrofit object using a kotlinx.serialization converter
 */
private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    //.addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .build()

/**
 * Retrofit service object for creating api calls
 */
interface ItemApiService {
    @GET("avaloirs/items/api/categories")
    suspend fun fetchCategories(): List<Category>

    @Multipart
    @POST("avaloirs/items/api/insert")
    fun insertItemNotSuspend(
        @Part("coordinates") coordinates: Coordinates,
        @Part file: MultipartBody.Part,
        @Part("image") requestBody: RequestBody
    ): Call<DataResponse>
}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object ItemApi {
    val retrofitService: ItemApiService by lazy {
        retrofit.create(ItemApiService::class.java)
    }
}