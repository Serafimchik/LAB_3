package com.example.news

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.news.databinding.ActivityMainBinding
import org.json.JSONObject

const val API_KEY = "pub_351785071574ac4c0f3c157920566076c754a"
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: NewsAdapter

    private fun initRcView(List: List<NewsModel>) = with(binding){
        recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
        adapter = NewsAdapter()
        recyclerView.adapter = adapter
        val list = List
        adapter.submitList(list)
    }

    private fun requestNewsData(keyword: String){
        val url = "https://newsdata.io/api/1/news"+
                "?apikey=$API_KEY"+
                "&q=$keyword"+"&language=en"
        val queue = Volley.newRequestQueue(this)
        val stringRequest = StringRequest(com.android.volley.Request.Method.GET,
            url,
            {
                    result -> parseNewsData(result)
            },
            {
                    error -> Log.d("MyLog","Error: $error")
            }
        )
        queue.add(stringRequest)
    }

    private fun parseNewsData(result: String){
        val mainObject = JSONObject(result)
        val list = parseNews(mainObject)
        initRcView(list)
    }

    private fun parseNews(mainObject: JSONObject): List<NewsModel>{
        val list = ArrayList<NewsModel>()
        val newsArray = mainObject.getJSONArray("results")
        for (i in 0 until newsArray.length()){
            val news = newsArray[i] as JSONObject
            val item = NewsModel(
                news.getString("title"),
                news.getString("description"),
                news.getString("image_url"),
                news.getString("link"),
                news.getString("pubDate"),
                news.getString("category"),
                news.getString("language")
            )
            list.add(item)
        }
        return list
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bGet.setOnClickListener {
            val keyword = binding.inputKeyword.text.toString()
            requestNewsData(keyword)
            binding.inputKeyword.text.clear()
        }
    }
}