package com.urbansportsclub.kmm.shared

import com.urbansportsclub.kmm.shared.cache.Database
import com.urbansportsclub.kmm.shared.cache.DatabaseDriverFactory
import com.urbansportsclub.kmm.shared.entity.RocketLaunch
import com.urbansportsclub.kmm.shared.network.SpaceXApi

class SpaceXSDK(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = Database(databaseDriverFactory)
    private val api = SpaceXApi()

    @Throws(Exception::class) suspend fun getLaunches(forceReload: Boolean): List<RocketLaunch> {
        val cachedLaunches = database.getAllLaunches()
        return if (cachedLaunches.isNotEmpty() && !forceReload) {
            cachedLaunches
        } else {
            api.getAllLaunches().also {
                database.clearDatabase()
                database.createLaunches(it)
            }
        }
    }
}