//package com.example.pdf_signature.pdf
//
//import android.os.Bundle
//import androidx.appcompat.app.AppCompatActivity
//import com.example.pdf_signature.R
//import com.github.barteksc.pdfviewer.PDFView
//
//class PdfActivity:AppCompatActivity() {
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_pdfactivity)
//
//    }
//
//    override fun onStart() {
//        super.onStart()
//        val pdfview = findViewById<PDFView>(R.id.pdfView)
//        pdfview.fromAsset("专用服务器.pdf").load()
//    }
//}