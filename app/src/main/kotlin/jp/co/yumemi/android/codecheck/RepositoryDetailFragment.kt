/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.codecheck

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import jp.co.yumemi.android.codecheck.databinding.FragmentRepositoryDetailBinding

@AndroidEntryPoint
class RepositoryDetailFragment : Fragment(R.layout.fragment_repository_detail) {

    private val args: RepositoryDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("検索した日時", args.lastSearchDate.toString())

        val binding = FragmentRepositoryDetailBinding.bind(view)

        val repository = args.repository

        binding.ownerIconView.load(repository.ownerIconUrl)
        binding.nameView.text = repository.name
        binding.languageView.text =
            getString(R.string.repository_detail_written_language, repository.language)
        binding.starsView.text =
            getString(R.string.repository_detail_stars, repository.stargazersCount)
        binding.watchersView.text =
            getString(R.string.repository_detail_watchers, repository.watchersCount)
        binding.forksView.text = getString(R.string.repository_detail_forks, repository.forksCount)
        binding.openIssuesView.text =
            getString(R.string.repository_detail_open_issues, repository.openIssuesCount)
    }
}
