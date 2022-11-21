package jp.co.yumemi.android.codecheck.core.data.model

import jp.co.yumemi.android.codecheck.core.model.GithubRepo
import jp.co.yumemi.android.codecheck.core.network.model.NetworkGithubRepo

internal fun NetworkGithubRepo.asModel() = GithubRepo(
    id = id,
    name = name,
    ownerName = owner?.login,
    ownerIconUrl = owner?.iconUrl,
    description = description,
    language = language,
    stargazersCount = stargazersCount,
    watchersCount = watchersCount,
    forksCount = forksCount,
    openIssuesCount = openIssuesCount,
)