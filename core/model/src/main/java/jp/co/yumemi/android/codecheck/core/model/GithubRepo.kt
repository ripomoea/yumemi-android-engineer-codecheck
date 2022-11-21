package jp.co.yumemi.android.codecheck.core.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * リポジトリ情報
 */
@Parcelize
data class GithubRepo(
    val id: Long,
    val name: String,
    val ownerName: String?,
    val ownerIconUrl: String?,
    val description: String,
    val language: String?,
    val stargazersCount: Long,
    val watchersCount: Long,
    val forksCount: Long,
    val openIssuesCount: Long,
) : Parcelable