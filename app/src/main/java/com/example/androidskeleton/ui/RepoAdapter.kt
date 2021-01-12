package com.example.androidskeleton.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.example.androidskeleton.R
import com.example.androidskeleton.data.model.search.Repo
import com.example.androidskeleton.databinding.RowRepoListBinding

object RepoDiffCallback: DiffUtil.ItemCallback<Repo>(){
    override fun areItemsTheSame(oldItem: Repo, newItem: Repo) = oldItem.html_url == newItem.html_url
    override fun areContentsTheSame(oldItem: Repo, newItem: Repo): Boolean {
        return oldItem==newItem
    }
}

class RepoAdapter(val onclick: (repo: Repo) -> Unit) :
    ListAdapter<Repo,RepoAdapter.ViewHolder>(RepoDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.row_repo_list, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val repo = getItem(position)
        with(holder.binding) {
            tvRepoName.text = repo.full_name
            tvDescription.text = repo.description
            ivAvatar.load(repo.owner.avatar_url)
        }
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val binding = RowRepoListBinding.bind(v)

        init {
            binding.root.setOnClickListener { onclick(getItem(adapterPosition)) }
        }
    }
}