package com.example.pdf_signature

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import cn.hutool.json.JSONUtil
import cn.zhxu.data.Mapper
import cn.zhxu.okhttps.HTTP
import cn.zhxu.okhttps.HttpResult
import cn.zhxu.okhttps.gson.GsonMsgConvertor
import com.example.pdf_signature.fancybuttons.FancyButton
import com.example.pdf_signature.utils.SharedPreferencesUtils
import com.example.pdf_signature.utils.SharedPreferencesUtils.getAddress
import com.example.pdf_signature.utils.UuidUtils
import com.google.android.material.snackbar.Snackbar


class PDFActivity : AppCompatActivity() {

    private lateinit var pdfView: WebView
//    private lateinit var fahuorenBtn: FancyButton
    private lateinit var shusongrenBtn: FancyButton
    private lateinit var commitBtn: FancyButton
    private lateinit var titleTv: TextView
    private lateinit var progress:ProgressBar

    private var sign1: String? = null
    private var sign2: String? = null
    private var ticketNo: String? = null

    private val REQUEST_READ_EXTERNAL_STORAGE = 2000

    private val REQUEST_SIGN1_FLAG = 100
    private val REQUEST_SIGN2_FLAG = 200

    private var handler: Handler? = null
    private var runnable: Runnable? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf)

        pdfView = findViewById(R.id.pdfView)
//        fahuorenBtn = findViewById(R.id.fahuorenBtn)
        shusongrenBtn = findViewById(R.id.shusongrenBtn)
        commitBtn = findViewById(R.id.commitBtn)
        progress = findViewById(R.id.progress)
        titleTv = findViewById(R.id.titleTv)
        titleTv.setText("PDF预览")
        findViewById<ImageView>(R.id.btnBack).setOnClickListener { finish() }
        initView()
        handler = Handler(Looper.myLooper()!!)

        ticketNo = intent.getStringExtra("ticketNo")
        val url = intent.getStringExtra("url")

        if (url == null || url.isBlank()) {
            pdfView.loadUrl("file:///android_asset/pdf-website/index.html?pdf=../so-BS00110769.pdf")
//            showDialog("加载的pdf地址：../so-BS00110769.pdf")
        } else {
            var address = getAddress(this)
            if (address.endsWith("/")) {
                address = address.subSequence(0, address.length).toString()
            }
//            preView("${address}${url}")

            pdfView.loadUrl("file:///android_asset/pdf-website/index.html?pdf=${address}${url}")
//            showDialog("加载的pdf地址：${address}${url}")
        }


//        pdfView.loadUrl("file:///android_asset/index.html?file=" + assetUri);
//        pdfView.loadUrl("file:///android_asset/pdf-website/index.html?pdf=../so-BS00110769.pdf");

        onClick()
    }

    fun initView() {
        val webSettings: WebSettings = pdfView.getSettings()
        webSettings.javaScriptEnabled = true
        webSettings.pluginState = WebSettings.PluginState.ON
        webSettings.allowContentAccess = true
        webSettings.allowFileAccessFromFileURLs = true
        webSettings.allowFileAccess = true
        webSettings.allowFileAccessFromFileURLs = true
        /**
         * 简单来说，该项设置决定了JavaScript能否访问来自于任何源的文件标识的URL。
         * 比如我们把PDF.js放在本地assets里，然后通过一个URL跳转访问来自于任何URL的PDF，所以这里我们需要将其设置为true。
         * 而一般情况下，为了安全起见，是需要将其设置为false的。
         */
        webSettings.allowUniversalAccessFromFileURLs = true
    }

    fun onClick() {
        commitBtn.setOnClickListener { commit() }
//        fahuorenBtn.setOnClickListener {
//            val intent = Intent(this, SignatureActivity::class.java)
//            startActivityForResult(intent, REQUEST_SIGN1_FLAG)
//        }
        shusongrenBtn.setOnClickListener {
            val intent = Intent(this, SignatureActivity::class.java)
            startActivityForResult(intent, REQUEST_SIGN2_FLAG)
        }
    }


    fun commit() {
        progress.visibility = View.VISIBLE
        val address = getAddress(this)
        val signApi = SharedPreferencesUtils.getSignApi(this)

        if (ticketNo == null || ticketNo!!.isBlank()) {
            Toast.makeText(this, "当前出库单编号不能为空！", Toast.LENGTH_LONG).show()
            return
        }

//        if (sign1 == null || sign1!!.isBlank()) {
//            Toast.makeText(this, "当前发货人签名不能为空！", Toast.LENGTH_LONG).show()
//            return
//        }

        if (sign2 == null || sign2!!.isBlank()) {
            Toast.makeText(this, "当前输送人签名不能为空！", Toast.LENGTH_LONG).show()
            return
        }

        val http = HTTP.builder()
            .baseUrl(address)
            .addMsgConvertor(GsonMsgConvertor())
            .build()

        val map = hashMapOf<String, String>()
        map["ticketNo"] = ticketNo!!
        map["deviceCode"] = UuidUtils.getOrCreateUuid(this)
//        map["sign1"] = sign1!!
        map["sign2"] = sign2!!

        http.async(signApi)
            .setOnResponse { res: HttpResult ->

                var mapper: Mapper?
                try {
                    mapper = res.body.toMapper()
                } catch (e: Exception) {
                    runOnUiThread {
                        progress.visibility = View.GONE
                        Toast.makeText(this, "网络问题，请重试", Toast.LENGTH_LONG).show()
                    }
                    return@setOnResponse
                }


                runOnUiThread {
                    progress.visibility = View.GONE
                    if (mapper.getInt("errno") == 200) {

                        Snackbar.make(commitBtn, "当前签名，提交成功！", Snackbar.LENGTH_LONG)
                            .show()
//                        Snackbar.make(coordinatorLayout,"提交成功！",Snackbar.LENGTH_LONG).show();

                        // 创建一个Runnable对象

                        // 创建一个Runnable对象
                        runnable = object : Runnable {
                            override fun run() {
                                // 在此处执行定时任务的操作
                                finish()
                            }
                        }

                        // 延迟启动定时任务，时间间隔为1000毫秒(1秒)

                        // 延迟启动定时任务，时间间隔为1000毫秒(1秒)
                        handler!!.postDelayed(runnable as Runnable, 4000)

                    } else {
                        Toast.makeText(
                            this,
                            "请求接口错误：${mapper.getString("errmsg")}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }


            }
            .setBodyPara(JSONUtil.toJsonStr(map))
            .setOnException {
                Toast.makeText(this, "服务器接口请求异常：${it.message}", Toast.LENGTH_LONG).show()
            }
            .bodyType("application/json")
            .post()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == REQUEST_SIGN1_FLAG) {
            sign1 = data?.getStringExtra("result")
//            fahuorenBtn.isEnabled = false

        } else if (resultCode == RESULT_OK && requestCode == REQUEST_SIGN2_FLAG) {
            sign2 = data?.getStringExtra("result")
            shusongrenBtn.isEnabled = false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // 在Activity销毁时，移除定时任务，避免内存泄漏
        runnable?.let {
            handler?.removeCallbacks(it);
        }

    }
}
