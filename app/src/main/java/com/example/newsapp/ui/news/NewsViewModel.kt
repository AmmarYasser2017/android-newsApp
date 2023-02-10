package com.example.newsapp.ui.news

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapp.Constants
import com.example.newsapp.api.ApiManager
import com.example.newsapp.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsViewModel : ViewModel() {

    val sourcesLiveData = MutableLiveData<List<SourcesItem?>?>()
    val newsLiveData = MutableLiveData<List<ArticlesItem?>?>()
    val progressVisible = MutableLiveData<Boolean>()
    val messageLiveData = MutableLiveData<String>()

    fun getNewsSources(category: Category) {
        progressVisible.value = true
        ApiManager.getApis().getSources(Constants.apiKey, category.id)
            .enqueue(object : Callback<SourcesResponse> {
                override fun onResponse(
                    call: Call<SourcesResponse>,
                    response: Response<SourcesResponse>
                ) {
//                    progressBar.isVisible=false
//                    addSourcesToTabLayout(response.body()?.sources)
                    progressVisible.value = false
                    sourcesLiveData.value = response.body()?.sources
                }

                override fun onFailure(call: Call<SourcesResponse>, t: Throwable) {
                    progressVisible.value = false
                    Log.e("error", t.localizedMessage)
                    messageLiveData.value = t.localizedMessage
                }

            })
    }

    fun getNewsBySource(sources: SourcesItem) {
        progressVisible.value = true
//        progressBar.isVisible=true
        ApiManager.getApis().getNews(Constants.apiKey, sources.id ?: "")
            .enqueue(object : Callback<NewsResponse> {
                override fun onResponse(
                    call: Call<NewsResponse>,
                    response: Response<NewsResponse>
                ) {
                    progressVisible.value = false
                    newsLiveData.value = response.body()?.articles

//                    progressBar.isVisible=false
//                    adapter.changeData(response.body()?.articles)

                }

                override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
//                    progressBar.isVisible=false
                    progressVisible.value = false
                    messageLiveData.value = t.localizedMessage
                }
            })
    }


}