package be.marche.pinpoint.network

import be.marche.pinpoint.data.MarsPhoto
//import kotlinx.serialization.json.Json
import retrofit2.Retrofit
import retrofit2.http.GET
//import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
//import okhttp3.MediaType.Companion.toMediaType
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL =
    "https://cst.marche.be/"

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
    @GET("photos")
    suspend fun getPhotos404(): List<MarsPhoto>
    @GET("api.php")
    suspend fun getPhotos(): List<MarsPhoto>
}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object ItemApi {
    val retrofitService: ItemApiService by lazy {
        retrofit.create(ItemApiService::class.java)
    }
}