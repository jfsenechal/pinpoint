package be.marche.pinpoint.network

import be.marche.pinpoint.data.Coordinates
import be.marche.pinpoint.data.DataResponse
import be.marche.pinpoint.entity.Category
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

private const val BASE_URL =
    "https://apptravaux.marche.be/"

interface ItemApiService {
    @GET("avaloirs/items/api/categories")
    suspend fun fetchCategories(): List<Category>

    @Multipart
    @POST("avaloirs/items/api/insert")
    fun insertItemNotSuspend(
        @Part("coordinates") coordinatesJson: RequestBody,
        @Part("category") categoryId: Int,
        @Part("description") description: String?,
        @Part file: MultipartBody.Part,
        @Part("image") requestBody: RequestBody
    ): Call<DataResponse>
}

/**
 * Retrofit service object for creating api calls
 */
object ItemApi {
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val retrofitService: ItemApiService by lazy {
        retrofit.create(ItemApiService::class.java)
    }
}