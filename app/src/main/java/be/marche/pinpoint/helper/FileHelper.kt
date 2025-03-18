package be.marche.pinpoint.helper

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import java.io.File
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.Date

/**
 *
 * <files-path/> --> Context.getFilesDir()
 * <cache-path/> --> Context.getCacheDir()
 * <external-path/> --> Environment.getExternalStorageDirectory()
 * <external-files-path/> --> Context.getExternalFilesDir(String)
 * <external-cache-path/> --> Context.getExternalCacheDir()
 * <external-media-path/> --> Context.getExternalMediaDirs()
 */

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
