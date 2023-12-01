package com.znu.news.data.remote

import com.znu.news.data.local.prefs.AppPreferencesHelper
import com.znu.news.data.remote.services.RefreshTokenService
import com.znu.news.utils.SessionManager
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class AuthAuthenticator @Inject constructor(
    private val appPreferencesHelper: AppPreferencesHelper,
    private val refreshTokenService: RefreshTokenService,
    private val sessionManager: SessionManager
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        synchronized(this) {
            val refreshToken = appPreferencesHelper.refreshToken
            val newSessionResponse =
                runBlocking { refreshTokenService.refreshAccessToken() }
            val token = if (newSessionResponse.isSuccessful && newSessionResponse.body() != null) {
                newSessionResponse.body()?.let { body ->
                    runBlocking {
//                            appPreferencesHelper.accessToken = body.accessToken
//                            appPreferencesHelper.refreshToken = body.refreshToken
                    }
                    body.accessToken
                }
            } else null

            return if (token != null) response.request.newBuilder()
                .header("Authorization", "Bearer $token")
                .build() else {
                sessionManager.logout()
                null
            }
        }
    }
}