/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.codecheck

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
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
        val adapter = GithubRepoListAdapter { githubRepo ->
            navigateToRepositoryDetailFragment(githubRepo)
        }

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

    private fun navigateToRepositoryDetailFragment(githubRepo: GithubRepo) {
        val lastSearchDate = githubRepoSearchViewModel.lastSearchDate.value ?: return
        val action = GithubRepoSearchFragmentDirections
            .actionToGithubRepoDetailFragment(lastSearchDate, githubRepo)
        findNavController().navigate(action)
    }
}
