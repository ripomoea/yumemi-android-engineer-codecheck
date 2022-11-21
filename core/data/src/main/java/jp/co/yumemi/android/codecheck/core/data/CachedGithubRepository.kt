package jp.co.yumemi.android.codecheck.core.data

import javax.inject.Inject
import jp.co.yumemi.android.codecheck.core.data.model.asModel
import jp.co.yumemi.android.codecheck.core.model.GithubRepo
import jp.co.yumemi.android.codecheck.core.network.GithubNetworkDataSource
import jp.co.yumemi.android.codecheck.core.network.model.NetworkGithubRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class CachedGithubRepository @Inject constructor(
    private val dataSource: GithubNetworkDataSource,
) : GithubRepository {
    override fun getGithubReposStream(query: String): Flow<List<GithubRepo>> = flow {
        val response = dataSource.searchGithubRepos(query)
        val githubRepos = response.repositories.map(NetworkGithubRepo::asModel)
        emit(githubRepos)
    }
}