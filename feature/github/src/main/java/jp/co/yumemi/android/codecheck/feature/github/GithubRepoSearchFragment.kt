/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.codecheck.feature.github

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import jp.co.yumemi.android.codecheck.feature.github.databinding.FragmentGithubrepoSearchBinding

@AndroidEntryPoint
class GithubRepoSearchFragment : Fragment(R.layout.fragment_githubrepo_search) {

    private val githubRepoSearchViewModel: GithubRepoSearchViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentGithubrepoSearchBinding.bind(view)

        binding.searchInputText.setOnEditorActionListener { editText, action, _ ->
            if (action == EditorInfo.IME_ACTION_SEARCH) {
                githubRepoSearchViewModel.searchGithubRepos(editText.text)
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            githubRepoSearchViewModel.effect.collect { event ->
                when (event) {
                    is GithubRepoSearchViewModel.Effect.NavigateToList -> {
                        val action = GithubRepoSearchFragmentDirections
                            .actionToGithubRepoListFragment(event.query, event.lastSearchDate)
                        findNavController().navigate(action)
                    }
                }
            }
        }
    }
}
