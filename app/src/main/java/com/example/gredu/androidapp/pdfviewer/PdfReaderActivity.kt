package com.example.gredu.androidapp.pdfviewer

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.gredu.androidapp.R
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener
import kotlinx.android.synthetic.main.activity_pdfview.*
import java.io.File
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle
import android.os.Handler
import android.util.Log
import com.github.barteksc.pdfviewer.listener.OnPageScrollListener
import com.shockwave.pdfium.PdfDocument

class PdfReaderActivity : AppCompatActivity(), OnPageChangeListener, OnLoadCompleteListener,
        OnPageErrorListener,OnPageScrollListener {

    private val TAG = PdfReaderActivity::class.java.getSimpleName()

    private var pdfFileName: String? = null
    var pageNumber: Int? = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdfview)
        download("https://s3-ap-southeast-1.amazonaws.com/staging.gredu.co/Kelas_12_SMK_Teknik_Pemesinan_Gerinda_5.pdf")
    }

    private fun download(url: String) {
        pdfFileName = url.substring(url.lastIndexOf('/') + 1)

        DownloadingImplementation(this, Handler(), object : Downloading.Listener {
            override fun onSuccess(url: String, pdfFile: File) {
                displayPdpFromFile(pdfFile)
            }

            override fun onFailure(e: Exception) {

            }

            override fun onProgressUpdate(progress: Int, total: Int) {

            }
        }).download(url)

    }

    private fun displayPdpFromFile(file: File) {
        pdfView.fromFile(file)
                .defaultPage(0)
                .onPageChange(this)
                .enableAnnotationRendering(true)
                .onLoad(this)
                .scrollHandle(DefaultScrollHandle(this))
                .spacing(5) // in dp
                .onPageScroll(this)
                .onPageError(this)
                .load()
    }

    override fun onPageChanged(page: Int, pageCount: Int) {
        pageNumber = page;
        setTitle(String.format("%s %s / %s", pdfFileName, page + 1, pageCount));
    }

    override fun onPageScrolled(page: Int, positionOffset: Float) {
        pdfView.getPageAtPositionOffset(positionOffset)
    }

    override fun loadComplete(nbPages: Int) {
        val meta = pdfView.documentMeta
        Log.e(TAG, "title = " + meta.title)
        Log.e(TAG, "author = " + meta.author)
        Log.e(TAG, "subject = " + meta.subject)
        Log.e(TAG, "keywords = " + meta.keywords)
        Log.e(TAG, "creator = " + meta.creator)
        Log.e(TAG, "producer = " + meta.producer)
        Log.e(TAG, "creationDate = " + meta.creationDate)
        Log.e(TAG, "modDate = " + meta.modDate)

        printBookmarksTree(pdfView.tableOfContents, "-")
    }


    override fun onPageError(page: Int, t: Throwable?) {
        Log.e(TAG, "Cannot load page " + page);
    }

    fun printBookmarksTree(tree: List<PdfDocument.Bookmark>, sep: String) {
        for (b in tree) {

            Log.e(TAG, String.format("%s %s, p %d", sep, b.getTitle(), b.getPageIdx()))

            if (b.hasChildren()) {
                printBookmarksTree(b.getChildren(), "$sep-")
            }
        }
    }

}