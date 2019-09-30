package com.katana.koin.data

import com.katana.koin.data.local.prefs.PrefsHelper
import com.katana.koin.data.remote.ApiHelper

/**
 * Created by Kaz on 10:14 2018-12-19
 */
interface DataManager : PrefsHelper, ApiHelper {
}