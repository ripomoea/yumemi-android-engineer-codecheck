package jp.co.yumemi.android.codecheck.core.data

import jp.co.yumemi.android.codecheck.core.model.GithubRepo
import kotlinx.coroutines.flow.Flow

interface GithubRepository {
    fun getGithubReposStream(query: String): Flow<List<GithubRepo>>
}