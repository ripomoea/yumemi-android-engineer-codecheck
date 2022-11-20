package jp.co.yumemi.android.codecheck.core.network.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import javax.inject.Singleton
import jp.co.yumemi.android.codecheck.core.network.BuildConfig
import jp.co.yumemi.android.codecheck.core.network.GithubNetworkDataSource
import jp.co.yumemi.android.codecheck.core.network.KtorGithubNetworkDataSource
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

@InstallIn(SingletonComponent::class)
@Module
abstract class NetworkModule {
    @Binds
    internal abstract fun KtorGithubNetworkDataSource.bindGithubNetworkDataSource(): GithubNetworkDataSource

    companion object {
        @Provides
        @Singleton
        fun provideHttpClient(okHttpClient: OkHttpClient): HttpClient {
            val httpClient = HttpClient(OkHttp) {
                engine {
                    config {
                        preconfigured = okHttpClient
                        addInterceptor(
                            HttpLoggingInterceptor().apply {
                                level = if (BuildConfig.DEBUG) {
                                    HttpLoggingInterceptor.Level.BODY
                                } else {
                                    HttpLoggingInterceptor.Level.NONE
                                }
                            }
                        )
                    }
                }
                install(ContentNegotiation) {
                    json(
                        Json {
                            encodeDefaults = true
                            ignoreUnknownKeys = true
                        }
                    )
                }
            }
            return httpClient
        }

        @Provides
        @Singleton
        internal fun provideOkHttpClient(): OkHttpClient {
            val builder = OkHttpClient.Builder()
            if (BuildConfig.DEBUG) {
                val httpLoggingInterceptor = HttpLoggingInterceptor()
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
                builder.addNetworkInterceptor(httpLoggingInterceptor)
            }
            return builder.build()
        }
    }
}