package jp.co.yumemi.android.codecheck.core.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import javax.inject.Inject
import javax.inject.Singleton
import jp.co.yumemi.android.codecheck.core.network.model.SearchGithubReposResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Singleton
internal class KtorGithubNetworkDataSource @Inject constructor(
    private val httpClient: HttpClient,
) : GithubNetworkDataSource {
    override suspend fun searchGithubRepos(query: String): SearchGithubReposResponse {
        return withContext(Dispatchers.IO) {
            httpClient.get("https://api.github.com/search/repositories") {
                header("Accept", "application/vnd.github.v3+json")
                parameter("q", query)
            }.body()
        }
    }
}