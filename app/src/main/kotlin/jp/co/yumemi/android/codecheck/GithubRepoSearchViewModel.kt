/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.codecheck

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Date
import javax.inject.Inject
import jp.co.yumemi.android.codecheck.core.data.GithubRepository
import jp.co.yumemi.android.codecheck.core.model.GithubRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class GithubRepoSearchViewModel @Inject constructor(
    private val githubRepository: GithubRepository,
) : ViewModel() {

    private val _lastSearchDate = MutableStateFlow<Date?>(null)
    val lastSearchDate: StateFlow<Date?> = _lastSearchDate

    /**
     * キーワード検索に一致したリポジトリ一覧
     */
    val githubRepos: StateFlow<List<GithubRepo>> = githubRepository.getGithubReposStream()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList(),
        )

    /**
     * リポジトリをキーワード検索する
     */
    fun searchRepositories(query: String) {
        viewModelScope.launch {
            try {
                githubRepository.searchGithubRepos(query)
                _lastSearchDate.value = Date()
            } catch (exception: Throwable) {
                Log.e("RepositorySearchViewModel", "searchRepositories", exception)
            }
        }
    }
}
