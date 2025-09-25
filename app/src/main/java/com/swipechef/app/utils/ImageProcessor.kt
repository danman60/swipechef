package com.swipechef.app.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import androidx.exifinterface.media.ExifInterface
import java.io.ByteArrayOutputStream
import java.io.InputStream

class ImageProcessor(private val context: Context) {

    suspend fun processSharedImage(imageUri: Uri, userId: String): Result<ProcessedImage> {
        return try {
            val inputStream = context.contentResolver.openInputStream(imageUri)
            val originalBitmap = BitmapFactory.decodeStream(inputStream)
            inputStream?.close()

            // Rotate image based on EXIF data
            val rotatedBitmap = rotateImageBasedOnExif(imageUri, originalBitmap)

            // Compress image for storage and API calls
            val compressedBytes = compressBitmap(rotatedBitmap, quality = 85)
            val thumbnailBytes = compressBitmap(
                scaleBitmap(rotatedBitmap, maxSize = 400),
                quality = 70
            )

            // Create base64 for API call (smaller version)
            val apiBytes = compressBitmap(
                scaleBitmap(rotatedBitmap, maxSize = 800),
                quality = 60
            )

            Result.success(
                ProcessedImage(
                    originalBytes = compressedBytes,
                    thumbnailBytes = thumbnailBytes,
                    apiBytes = apiBytes,
                    width = rotatedBitmap.width,
                    height = rotatedBitmap.height
                )
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun rotateImageBasedOnExif(imageUri: Uri, bitmap: Bitmap): Bitmap {
        return try {
            val inputStream = context.contentResolver.openInputStream(imageUri)
            val exif = ExifInterface(inputStream!!)
            val orientation = exif.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL
            )
            inputStream.close()

            val matrix = Matrix()
            when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> matrix.postRotate(90f)
                ExifInterface.ORIENTATION_ROTATE_180 -> matrix.postRotate(180f)
                ExifInterface.ORIENTATION_ROTATE_270 -> matrix.postRotate(270f)
                else -> return bitmap
            }

            Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
        } catch (e: Exception) {
            bitmap
        }
    }

    private fun scaleBitmap(bitmap: Bitmap, maxSize: Int): Bitmap {
        val width = bitmap.width
        val height = bitmap.height

        if (width <= maxSize && height <= maxSize) {
            return bitmap
        }

        val ratio = if (width > height) {
            maxSize.toFloat() / width
        } else {
            maxSize.toFloat() / height
        }

        val newWidth = (width * ratio).toInt()
        val newHeight = (height * ratio).toInt()

        return Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true)
    }

    private fun compressBitmap(bitmap: Bitmap, quality: Int): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, stream)
        return stream.toByteArray()
    }

    data class ProcessedImage(
        val originalBytes: ByteArray,
        val thumbnailBytes: ByteArray,
        val apiBytes: ByteArray,
        val width: Int,
        val height: Int
    ) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as ProcessedImage

            if (!originalBytes.contentEquals(other.originalBytes)) return false
            if (!thumbnailBytes.contentEquals(other.thumbnailBytes)) return false
            if (!apiBytes.contentEquals(other.apiBytes)) return false
            if (width != other.width) return false
            if (height != other.height) return false

            return true
        }

        override fun hashCode(): Int {
            var result = originalBytes.contentHashCode()
            result = 31 * result + thumbnailBytes.contentHashCode()
            result = 31 * result + apiBytes.contentHashCode()
            result = 31 * result + width
            result = 31 * result + height
            return result
        }
    }
}