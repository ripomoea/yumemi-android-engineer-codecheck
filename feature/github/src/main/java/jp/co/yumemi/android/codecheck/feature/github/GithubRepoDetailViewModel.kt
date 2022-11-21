package jp.co.yumemi.android.codecheck.feature.github

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Date
import javax.inject.Inject
import jp.co.yumemi.android.codecheck.core.model.GithubRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel
internal class GithubRepoDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val githubRepo: StateFlow<GithubRepo> =
        MutableStateFlow(checkNotNull(savedStateHandle["githubRepo"]))

    init {
        val lastSearchDate: Date = checkNotNull(savedStateHandle["lastSearchDate"])
        // TODO: この処理が必要かどうかを有識者に伺う
        Log.d("検索した日時", lastSearchDate.toString())
    }
}