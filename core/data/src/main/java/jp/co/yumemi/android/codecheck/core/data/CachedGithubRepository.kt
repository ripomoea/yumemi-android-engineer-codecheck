package jp.co.yumemi.android.codecheck.core.data

import javax.inject.Inject
import jp.co.yumemi.android.codecheck.core.data.model.asModel
import jp.co.yumemi.android.codecheck.core.model.GithubRepo
import jp.co.yumemi.android.codecheck.core.network.GithubNetworkDataSource
import jp.co.yumemi.android.codecheck.core.network.model.NetworkGithubRepo
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

internal class CachedGithubRepository @Inject constructor(
    private val dataSource: GithubNetworkDataSource,
) : GithubRepository {
    private val githubRepositoryFlow: MutableSharedFlow<List<GithubRepo>> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    override fun getGithubReposStream(): Flow<List<GithubRepo>> = githubRepositoryFlow

    override suspend fun searchGithubRepos(query: String) {
        val response = dataSource.searchGithubRepos(query)
        val githubRepos = response.repositories.map(NetworkGithubRepo::asModel)
        githubRepositoryFlow.emit(githubRepos)
    }
}