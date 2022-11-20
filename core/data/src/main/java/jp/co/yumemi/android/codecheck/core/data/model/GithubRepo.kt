package jp.co.yumemi.android.codecheck.core.data.model

import jp.co.yumemi.android.codecheck.core.model.GithubRepo
import jp.co.yumemi.android.codecheck.core.network.model.NetworkGithubRepo

internal fun NetworkGithubRepo.asModel() = GithubRepo(
    name = name,
    ownerIconUrl = owner?.iconUrl ?: "",
    language = language ?: "",
    stargazersCount = stargazersCount,
    watchersCount = watchersCount,
    forksCount = forksCount,
    openIssuesCount = openIssuesCount,
)