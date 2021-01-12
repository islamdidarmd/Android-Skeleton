package com.example.androidskeleton.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidskeleton.R
import com.example.androidskeleton.data.model.STATE
import com.example.androidskeleton.data.model.search.Repo
import com.example.androidskeleton.databinding.ActivityMainBinding
import com.example.androidskeleton.ui.base.BaseActivity

class MainActivity : BaseActivity() {
    private val TAG = "MainActivity"
    private lateinit var _binding: ActivityMainBinding

    private var searchView: SearchView? = null
    private var tvAutoComplete: AutoCompleteTextView? = null

    private val viewModel by viewModels<MainViewModel>()
    private var adapter: ArrayAdapter<String>? = null

    private val repoAdapter = RepoAdapter { repo ->
        val share = Intent(Intent.ACTION_VIEW, Uri.parse(repo.html_url))
        startActivity(share)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        setSupportActionBar(_binding.appbar.toolbar)

        initUI()
    }

    private fun initUI() {
        with(_binding.rvList) {
            layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(this@MainActivity, RecyclerView.VERTICAL))
            adapter = repoAdapter
        }
        //observing db changes for recent histories
        viewModel.getRecent().observe(this,
            Observer { list ->
                if (list == null) return@Observer
                Log.d(TAG, "Recent List size ${list.size}")

                adapter?.clear()
                adapter?.addAll(list)
            })

        viewModel.getRepoListLiveData().observe(this,
            Observer { data ->
                if (data == null) return@Observer

                when (data.state) {
                    STATE.LOADING -> {
                        //show loading ui
                        showProgressDialog(false)
                    }
                    STATE.SUCCESS -> {
                        //show success ui
                        dismissProgressDialog()
                        updateList(data.data?.items)
                    }
                    STATE.ERROR -> {
                        //show error ui
                        dismissProgressDialog()
                        Toast.makeText(this, "Failed to fetch repositories!", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            })
    }

    private fun updateList(items: List<Repo>?) {
        items?.let { repoAdapter.submitList(it) }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        initMenu(menu)
        return true
    }

    private fun initMenu(menu: Menu?) {
        searchView = menu?.findItem(R.id.menu_search)?.actionView as SearchView
        searchView?.queryHint = getString(R.string.search)
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    viewModel.searchRepo(query)
                    viewModel.insertToDb(it)
                }
                return false
            }
        })

        adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            mutableListOf("Android")
        )

        tvAutoComplete = searchView?.findViewById(androidx.appcompat.R.id.search_src_text)
        tvAutoComplete?.setAdapter(adapter)
        tvAutoComplete?.threshold = 1
        tvAutoComplete?.setDropDownBackgroundResource(R.color.white)
        tvAutoComplete?.setOnItemClickListener { parent, view, position, id ->
            (parent.adapter.getItem(position) as String).apply {
                searchView?.setQuery(this,true)
            }
        }
    }
}
