package jp.co.yumemi.android.codecheck.core.network

import jp.co.yumemi.android.codecheck.core.network.model.SearchGithubReposResponse

interface GithubNetworkDataSource {
    /**
     * リポジトリをキーワード検索して一致した一覧を返却する
     */
    suspend fun searchGithubRepos(query: String): SearchGithubReposResponse
}