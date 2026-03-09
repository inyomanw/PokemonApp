package com.inyomanw.pokemonapp.data.local

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import androidx.core.content.edit

@Singleton
class SessionManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val prefs = context.getSharedPreferences("pokemon_app_session", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_LOGGED_IN_USERNAME = "logged_in_username"
    }

    fun saveSession(username: String) {
        prefs.edit { putString(KEY_LOGGED_IN_USERNAME, username) }
    }

    fun getLoggedInUsername(): String? {
        return prefs.getString(KEY_LOGGED_IN_USERNAME, null)
    }

    fun clearSession() {
        prefs.edit { remove(KEY_LOGGED_IN_USERNAME) }
    }

    fun isLoggedIn(): Boolean {
        return getLoggedInUsername() != null
    }
}
