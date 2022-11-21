/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.codecheck.feature.github

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Date
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

@HiltViewModel
internal class GithubRepoSearchViewModel @Inject constructor() : ViewModel() {
    sealed class Effect {
        data class NavigateToList(val query: String, val lastSearchDate: Date) : Effect()
    }

    private val _effect: MutableSharedFlow<Effect> = MutableSharedFlow()
    val effect: SharedFlow<Effect> = _effect

    fun searchGithubRepos(editText: CharSequence) {
        val query = editText.toString()
        val searchDate = Date()

        viewModelScope.launch {
            _effect.emit(Effect.NavigateToList(query, searchDate))
        }
    }
}
