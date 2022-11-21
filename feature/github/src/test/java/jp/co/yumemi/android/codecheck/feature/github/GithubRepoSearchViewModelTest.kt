package jp.co.yumemi.android.codecheck.feature.github

import jp.co.yumemi.android.codecheck.core.testing.util.MainDispatcherRule
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GithubRepoSearchViewModelTest {
    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: GithubRepoSearchViewModel

    @Before
    fun setup() {
        viewModel = GithubRepoSearchViewModel()
    }

    /**
     * Effect - 検索キーアクション時に一覧画面への画面遷移副作用が発生すること
     */
    @Test
    fun effect_whenSearchGithubRepos_thenNavigateToList() = runTest {
        val collectJob = launch(UnconfinedTestDispatcher()) { viewModel.effect.collect() }

        viewModel.searchGithubRepos("searchText")

        val effect = viewModel.effect.first()
        assertIs<GithubRepoSearchViewModel.Effect.NavigateToList>(effect)
        assertEquals("searchText", effect.query)

        collectJob.cancel()
    }
}