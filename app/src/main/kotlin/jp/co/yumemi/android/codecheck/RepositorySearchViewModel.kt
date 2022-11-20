/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.codecheck

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse
import java.util.Date
import javax.inject.Inject
import jp.co.yumemi.android.codecheck.core.model.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.json.JSONObject

@HiltViewModel
class RepositorySearchViewModel @Inject constructor(
    private val client: HttpClient,
) : ViewModel() {

    private val _lastSearchDate = MutableStateFlow<Date?>(null)
    val lastSearchDate: StateFlow<Date?> = _lastSearchDate

    /**
     * リポジトリをキーワード検索して一致した一覧を返却する
     */
    fun searchRepositories(inputText: String): List<Repository> = runBlocking {
        val result = runCatching {
            val response: HttpResponse = withContext(Dispatchers.IO) {
                client.get("https://api.github.com/search/repositories") {
                    header("Accept", "application/vnd.github.v3+json")
                    parameter("q", inputText)
                }
            }

            val jsonBody = JSONObject(response.body<String>())

            val jsonItems = jsonBody.optJSONArray("items") ?: return@runCatching emptyList()

            val repositories = mutableListOf<Repository>()

            for (i in 0 until jsonItems.length()) {
                val jsonItem = jsonItems.optJSONObject(i) ?: continue

                val name = jsonItem.optString("full_name")
                val ownerIconUrl = jsonItem.optJSONObject("owner")
                    ?.optString("avatar_url")
                    ?: ""
                val language = jsonItem.optString("language")
                val stargazersCount = jsonItem.optLong("stargazers_count")
                val watchersCount = jsonItem.optLong("watchers_count")
                val forksCount = jsonItem.optLong("forks_count")
                val openIssuesCount = jsonItem.optLong("open_issues_count")

                repositories.add(
                    Repository(
                        name = name,
                        ownerIconUrl = ownerIconUrl,
                        language = language,
                        stargazersCount = stargazersCount,
                        watchersCount = watchersCount,
                        forksCount = forksCount,
                        openIssuesCount = openIssuesCount
                    )
                )
            }

            _lastSearchDate.value = Date()

            repositories.toList()
        }

        if (result.isFailure) {
            Log.e("RepositorySearchViewModel", "searchRepositories", result.exceptionOrNull())
        }

        return@runBlocking result.getOrDefault(emptyList())
    }
}
