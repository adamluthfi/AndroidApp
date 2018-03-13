package com.example.gredu.androidapp.pdfviewer

import android.content.pm.PackageManager
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import com.example.gredu.androidapp.R
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Color
import android.widget.Toast
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle
import kotlinx.android.synthetic.main.activity_pdfview.*
import android.provider.OpenableColumns
import android.util.Log
import com.shockwave.pdfium.PdfDocument
import com.shockwave.pdfium.PdfDocument.Bookmark

class PdfViewActivity : AppCompatActivity(),OnPageChangeListener,OnLoadCompleteListener
        ,OnPageErrorListener{

    val REQUEST_CODE = 42
    val PERMISSION_CODE = 42042

    val SAMPLE_FILE = "dat=content://com.android.providers.downloads.documents/document/257 flg=0x1"
    val READ_EXTERNAL_STORAGE = "android.permission.READ_EXTERNAL_STORAGE"

    private lateinit var uri:Uri
    private var pageNumber: Int = 0
    private var pdfFileName : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdfview)

       // pickFile()
        uri = Uri.parse("dat=content://com.android.providers.downloads.documents/document/257 flg=0x1")
        displayFromUri(uri)
    }

    override fun onPageChanged(page: Int, pageCount: Int) {
        pageNumber = page
        setTitle(String.format("%s %s / %s", pdfFileName, page + 1, pageCount))
    }

    override fun loadComplete(nbPages: Int) {
        val meta = pdfView.documentMeta

        printBookmarksTree(pdfView.tableOfContents, "-")
    }

    override fun onPageError(page: Int, t: Throwable?) {
        Log.e("TAG" ,"Cannot load page " + page)
    }

    fun printBookmarksTree(tree: List<PdfDocument.Bookmark>, sep: String) {
        for (b in tree) {

            if (b.hasChildren()) {
                printBookmarksTree(b.getChildren(), sep + "-")
            }
        }
    }

    fun pickFile(){
        val permissionCheck = ContextCompat.checkSelfPermission(this,READ_EXTERNAL_STORAGE)

        if (permissionCheck != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(
                    this,
                    arrayOf(READ_EXTERNAL_STORAGE),PERMISSION_CODE
            )
        }
        launchPicker()
    }

    fun launchPicker(){
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.setType("application/pdf")
        try {
            startActivityForResult(intent,REQUEST_CODE)
        }catch (e: ActivityNotFoundException){
            Toast.makeText(this,"Not found",Toast.LENGTH_SHORT).show()
        }
    }

    fun afterViews(){
        pdfView.setBackgroundColor(Color.LTGRAY)
        if (uri  != null){
            displayFromUri(uri)
        }else{
            displayFromAsset(SAMPLE_FILE)
        }
        setTitle(pdfFileName)
    }

    fun displayFromAsset(assetFileName:String){
        pdfFileName = assetFileName

        pdfView.fromAsset(SAMPLE_FILE)
                .defaultPage(pageNumber)
                .onPageChange(this)
                .enableAnnotationRendering(true)
                .onLoad(this)
                .scrollHandle(DefaultScrollHandle(this))
                .spacing(10)
                .onPageError(this)
                .load()
    }

    fun displayFromUri(uri:Uri){
        pdfFileName = getFileName(uri)

        pdfView.fromUri(uri)
                .defaultPage(pageNumber)
                .onPageChange(this)
                .enableAnnotationRendering(true)
                .onLoad(this)
                .scrollHandle( DefaultScrollHandle(this))
                .spacing(10) // in dp
                .onPageError(this)
                .load();

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK){
            uri = intent.data
            displayFromUri(uri)
        }
    }

    fun getFileName(uri: Uri): String {
        var result: String? = null
        if (uri.scheme == "content") {
            val cursor = contentResolver.query(uri, null, null, null, null)
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                }
            } finally {
                cursor?.close()
            }
        }
        if (result == null) {
            result = uri.lastPathSegment
        }
        return result!!
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == PERMISSION_CODE){
            if (grantResults.size>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                launchPicker()
            }
        }
    }
}
