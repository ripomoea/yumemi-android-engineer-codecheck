package jp.co.yumemi.android.codecheck.feature.github

import androidx.lifecycle.SavedStateHandle
import java.util.Date
import jp.co.yumemi.android.codecheck.core.model.GithubRepo
import jp.co.yumemi.android.codecheck.core.testing.repository.TestGithubRepository
import jp.co.yumemi.android.codecheck.core.testing.util.MainDispatcherRule
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GithubRepoListViewModelTest {
    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private val githubRepository = TestGithubRepository()

    private lateinit var viewModel: GithubRepoListViewModel

    @Before
    fun setup() {
        viewModel = GithubRepoListViewModel(
            savedStateHandle = SavedStateHandle(
                mapOf(
                    "query" to "kotlin",
                    "lastSearchDate" to Date(),
                )
            ),
            githubRepository = githubRepository,
        )
    }

    /**
     * インスタンス化時にリポジトリ一覧を取得できること
     */
    @Test
    fun githubRepos_whenInit_thenGetList() = runTest {
        val collectJob = launch(UnconfinedTestDispatcher()) { viewModel.githubRepos.collect() }

        githubRepository.sendGithubRepos(sampleGithubRepos)
        advanceUntilIdle()

        assertEquals(
            listOf(
                GithubRepo(
                    id  = 1,
                    name = "kotlin",
                    ownerName = "",
                    ownerIconUrl = "",
                    description = "",
                    language = "kotlin",
                    stargazersCount = 0,
                    watchersCount = 0,
                    forksCount = 0,
                    openIssuesCount = 0,
                ),
                GithubRepo(
                    id  = 2,
                    name = "swift",
                    ownerName = "",
                    ownerIconUrl = "",
                    description = "",
                    language = "swift",
                    stargazersCount = 0,
                    watchersCount = 0,
                    forksCount = 0,
                    openIssuesCount = 0,
                ),
                GithubRepo(
                    id  = 3,
                    name = "rust",
                    ownerName = "",
                    ownerIconUrl = "",
                    description = "",
                    language = "rust",
                    stargazersCount = 0,
                    watchersCount = 0,
                    forksCount = 0,
                    openIssuesCount = 0,
                ),
            ),
            viewModel.githubRepos.value
        )

        collectJob.cancel()
    }

    /**
     * リポジトリアイテム選択で詳細画面へ遷移する副作用が発生すること
     */
    @Test
    fun effect_whenGithubRepoItemClick_thenNavigateToDetail() = runTest {
        val collectJob = launch(UnconfinedTestDispatcher()) { viewModel.effect.collect() }

        viewModel.githubRepoItemClick(sampleGithubRepos[0])

        val effect = viewModel.effect.first()
        assertIs<GithubRepoListViewModel.Effect.NavigateToDetail>(effect)
        assertEquals(
            GithubRepo(
                id  = 1,
                name = "kotlin",
                ownerName = "",
                ownerIconUrl = "",
                description = "",
                language = "kotlin",
                stargazersCount = 0,
                watchersCount = 0,
                forksCount = 0,
                openIssuesCount = 0,
            ),
            effect.githubRepo
        )

        collectJob.cancel()
    }
}

private val sampleGithubRepos = listOf(
    GithubRepo(
        id  = 1,
        name = "kotlin",
        ownerName = "",
        ownerIconUrl = "",
        description = "",
        language = "kotlin",
        stargazersCount = 0,
        watchersCount = 0,
        forksCount = 0,
        openIssuesCount = 0,
    ),
    GithubRepo(
        id  = 2,
        name = "swift",
        ownerName = "",
        ownerIconUrl = "",
        description = "",
        language = "swift",
        stargazersCount = 0,
        watchersCount = 0,
        forksCount = 0,
        openIssuesCount = 0,
    ),
    GithubRepo(
        id  = 3,
        name = "rust",
        ownerName = "",
        ownerIconUrl = "",
        description = "",
        language = "rust",
        stargazersCount = 0,
        watchersCount = 0,
        forksCount = 0,
        openIssuesCount = 0,
    ),
)