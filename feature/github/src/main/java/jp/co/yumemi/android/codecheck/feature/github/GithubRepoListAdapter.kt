package jp.co.yumemi.android.codecheck.feature.github

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import jp.co.yumemi.android.codecheck.core.model.GithubRepo
import jp.co.yumemi.android.codecheck.feature.github.databinding.ItemGithubrepoListBinding

internal class GithubRepoListAdapter(
    private val onGithubRepoClick: (GithubRepo) -> Unit,
) : ListAdapter<GithubRepo, GithubRepoViewHolder>(githubRepoDiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GithubRepoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_githubrepo_list, parent, false)
        return GithubRepoViewHolder(view, onGithubRepoClick)
    }

    override fun onBindViewHolder(holder: GithubRepoViewHolder, position: Int) {
        holder.bind(githubRepo = getItem(position))
    }

    override fun onViewRecycled(holder: GithubRepoViewHolder) {
        holder.unbind()
    }
}

internal class GithubRepoViewHolder(
    view: View,
    onGithubRepoClick: (GithubRepo) -> Unit,
) : RecyclerView.ViewHolder(view) {
    private val binding = ItemGithubrepoListBinding.bind(view)
    private var currentGithubRepo: GithubRepo? = null

    init {
        view.setOnClickListener {
            currentGithubRepo?.let(onGithubRepoClick)
        }
    }

    internal fun bind(githubRepo: GithubRepo) {
        currentGithubRepo = githubRepo

        binding.repositoryNameView.text = githubRepo.name
    }

    internal fun unbind() {
        currentGithubRepo = null
    }
}

private val githubRepoDiffCallback = object : DiffUtil.ItemCallback<GithubRepo>() {
    override fun areItemsTheSame(oldItem: GithubRepo, newItem: GithubRepo): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: GithubRepo, newItem: GithubRepo): Boolean {
        return oldItem == newItem
    }
}
