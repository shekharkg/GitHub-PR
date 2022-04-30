package com.shekharkg.githubpr

import android.app.Application
import com.shekharkg.githubpr.manual_DI.AppContainer

class GitHubPR : Application() {

    // Instance of AppContainer that will be used by all the Activities of the app
    val appContainer = AppContainer()
}