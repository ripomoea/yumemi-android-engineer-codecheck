/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.codecheck.feature.github

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import jp.co.yumemi.android.codecheck.feature.github.databinding.FragmentGithubrepoListBinding
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GithubRepoListFragment : Fragment(R.layout.fragment_githubrepo_list) {

    private val githubRepoListViewModel: GithubRepoListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentGithubrepoListBinding.bind(view)

        val layoutManager = LinearLayoutManager(requireContext())
        val dividerItemDecoration =
            DividerItemDecoration(requireContext(), layoutManager.orientation)
        val adapter = GithubRepoListAdapter(githubRepoListViewModel::githubRepoItemClick)

        binding.recyclerView.also {
            it.layoutManager = layoutManager
            it.addItemDecoration(dividerItemDecoration)
            it.adapter = adapter
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            launch {
                githubRepoListViewModel.githubRepos.collect(adapter::submitList)
            }

            launch {
                githubRepoListViewModel.effect.collect { event ->
                    when (event) {
                        is GithubRepoListViewModel.Effect.NavigateToDetail -> {
                            val action = GithubRepoListFragmentDirections
                                .actionToGithubRepoDetailFragment(
                                    event.lastSearchDate,
                                    event.githubRepo,
                                )
                            findNavController().navigate(action)
                        }
                    }
                }
            }
        }
    }
}
