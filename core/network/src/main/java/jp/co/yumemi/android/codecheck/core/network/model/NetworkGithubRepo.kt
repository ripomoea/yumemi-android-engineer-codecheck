package jp.co.yumemi.android.codecheck.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * https://api.github.com/search/repositories のレスポンス値
 */
@Serializable
data class SearchGithubReposResponse internal constructor(
    @SerialName(value = "items")
    val repositories: List<NetworkGithubRepo> = emptyList(),
)

@Serializable
data class NetworkGithubRepo internal constructor(
    @SerialName(value = "id")
    val id: Long,

    @SerialName(value = "name")
    val name: String = "",

    @SerialName(value = "owner")
    val owner: NetworkRepositoryOwner? = null,

    @SerialName(value = "description")
    val description: String,

    @SerialName(value = "language")
    val language: String? = null,

    @SerialName(value = "stargazers_count")
    val stargazersCount: Long,

    @SerialName(value = "watchers_count")
    val watchersCount: Long,

    @SerialName(value = "forks_count")
    val forksCount: Long,

    @SerialName(value = "open_issues_count")
    val openIssuesCount: Long,
)

@Serializable
data class NetworkRepositoryOwner internal constructor(
    @SerialName(value = "login")
    val login: String,

    @SerialName(value = "avatar_url")
    val iconUrl: String,
)