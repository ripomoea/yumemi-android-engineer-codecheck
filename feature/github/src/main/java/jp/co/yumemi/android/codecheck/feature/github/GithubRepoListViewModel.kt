/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.codecheck.feature.github

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Date
import javax.inject.Inject
import jp.co.yumemi.android.codecheck.core.data.GithubRepository
import jp.co.yumemi.android.codecheck.core.model.GithubRepo
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
internal class GithubRepoListViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    githubRepository: GithubRepository,
) : ViewModel() {
    sealed class Effect {
        data class NavigateToDetail(val githubRepo: GithubRepo, val lastSearchDate: Date) : Effect()
    }

    private val _effect: MutableSharedFlow<Effect> = MutableSharedFlow()
    val effect: SharedFlow<Effect> = _effect

    /**
     * キーワード検索に一致したリポジトリ一覧
     */
    val githubRepos: StateFlow<List<GithubRepo>> =
        githubRepository.getGithubReposStream(checkNotNull(savedStateHandle["query"]))
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList(),
            )

    fun githubRepoItemClick(githubRepo: GithubRepo) {
        val lastSearchDate: Date = checkNotNull(savedStateHandle["lastSearchDate"])

        viewModelScope.launch {
            _effect.emit(Effect.NavigateToDetail(githubRepo, lastSearchDate))
        }
    }
}
