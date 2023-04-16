package com.example.praktikumasynctask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import java.lang.Exception

class ListQuoteActivity : AppCompatActivity() {
    private  lateinit var progressBar : ProgressBar
    private  lateinit var  recyclerView: RecyclerView
    private lateinit var quoteAdapter: QuoteAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_quote)
        progressBar = findViewById(R.id.pb_list_quote)
        recyclerView = findViewById(R.id.rv_list_quote)
        val layoutManager = LinearLayoutManager(this)
        val itemDecoration = DividerItemDecoration(this,layoutManager.orientation)
        recyclerView.layoutManager = layoutManager
        recyclerView.addItemDecoration(itemDecoration)
        getlistQuote()
    }
    private  fun  getlistQuote(){
        progressBar.visibility = View.VISIBLE
        val client = AsyncHttpClient()
        val url = "https://api.quotable.io/quotes"
        client.get(url,object : AsyncHttpResponseHandler(){
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                progressBar.visibility = View.GONE
                val result = String(responseBody!!)
                try {
                    val responseObject = JSONObject(result)
                    val listQuotes = responseObject.getJSONArray("result")
                    quoteAdapter = QuoteAdapter(listQuotes)
                    recyclerView.adapter = quoteAdapter
                }catch (e : Exception){

                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                //menampilkan pesan Error
                progressBar.visibility = View.GONE
                val errorMessage = when(statusCode){
                    401 ->"$statusCode : Bad Request"
                    402 ->"$statusCode : Forbidden"
                    403 ->"$statusCode : Not Found"
                    else ->"$statusCode : ${error?.message}"
                }
                Toast.makeText(this@ListQuoteActivity,errorMessage,Toast.LENGTH_LONG).show()
            }

        })
    }
}