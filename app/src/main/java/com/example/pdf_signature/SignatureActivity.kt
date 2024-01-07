package com.example.pdf_signature

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pdf_signature.fancybuttons.FancyButton
import com.example.pdf_signature.signatureview.SignatureView


class SignatureActivity:AppCompatActivity() {

    lateinit var clearBtn: FancyButton
    lateinit var signatureView: SignatureView
    lateinit var saveBtn: FancyButton
    private lateinit var titleTv: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_signature)
        clearBtn = findViewById(R.id.clearBtn)
        signatureView = findViewById(R.id.signatureView)
        saveBtn = findViewById(R.id.saveBtn)
        titleTv = findViewById(R.id.titleTv)
        titleTv.setText("电子签名")
        findViewById<ImageView>(R.id.btnBack).setOnClickListener { finish() }


        clearBtn.setOnClickListener {
            signatureView.clear()
        }
        saveBtn.setOnClickListener {
            val base64 = signatureView.save(getCacheDirectory(this, "sign.png"), true, 20)
            if (base64 == null) {
                Toast.makeText(this, "保存失败！", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            Toast.makeText(this, "保存成功！", Toast.LENGTH_SHORT).show()
            System.out.println("图片路径：${signatureView.savePath}")
            val intent = Intent()
            intent.putExtra("result", base64)
            setResult(RESULT_OK, intent)
            finish()
        }
    }


    fun getCacheDirectory(context: Context, fileName:String): String {
        return context.cacheDir.absolutePath + "/${fileName}"
    }

    fun getExternalCacheDirectory(context: Context, fileName:String): String? {
        return context.externalCacheDir?.absolutePath+"/${fileName}"
    }

    fun getPublicDirectory(fileName:String): String {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).absolutePath + "/${fileName}"
    }


}