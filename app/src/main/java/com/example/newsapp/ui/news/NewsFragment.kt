package com.example.newsapp.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentNewsBinding
import com.example.newsapp.model.ArticlesItem
import com.example.newsapp.model.Category
import com.example.newsapp.model.SourcesItem
import com.google.android.material.tabs.TabLayout

class NewsFragment : Fragment() {

    companion object {
        fun getInstance(category: Category): NewsFragment {
            val fragment = NewsFragment()
            fragment.category = category
            return fragment
        }
    }

    lateinit var category: Category

    lateinit var viewDataBinding: FragmentNewsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_news, container, false)
        return viewDataBinding.root
//        return inflater.inflate(R.layout.fragment_news,container,false)
    }

    lateinit var viewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        subscribeToLiveData()
        viewModel.getNewsSources(category)
    }

    private fun subscribeToLiveData() {
        viewModel.sourcesLiveData.observe(viewLifecycleOwner, Observer {
            addSourcesToTabLayout(it)
        })

        viewModel.newsLiveData.observe(viewLifecycleOwner, Observer {
            showData(it)
        })

        viewModel.progressVisible.observe(viewLifecycleOwner, Observer { isVisible ->
            viewDataBinding.progressBar.isVisible = isVisible
        })

        viewModel.messageLiveData.observe(viewLifecycleOwner, Observer { message ->
            Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
        })
    }

    private fun showData(it: List<ArticlesItem?>?) {
        adapter.changeData(it)
    }

    val adapter = NewsAdapter(null)
    fun initViews() {
//        tabLayout=requireView().findViewById(R.id.tab_layout)
//        progressBar=requireView().findViewById(R.id.progress_bar)
//        recyclerView=requireView().findViewById(R.id.recyclerview)
        viewDataBinding.recyclerview.adapter = adapter
    }


    fun addSourcesToTabLayout(sources: List<SourcesItem?>?) {
        sources?.forEach {
            val tab = viewDataBinding.tabLayout.newTab()
            tab.text = it?.name
            tab.tag = it
            viewDataBinding.tabLayout.addTab(tab)
        }
        viewDataBinding.tabLayout.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val sources = tab?.tag as SourcesItem
//                getNewsBySource(sources)
                viewModel.getNewsBySource(sources)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                val sources = tab?.tag as SourcesItem
//                getNewsBySource(sources)
                viewModel.getNewsBySource(sources)
            }

        })
        viewDataBinding.tabLayout.getTabAt(0)?.select()
    }


}