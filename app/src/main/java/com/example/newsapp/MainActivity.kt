package com.example.newsapp

import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.api.ApiManager
import com.example.newsapp.model.NewsResponse
import com.example.newsapp.model.SourcesItem
import com.example.newsapp.model.SourcesResponse
import com.example.newsapp.ui.news.NewsAdapter
import com.google.android.material.tabs.TabLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var tabLayout: TabLayout
    lateinit var progressBar: ProgressBar
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        getNewsSources()
    }

    val adapter = NewsAdapter(null)
    fun initViews() {
        tabLayout = findViewById(R.id.tab_layout)
        progressBar = findViewById(R.id.progress_bar)
        recyclerView = findViewById(R.id.recyclerview)
        recyclerView.adapter = adapter
    }

    fun getNewsSources() {
        ApiManager.getApis().getSources(Constants.apiKey, "")
            .enqueue(object : Callback<SourcesResponse> {
                override fun onResponse(
                    call: Call<SourcesResponse>,
                    response: Response<SourcesResponse>
                ) {
                    progressBar.isVisible = false
                    addSourcesToTabLayout(response.body()?.sources)
                }

                override fun onFailure(call: Call<SourcesResponse>, t: Throwable) {
                    Log.e("error", t.localizedMessage)
                }

            })
    }

    fun addSourcesToTabLayout(sources: List<SourcesItem?>?) {
        sources?.forEach {
            val tab = tabLayout.newTab()
            tab.text = it?.name
            tab.tag = it
            tabLayout.addTab(tab)
        }
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val sources = tab?.tag as SourcesItem
                getNewsBySource(sources)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                val sources = tab?.tag as SourcesItem
                getNewsBySource(sources)
            }

        })
        tabLayout.getTabAt(0)?.select()
    }

    private fun getNewsBySource(sources: SourcesItem) {
        progressBar.isVisible = true
        ApiManager.getApis().getNews(Constants.apiKey, sources.id ?: "")
            .enqueue(object : Callback<NewsResponse> {
                override fun onResponse(
                    call: Call<NewsResponse>,
                    response: Response<NewsResponse>
                ) {
                    progressBar.isVisible = false
                    adapter.changeData(response.body()?.articles)

                }

                override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                    progressBar.isVisible = false

                }
            })
    }
}