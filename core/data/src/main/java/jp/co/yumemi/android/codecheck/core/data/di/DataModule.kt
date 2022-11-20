package jp.co.yumemi.android.codecheck.core.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jp.co.yumemi.android.codecheck.core.data.CachedGithubRepository
import jp.co.yumemi.android.codecheck.core.data.GithubRepository

@InstallIn(SingletonComponent::class)
@Module
abstract class DataModule {
    @Binds
    internal abstract fun CachedGithubRepository.bindGitHubRepository(): GithubRepository
}