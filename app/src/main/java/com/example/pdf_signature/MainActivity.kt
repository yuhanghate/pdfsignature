package com.example.pdf_signature

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.InputType
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.hutool.json.JSONUtil
import cn.zhxu.data.Mapper
import cn.zhxu.okhttps.HTTP
import cn.zhxu.okhttps.HttpResult
import cn.zhxu.okhttps.gson.GsonMsgConvertor
import com.example.pdf_signature.adapter.MyAdapter
import com.example.pdf_signature.model.result.ListResult
import com.example.pdf_signature.utils.CustomDividerItemDecoration
import com.example.pdf_signature.utils.SharedPreferencesUtils
import com.example.pdf_signature.utils.SharedPreferencesUtils.getListApi
import com.example.pdf_signature.utils.SharedPreferencesUtils.getSignApi
import com.example.pdf_signature.utils.UuidUtils
import com.scwang.smart.refresh.header.MaterialHeader
import com.scwang.smart.refresh.layout.api.RefreshLayout


class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var refreshLayout: RefreshLayout
    private lateinit var adapter: MyAdapter
    private lateinit var errorTv: View
    private lateinit var titleTv: TextView
    private lateinit var settingTv: View
    private lateinit var PDFTv: View

    private var address: String? = null
    private var listApi: String? = null
    private var signApi: String? = null

    private var handler: Handler? = null
    private var runnable: Runnable? = null


    private val TAG = MainActivity::class.java.simpleName

    var clickCount = 0
    var lastClickTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recyclerView)
        refreshLayout = findViewById(R.id.refreshLayout)
        titleTv = findViewById(R.id.titleTv)
        errorTv = findViewById(R.id.errorTv)
        settingTv = findViewById(R.id.settingTv)
        PDFTv = findViewById(R.id.PDFTv)


        adapter = MyAdapter(listOf(), this)
        findViewById<ImageView>(R.id.btnBack).visibility = View.GONE
        titleTv.setText("ROMYS电子签名")


        refreshLayout.setRefreshHeader(MaterialHeader(this));

        // 设置布局管理器
        recyclerView.setLayoutManager(LinearLayoutManager(this))

        val dividerItemDecoration =
            CustomDividerItemDecoration(recyclerView.context, LinearLayoutManager.VERTICAL)
        recyclerView.addItemDecoration(dividerItemDecoration)

        refreshLayout.setOnRefreshListener { refreshlayout ->
            refreshlayout.finishRefresh(2000 /*,false*/) //传入false表示刷新失败
            refresh()
        }
        refreshLayout.setEnableLoadMore(false)

//        PDFTv.visibility = View.VISIBLE
        settingTv.visibility = View.VISIBLE
        PDFTv.setOnClickListener { startActivity(Intent(this, PDFActivity::class.java)) }
        settingTv.setOnClickListener {
            showPaaword()

        }
        handler = Handler(Looper.getMainLooper());


    }

    /**
     * 输入密码
     */
    fun showPaaword() {
        val alertDialogBuilder = AlertDialog.Builder(this)

        // 设置弹窗标题
        alertDialogBuilder.setTitle("请输入密码")

        // 创建一个EditText对象用于输入密码
        val input = EditText(this)
        input.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        alertDialogBuilder.setView(input)

        // 设置弹窗按钮及点击事件
        alertDialogBuilder.setPositiveButton(
            "确定"
        ) { dialog, which ->
            val password = input.text.toString()
            if ("8271" == password) {
                startActivity(Intent(this, SettingsActivity::class.java))
            }
            // 在这里可以处理用户输入的密码
        }

        alertDialogBuilder.setNegativeButton(
            "取消"
        ) { dialog, which -> dialog.cancel() }

        // 创建并显示弹窗
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    override fun onStart() {
        super.onStart()
        errorTv.visibility = View.GONE



        address = SharedPreferencesUtils.getAddress(this)
        if (address == null || address!!.isBlank()) {
            Toast.makeText(this, "请填写当前服务器地址", Toast.LENGTH_LONG).show()

            return
        }

        listApi = getListApi(this)
        if (listApi == null || listApi!!.isBlank()) {
            Toast.makeText(this, "获取列表接口地址不能为空", Toast.LENGTH_LONG).show()
            return
        }

        signApi = getSignApi(this)
        if (signApi == null || signApi!!.isBlank()) {
            Toast.makeText(this, "保存签名接口地址不能为空", Toast.LENGTH_LONG).show()
            return
        }

        refresh()

        titleTv.setOnClickListener {
            val currentTime = System.currentTimeMillis()
            if (currentTime - lastClickTime < 800) {
                clickCount++
                if (clickCount >= 5) {
                    startActivity(Intent(this, SettingsActivity::class.java))
                    clickCount = 0
                }
            } else {
                clickCount = 1
            }
            lastClickTime = currentTime
        }

    }

    override fun onResume() {
        super.onResume()
        handler?.postDelayed({ refresh() }, 10000)
    }

    override fun onPause() {
        super.onPause()
        // 在Activity销毁时，移除定时任务，避免内存泄漏
        runnable?.let {
            handler?.removeCallbacks(it);
        }
    }


    /**
     * 刷新
     */
    fun refresh() {
        val http = HTTP.builder()
            .baseUrl(address)
            .addMsgConvertor(GsonMsgConvertor())
            .build()

        val map = hashMapOf<String, String>()
        map["deviceCode"] = UuidUtils.getOrCreateUuid(this)

        http.async(listApi)
            .setOnResponse { res: HttpResult ->


                // 自动反序列化 Bean
                var mapper: Mapper?

                try {
                    mapper = res.body.toMapper()
                } catch (e: Exception) {
                    runOnUiThread {
                        Toast.makeText(
                            this,
                            "网络不好，请重试！",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    return@setOnResponse
                }


                if (mapper.getInt("errno") == 200) {
                    val data = mapper.getArray("data")

                    val list = data.toList(ListResult::class.java)
                    runOnUiThread {
                        errorTv.visibility = View.GONE
                        adapter.updateData(list)
                        recyclerView.setAdapter(adapter)
                    }
                } else {
                    Toast.makeText(
                        this,
                        "请求接口错误：${mapper.getString("errmsg")}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
            .setOnException {
                Toast.makeText(this, "服务器接口请求异常：${it.message}", Toast.LENGTH_LONG).show()
            }
            .setBodyPara(JSONUtil.toJsonStr(map))
            .bodyType("application/json")
            .post() // GET请求
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
                return true
            }

            R.id.pdfView -> {
                startActivity(Intent(this, PDFActivity::class.java))
                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }

}