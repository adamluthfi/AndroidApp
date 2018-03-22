package com.example.gredu.androidapp.pdfviewer

import java.io.File

interface Downloading{
    fun download(url: String, customName: String)

    fun download(url: String)

    interface Listener {
        fun onSuccess(url: String, pdfFile: File)

        fun onFailure(e: Exception)

        fun onProgressUpdate(progress: Int, total: Int)
    }
}