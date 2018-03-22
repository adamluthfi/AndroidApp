package com.example.gredu.androidapp.pdfviewer

import android.content.Context
import android.graphics.pdf.PdfRenderer
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import com.example.gredu.androidapp.R
import es.voghdev.pdfviewpager.library.PDFViewPager
import es.voghdev.pdfviewpager.library.RemotePDFViewPager
import es.voghdev.pdfviewpager.library.adapter.PDFPagerAdapter
import es.voghdev.pdfviewpager.library.remote.DownloadFile
import es.voghdev.pdfviewpager.library.util.FileUtil
import kotlinx.android.synthetic.main.activity_pdf.*
import java.lang.Exception

class PdfActivity : AppCompatActivity(),DownloadFile.Listener {

    private lateinit var remotePdfViewer: RemotePDFViewPager
    private lateinit var adapter: PDFPagerAdapter
    val uri = "http://ilmukomputer.org/wp-content/uploads/2008/03/jun-stepbystep1.pdf"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf)
        setButtonListener()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (adapter != null){
            adapter.close()
        }
    }

    private fun setButtonListener(){
        btn_download.setOnClickListener {
            remotePdfViewer = RemotePDFViewPager(this,uri,this)
            remotePdfViewer.id = R.id.pdfViewPager
        }
    }

    override fun onSuccess(url: String?, destinationPath: String?) {
        adapter = PDFPagerAdapter(this,FileUtil.extractFileNameFromURL(url))
        remotePdfViewer.adapter = adapter
    }

    override fun onFailure(e: Exception?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onProgressUpdate(progress: Int, total: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
