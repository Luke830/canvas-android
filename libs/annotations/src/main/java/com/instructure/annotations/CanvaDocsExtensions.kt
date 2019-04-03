/*
 * Copyright (C) 2018 - present Instructure, Inc.
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */
package com.instructure.annotations

import android.graphics.RectF
import com.instructure.annotations.FileCaching.FetchFileAsyncTask
import com.instructure.annotations.FileCaching.FileCache
import com.instructure.canvasapi2.utils.weave.resumeSafely
import com.instructure.canvasapi2.utils.weave.resumeSafelyWithException
import com.pspdfkit.annotations.Annotation
import com.pspdfkit.document.PdfDocument
import kotlinx.coroutines.suspendCancellableCoroutine
import java.io.File


/**
 * Attempts to download a file from a URL and return the resulting [File] object. Internally this
 * uses a size-limited disk cache for quick retrieval of the most frequently-accessed files.
 *
 * @param url The URL of the file to be downloaded
 * @param onProgressChanged A callback for download progress updates. Progress is between 0f (0%)
 * and 1f (100%), and will be updated no more than 30 times per second. Note that this is called from
 * a background thread; if you need to manipulate UI based on these updates, you may wrap your code in
 * a [onUI][com.instructure.canvasapi2.utils.weave.WeaveCoroutine.onUI] block.
 * @return The file if it was successfully downloaded or retrieved from cache, or null if there was an error.
 */
@Suppress("EXPERIMENTAL_FEATURE_WARNING")
suspend fun FileCache.awaitFileDownload(url: String, onProgressChanged: ((Float) -> Unit)? = null): File? =
        suspendCancellableCoroutine { continuation ->

            val task = this.getInputStream(url, object : FetchFileAsyncTask.FetchFileCallback {
                override fun onProgress(progress: Float) {
                    if (!continuation.isCancelled) onProgressChanged?.invoke(progress)
                }

                override fun onFileLoaded(fileInputStream: File?) {
                    continuation.resumeSafely(fileInputStream)
                }
            })

            continuation.invokeOnCancellation { task.cancel() }
        }

/**
 * Wraps a [CanvaDocsRedirectAsyncTask] and returns the redirected URL
 *
 * @param url The original URL to be redirected
 * @return The redirected URL
 */
suspend fun getCanvaDocsRedirect(url: String): String =
        suspendCancellableCoroutine { continuation ->
            CanvaDocsRedirectAsyncTask(
                    url,
                    { continuation.resumeSafely(it) },
                    { continuation.resumeSafelyWithException(it) }
            ).execute()
        }

fun calculateRotationOffset(currentPageRotation: Int, desiredPageRotation: Int): Int {
    val rotationOffset = (360 - currentPageRotation) + desiredPageRotation
    return when {
        rotationOffset > 360 -> rotationOffset - 360
        rotationOffset < 360 -> rotationOffset
        else -> 0
    }
}

/**
 * If is CanvaAnnotation type, return id
 * Otherwise return ""
 */
fun Annotation.getId() = (this as? PSCanvaInterface)?.id ?: ""


/**
 * If is CanvaAnnotation type, return userId
 * Otherwise return ""
 */
fun Annotation.getUserId() = (this as? PSCanvaInterface)?.userId ?: ""

/**
 * If is CanvaAnnotation type, return ctxId
 * Otherwise return ""
 */
fun Annotation.getCtxId() = (this as? PSCanvaInterface)?.ctxId ?: ""

/**
 * If is CanvaAnnotation type, return ctxId
 * Otherwise return ""
 */
fun Annotation.getCreatedAt() = (this as? PSCanvaInterface)?.createdAt ?: ""

fun Annotation.isNotFound() = (this as? PSCanvaInterface)?.isNotFound ?: false

fun Annotation.setIsNotFound(isNotFound: Boolean) {
    (this as? PSCanvaInterface)?.isNotFound = isNotFound
}

fun Annotation.getContext() = (this as? PSCanvaInterface)?.context ?: ""

/**
 * Finds an annotation based on it's id
 */
fun PdfDocument.findAnnotationById(id: String, pageIndex: Int): Annotation? {
    val annotations = this.annotationProvider.getAnnotations(pageIndex)
    return annotations.find { it.getId() == id }
}

fun PdfDocument.removeAnnotation(id: String, pageIndex: Int) {
    val annotationToRemove = findAnnotationById(id, pageIndex) ?: return
    annotationProvider.removeAnnotationFromPage(annotationToRemove)
}

fun PdfDocument.addAnnotation(annotation: Annotation) {
    annotationProvider.addAnnotationToPage(annotation)
}


// Delegate
interface PSCanvaInterface {
    val id: String?
    val userId: String?
    val context: String?
    var page: Int
    var rect: RectF?
    val rectList: MutableList<RectF>?
    var isNotFound: Boolean
    val ctxId: String?
    val createdAt: String?
}

data class CanvaPdfAnnotation(
        override val id: String? = null,
        override val userId: String? = null,
        override val context: String? = null,
        override var page: Int = 0,
        override var rect: RectF? = null,
        override var isNotFound: Boolean = false,
        override val ctxId: String? = null,
        override val createdAt: String? = null,
        override val rectList: MutableList<RectF>? = null
) : PSCanvaInterface

