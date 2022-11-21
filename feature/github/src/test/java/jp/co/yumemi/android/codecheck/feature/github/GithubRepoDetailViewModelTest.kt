package jp.co.yumemi.android.codecheck.feature.github

import androidx.lifecycle.SavedStateHandle
import java.util.Date
import jp.co.yumemi.android.codecheck.core.model.GithubRepo
import jp.co.yumemi.android.codecheck.core.testing.util.MainDispatcherRule
import kotlin.test.assertEquals
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GithubRepoDetailViewModelTest {
    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: GithubRepoDetailViewModel

    @Before
    fun setup() {
        viewModel = GithubRepoDetailViewModel(
            savedStateHandle = SavedStateHandle(
                mapOf(
                    "githubRepo" to sampleGithubRepo,
                    "lastSearchDate" to Date(),
                )
            ),
        )
    }

    /**
     * インスタンス化時にリポジトリを取得できること
     */
    @Test
    fun githubRepo_whenInit_thenGetInstance() = runTest {
        val collectJob = launch(UnconfinedTestDispatcher()) { viewModel.githubRepo.collect() }

        advanceUntilIdle()

        assertEquals(
            GithubRepo(
                name = "kotlin",
                ownerIconUrl = "",
                language = "kotlin",
                stargazersCount = 0,
                watchersCount = 0,
                forksCount = 0,
                openIssuesCount = 0,
            ),
            viewModel.githubRepo.value
        )

        collectJob.cancel()
    }
}

private val sampleGithubRepo = GithubRepo(
    name = "kotlin",
    ownerIconUrl = "",
    language = "kotlin",
    stargazersCount = 0,
    watchersCount = 0,
    forksCount = 0,
    openIssuesCount = 0,
)