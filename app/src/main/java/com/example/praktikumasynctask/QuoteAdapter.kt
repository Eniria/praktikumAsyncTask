package com.example.praktikumasynctask
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray
import org.w3c.dom.Text

class QuoteAdapter(private val listQuote: JSONArray)
    : RecyclerView.Adapter<QuoteAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_quote,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val quote = listQuote.getJSONObject(position)
        val content = quote.getString("content")
        val author = quote.getString("author")
        holder.bind(content,author)
    }

    override fun getItemCount(): Int {
        return listQuote.length()
    }
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val tvContent = view.findViewById<TextView>(R.id.tv_content)
        val tvAuthor : TextView = view.findViewById(R.id.tv_author)
        fun  bind(content : String,author :String){
            tvContent.text = content
            tvAuthor.text = author

        }
    }

}