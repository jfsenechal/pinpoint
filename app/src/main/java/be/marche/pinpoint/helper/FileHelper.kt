package be.marche.pinpoint.helper

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import java.io.File
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.Date
import android.os.Environment
import androidx.core.content.FileProvider.getUriForFile
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody

/**
 *
 * <files-path/> --> Context.getFilesDir()
 * <cache-path/> --> Context.getCacheDir()
 * <external-path/> --> Environment.getExternalStorageDirectory()
 * <external-files-path/> --> Context.getExternalFilesDir(String)
 * <external-cache-path/> --> Context.getExternalCacheDir()
 * <external-media-path/> --> Context.getExternalMediaDirs()
 */

class FileHelper {

    fun createRequestBody(file: File): RequestBody {
        val MEDIA_TYPE_IMAGE: MediaType = "image/*".toMediaTypeOrNull()!!
        return file.asRequestBody(MEDIA_TYPE_IMAGE)
    }

    fun createPart(file: File, requestBody: RequestBody): MultipartBody.Part {
        return MultipartBody.Part.createFormData("image", file.name, requestBody)
    }

    fun createImageFile(context: Context): File {
        val fileName = "avaloir_" + System.currentTimeMillis() + ".jpg"
        val dirPath: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File(dirPath, fileName)
    }

    fun createUri(context: Context, file: File): Uri {
        return getUriForFile(
            context,
            context.packageName.toString() + ".fileprovider",
            file
        )
    }

}

@SuppressLint("SimpleDateFormat")
fun Context.createImageFile(): File {
    // Create an image file name
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
    val imageFileName = "JPEG_" + timeStamp + "_"
    val image = File.createTempFile(
        imageFileName,         /* prefix */
        ".jpg",          /* suffix */
        externalCacheDir      /* directory */
    )
    return image
}

fun copyImageToInternalStorage(context: Context, uri: Uri): String {
    val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
    val fileName = "image_${System.currentTimeMillis()}.jpg"
    val file = File(context.filesDir, fileName)

    inputStream?.use { input ->
        file.outputStream().use { output ->
            input.copyTo(output)
        }
    }
    return file.absolutePath // Store this in Room instead of content:// URI
}
