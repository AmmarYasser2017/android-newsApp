package com.example.newsapp.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.newsapp.R
import com.example.newsapp.databinding.ActivityHomeBinding
import com.example.newsapp.model.Category
import com.example.newsapp.ui.SettingsFragment
import com.example.newsapp.ui.categories.CategoriesFragment
import com.example.newsapp.ui.news.NewsFragment

class HomeActivity : AppCompatActivity() {

    //    lateinit var drawerLayout: DrawerLayout
//    lateinit var drawerButton:ImageView
//    lateinit var categories: View
//    lateinit var settings:View
    lateinit var binding: ActivityHomeBinding

    val categoriesFragment = CategoriesFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        initView()
        pushFragment(categoriesFragment)
        categoriesFragment.onCategoryClickListener =
            object : CategoriesFragment.OnCategoryClickListener {
                override fun onCategoryClick(category: Category) {
                    pushFragment(NewsFragment.getInstance(category), true)
                }
            }
    }

    private fun initView() {

//        drawerLayout=findViewById(R.id.drawer_layout)

//        drawerButton=findViewById(R.id.menu_button)
        binding.appBarHome.menuButton.setOnClickListener {
            binding.drawerLayout.open()
        }
//        categories=findViewById(R.id.categories)

        binding.categories.setOnClickListener {
            pushFragment(categoriesFragment)
        }

//        settings=findViewById(R.id.settings)
        binding.settings.setOnClickListener {
            pushFragment(SettingsFragment())
        }
    }

    private fun pushFragment(fragment: Fragment, addToBackStack: Boolean = false) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)


        if (addToBackStack) {
            fragmentTransaction.addToBackStack("")
        }
        fragmentTransaction.commit()
        binding.drawerLayout.closeDrawers()
    }


}