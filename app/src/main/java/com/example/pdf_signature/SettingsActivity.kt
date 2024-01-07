package com.example.pdf_signature

import android.content.ClipData
import android.content.ClipboardManager
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pdf_signature.fancybuttons.FancyButton
import com.example.pdf_signature.utils.SharedPreferencesUtils
import com.example.pdf_signature.utils.UuidUtils


class SettingsActivity :AppCompatActivity(){

    private lateinit var addressEt:EditText
    private lateinit var listEt:EditText
    private lateinit var signEt:EditText
    private lateinit var deviceNoEt:TextView
    private lateinit var saveBtn: FancyButton
    private lateinit var backBtn:FancyButton
    private lateinit var deviceLL:View
    private lateinit var titleTv: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        addressEt = findViewById(R.id.addressEt)
        listEt = findViewById(R.id.listEt)
        signEt = findViewById(R.id.signEt)
        deviceNoEt = findViewById(R.id.deviceNoEt)
        saveBtn = findViewById(R.id.saveBtn)
        backBtn = findViewById(R.id.backBtn)
        deviceLL = findViewById(R.id.deviceLL)
        titleTv = findViewById(R.id.titleTv)
        titleTv.setText("设置")
        findViewById<ImageView>(R.id.btnBack).setOnClickListener { finish() }
    }

    override fun onStart() {
        super.onStart()

        val address = SharedPreferencesUtils.getAddress(this)
        val listApi = SharedPreferencesUtils.getListApi(this)
        val signApi = SharedPreferencesUtils.getSignApi(this)

        if (address != null && address.isNotBlank()) {
            addressEt.setText(address)
        }
        if (listApi != null && listApi.isNotBlank()) {
            listEt.setText(listApi)
        }
        if (signApi != null && signApi.isNotBlank()) {
            signEt.setText(signApi)
        }

        deviceNoEt.setText(UuidUtils.getOrCreateUuid(this))
        deviceNoEt.isEnabled = false

        saveBtn.setOnClickListener {

            val add = addressEt.text.toString()
            if (add.endsWith("/")) {
                Toast.makeText(this, "服务器地址末尾不能 / 结束！", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            val list = listEt.text.toString()

            if (!list.startsWith("/")) {
                Toast.makeText(this, "获取列表接口首个字母必须 / 开始！", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            val sign = signEt.text.toString()
            if (!sign.startsWith("/")) {
                Toast.makeText(this, "保存签名接口首个字母必须 / 开始！", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            SharedPreferencesUtils.saveAddress(this,addressEt.text.toString())
            SharedPreferencesUtils.saveListApi(this,listEt.text.toString())
            SharedPreferencesUtils.saveSignApi(this,signEt.text.toString())

            Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show()
            finish()
        }

        backBtn.setOnClickListener {
            finish()
        }

        // 设置长按监听器
        // 设置长按监听器
        deviceLL.setOnLongClickListener { // 获取 EditText 的文本内容
            val text: String = deviceNoEt.text.toString()

            // 创建一个剪贴板管理器
            val clipboardManager: ClipboardManager =
                getSystemService(CLIPBOARD_SERVICE) as ClipboardManager

            // 创建一个 ClipData 对象，将文本内容放入剪贴板
            val clipData = ClipData.newPlainText("text", text)
            clipboardManager.setPrimaryClip(clipData)

            val vibrator: Vibrator = getSystemService(Vibrator::class.java)
            if (vibrator.hasVibrator()) {
                val vibrationEffect =
                    VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE)
                vibrator.vibrate(vibrationEffect)
            }

            Toast.makeText(applicationContext, "已复制文本：$text", Toast.LENGTH_SHORT).show()
            true
        }

    }
}