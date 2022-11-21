package jp.co.yumemi.android.codecheck.core.testing.repository

import jp.co.yumemi.android.codecheck.core.data.GithubRepository
import jp.co.yumemi.android.codecheck.core.model.GithubRepo
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class TestGithubRepository : GithubRepository {
    private val githubReposFlow: MutableSharedFlow<List<GithubRepo>> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    override fun getGithubReposStream(query: String): Flow<List<GithubRepo>> = githubReposFlow

    fun sendGithubRepos(githubRepos: List<GithubRepo>) {
        githubReposFlow.tryEmit(githubRepos)
    }
}