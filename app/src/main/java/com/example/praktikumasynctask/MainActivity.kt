package com.example.praktikumasynctask

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private  lateinit var  progressBar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        progressBar = findViewById(R.id.progressBar)
        val btnlistQuote: Button =findViewById(R.id.btn_list_quote)
        btnlistQuote.setOnClickListener {
            val intent = Intent(this,ListQuoteActivity::class.java)
            startActivity(intent)
        }
        getRandomQuote()
    }

    private  fun  getRandomQuote(){
        progressBar.visibility = View.VISIBLE
        val client = AsyncHttpClient()
        val url = "https://api.quotable.io/random"
        client.get(url,object : AsyncHttpResponseHandler (){
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                progressBar.visibility = View.GONE
                val result = String(responseBody!!)
                try {
                    val responseObject = JSONObject(result)
                    val quoteContent = responseObject.getString("content")
                    val quoteAuthor = responseObject.getString("author")
                    val tvContent =  findViewById<TextView>(R.id.quete_content)
                    val tvAuthor = findViewById<TextView>(R.id.quote_author)
                    tvAuthor.text = quoteAuthor
                    tvContent.text = quoteContent
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
                Toast.makeText(this@MainActivity,errorMessage,Toast.LENGTH_LONG).show()
            }

        })
    }
}