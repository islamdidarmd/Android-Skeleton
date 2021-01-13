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
import com.example.androidskeleton.ui.widgets.MultiStateLayout
import com.example.androidskeleton.util.hideSoftKeyboard

class MainActivity : BaseActivity() {
    private val TAG = "MainActivity"
    private lateinit var _binding: ActivityMainBinding

    private val viewModel by viewModels<MainViewModel>()
    private var autoCompleteadapter: ArrayAdapter<String>? = null

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
        initObservers()
    }

    private fun initUI() {
        _binding.rvList.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(this@MainActivity, RecyclerView.VERTICAL))
            adapter = repoAdapter
        }

        _binding.tiQuery.setEndIconOnClickListener {
            val query = _binding.atQuery.text

            if (query.isNullOrEmpty()) return@setEndIconOnClickListener
            else {
                viewModel.searchRepo(query.toString())
                viewModel.insertToDb(query.toString())
            }
        }

        autoCompleteadapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            mutableListOf()
        )

        _binding.atQuery.apply {
            setAdapter(autoCompleteadapter)
            threshold = 1

            setOnItemClickListener { parent, view, position, id ->
                val selected = (parent.adapter.getItem(position) as String)
                _binding.atQuery.apply {
                    setText(selected)
                    setSelection(selected.length)
                }
                hideSoftKeyboard()
            }
        }
    }

    private fun initObservers() {
        //observing db changes for recent histories
        viewModel.getRecent().observe(this,
            Observer { list ->
                if (list == null) return@Observer
                Log.d(TAG, "Recent List size ${list.size}")

                autoCompleteadapter?.clear()
                autoCompleteadapter?.addAll(list)
            })

        viewModel.getRepoListLiveData().observe(this,
            Observer { data ->
                if (data == null) return@Observer

                when (data.state) {
                    STATE.LOADING -> {
                        //show loading ui
                        _binding.layoutList.setState(MultiStateLayout.State.LOADING)
                    }
                    STATE.SUCCESS -> {
                        //show success ui
                        updateList(data.data?.items)
                        _binding.layoutList.setState(MultiStateLayout.State.CONTENT)
                    }
                    STATE.ERROR -> {
                        //show error ui
                        _binding.layoutList.setState(MultiStateLayout.State.EMPTY)
                    }
                }
            })
    }

    private fun updateList(items: List<Repo>?) {
        if (items != null) repoAdapter.submitList(items)
    }
}
