package com.example.pdf_signature

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.AlertDialog
import cn.hutool.json.JSONUtil
import cn.zhxu.okhttps.HTTP
import cn.zhxu.okhttps.HttpResult
import cn.zhxu.okhttps.gson.GsonMsgConvertor
import com.example.pdf_signature.fancybuttons.FancyButton
import com.example.pdf_signature.utils.SharedPreferencesUtils
import com.example.pdf_signature.utils.SharedPreferencesUtils.getAddress
import com.example.pdf_signature.utils.UuidUtils


class PDFActivity : AppCompatActivity() {

    private lateinit var pdfView: WebView
    private lateinit var fahuorenBtn: FancyButton
    private lateinit var shusongrenBtn: FancyButton
    private lateinit var commitBtn: FancyButton
    private lateinit var titleTv: TextView

    private var sign1: String? = null
    private var sign2: String? = null
    private var ticketNo: String? = null

    private val REQUEST_READ_EXTERNAL_STORAGE = 2000

    private val REQUEST_SIGN1_FLAG = 100
    private val REQUEST_SIGN2_FLAG = 200


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf)

        pdfView = findViewById(R.id.pdfView)
        fahuorenBtn = findViewById(R.id.fahuorenBtn)
        shusongrenBtn = findViewById(R.id.shusongrenBtn)
        commitBtn = findViewById(R.id.commitBtn)
        titleTv = findViewById(R.id.titleTv)
        titleTv.setText("PDF预览")
        findViewById<ImageView>(R.id.btnBack).setOnClickListener { finish() }


        ticketNo = intent.getStringExtra("ticketNo")
        val url = intent.getStringExtra("url")

        if (url == null || url.isBlank()) {
            pdfView.loadUrl("file:///android_asset/pdf-website/index.html?pdf=../so-BS00110769.pdf")
            showDialog("加载的pdf地址：../so-BS00110769.pdf")
        } else {
            var address = getAddress(this)
            if (address.endsWith("/")) {
                address = address.subSequence(0, address.length).toString()
            }
            pdfView.loadUrl("file:///android_asset/pdf-website/index.html?pdf=${address}${url}")
            showDialog("加载的pdf地址：${address}${url}")
        }


//        pdfView.loadUrl("file:///android_asset/index.html?file=" + assetUri);
        pdfView.loadUrl("file:///android_asset/pdf-website/index.html?pdf=../so-BS00110769.pdf");
        initView()
        onClick()
    }

    fun showDialog(message: String) {
        val alertDialogBuilder: AlertDialog.Builder =
            AlertDialog.Builder(this)

// 设置弹窗标题

// 设置弹窗标题
        alertDialogBuilder.setTitle("提示")

// 设置弹窗消息内容

// 设置弹窗消息内容
        alertDialogBuilder.setMessage(message)

// 设置弹窗按钮及点击事件

// 设置弹窗按钮及点击事件
        alertDialogBuilder.setPositiveButton("确定",
            DialogInterface.OnClickListener { dialog, which ->
                // 当用户点击确定按钮时执行的操作
                dialog.dismiss()
            })

// 创建并显示弹窗

// 创建并显示弹窗
        val alertDialog: AlertDialog = alertDialogBuilder.create()
        alertDialog.show()
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
        fahuorenBtn.setOnClickListener {
            val intent = Intent(this, SignatureActivity::class.java)
            startActivityForResult(intent, REQUEST_SIGN1_FLAG)
        }
        shusongrenBtn.setOnClickListener {
            val intent = Intent(this, SignatureActivity::class.java)
            startActivityForResult(intent, REQUEST_SIGN2_FLAG)
        }
    }


    fun commit() {
        val address = getAddress(this)
        val signApi = SharedPreferencesUtils.getSignApi(this)

        if (ticketNo == null || ticketNo!!.isBlank()) {
            Toast.makeText(this, "当前出库单编号不能为空！", Toast.LENGTH_LONG).show()
            return
        }

        if (sign1 == null || sign1!!.isBlank()) {
            Toast.makeText(this, "当前发货人签名不能为空！", Toast.LENGTH_LONG).show()
            return
        }

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
        map["sign1"] = sign1!!
        map["sign2"] = sign2!!

        http.async(signApi)
            .setOnResponse { res: HttpResult ->

                val mapper = res.body.toMapper()
                runOnUiThread {
                    if (mapper.getInt("errno") == 200) {
                        Toast.makeText(this, "提交成功！", Toast.LENGTH_LONG).show()
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
            .bodyType("application/json")
            .post()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == REQUEST_SIGN1_FLAG) {
            sign1 = data?.getStringExtra("result")
            fahuorenBtn.isEnabled = false

        } else if (resultCode == RESULT_OK && requestCode == REQUEST_SIGN2_FLAG) {
            sign2 = data?.getStringExtra("result")
            shusongrenBtn.isEnabled = false
        }
    }
}
