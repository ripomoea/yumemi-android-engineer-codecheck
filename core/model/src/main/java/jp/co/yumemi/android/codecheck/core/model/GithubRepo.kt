package jp.co.yumemi.android.codecheck.core.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * リポジトリ情報
 */
@Parcelize
data class GithubRepo(
    val name: String,
    val ownerIconUrl: String,
    val language: String,
    val stargazersCount: Long,
    val watchersCount: Long,
    val forksCount: Long,
    val openIssuesCount: Long,
) : Parcelable