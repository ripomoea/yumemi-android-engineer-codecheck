/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.codecheck

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import jp.co.yumemi.android.codecheck.core.model.GithubRepo
import jp.co.yumemi.android.codecheck.databinding.FragmentGithubrepoSearchBinding

@AndroidEntryPoint
class GithubRepoSearchFragment : Fragment(R.layout.fragment_githubrepo_search) {

    private val githubRepoSearchViewModel: GithubRepoSearchViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentGithubrepoSearchBinding.bind(view)

        val layoutManager = LinearLayoutManager(requireContext())
        val dividerItemDecoration =
            DividerItemDecoration(requireContext(), layoutManager.orientation)
        val adapter = RepositoryListAdapter(
            object : RepositoryListAdapter.OnItemClickListener {
                override fun onItemClick(githubRepo: GithubRepo) {
                    navigateToRepositoryDetailFragment(githubRepo)
                }
            }
        )

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            githubRepoSearchViewModel.githubRepos.collect(adapter::submitList)
        }

        binding.searchInputText
            .setOnEditorActionListener { editText, action, _ ->
                if (action == EditorInfo.IME_ACTION_SEARCH) {
                    val query = editText.text.toString()
                    githubRepoSearchViewModel.searchRepositories(query)
                    return@setOnEditorActionListener true
                }
                return@setOnEditorActionListener false
            }

        binding.recyclerView.also {
            it.layoutManager = layoutManager
            it.addItemDecoration(dividerItemDecoration)
            it.adapter = adapter
        }
    }

    fun navigateToRepositoryDetailFragment(githubRepo: GithubRepo) {
        val lastSearchDate = githubRepoSearchViewModel.lastSearchDate.value ?: return
        val action = GithubRepoSearchFragmentDirections
            .actionToGithubRepoDetailFragment(lastSearchDate, githubRepo)
        findNavController().navigate(action)
    }
}

val diffUtil = object : DiffUtil.ItemCallback<GithubRepo>() {
    override fun areItemsTheSame(oldItem: GithubRepo, newItem: GithubRepo): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: GithubRepo, newItem: GithubRepo): Boolean {
        return oldItem == newItem
    }

}

class RepositoryListAdapter(
    private val itemClickListener: OnItemClickListener,
) : ListAdapter<GithubRepo, RepositoryListAdapter.ViewHolder>(diffUtil) {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    interface OnItemClickListener {
        fun onItemClick(githubRepo: GithubRepo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_githubrepo_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val repository = getItem(position)
        (holder.itemView.findViewById<View>(R.id.repositoryNameView) as TextView).text =
            repository.name

        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(repository)
        }
    }
}
