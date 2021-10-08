package com.katana.koin

import android.app.Application
import com.utils.LogUtil
import dagger.hilt.android.HiltAndroidApp
import okhttp3.CipherSuite
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.TlsVersion
import java.util.*
import java.util.concurrent.TimeUnit

@HiltAndroidApp
class MvvmKoinApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        LogUtil.init(true)
        initFastNetworking()
    }

    private fun initFastNetworking() {
        val spec = ConnectionSpec.Builder(ConnectionSpec.COMPATIBLE_TLS)
                .supportsTlsExtensions(true)
                .tlsVersions(TlsVersion.TLS_1_2, TlsVersion.TLS_1_1, TlsVersion.TLS_1_0)
                .cipherSuites(
                        CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
                        CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
                        CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256,
                        CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA,
                        CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA,
                        CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA,
                        CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA,
                        CipherSuite.TLS_ECDHE_ECDSA_WITH_RC4_128_SHA,
                        CipherSuite.TLS_ECDHE_RSA_WITH_RC4_128_SHA,
                        CipherSuite.TLS_DHE_RSA_WITH_AES_128_CBC_SHA,
                        CipherSuite.TLS_DHE_DSS_WITH_AES_128_CBC_SHA,
                        CipherSuite.TLS_DHE_RSA_WITH_AES_256_CBC_SHA)
                .build()

        val builder = OkHttpClient().newBuilder().connectionSpecs(Collections.singletonList(spec))
        builder.addInterceptor { chain ->
            val original = chain.request()

            val requestBuilder = original.newBuilder()
                    .method(original.method(), original.body())

//            if (dataManager.getToken().isNotEmpty()) {
//                requestBuilder.header(AUTHORIZATION, dataManager.getToken())
//                requestBuilder.header(LANGUAGE, LocalManageUtil.getSetLanguageLocale(this).language)
//            }

            chain.proceed(requestBuilder.build())
        }

        val okHttpClient = builder
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build()

//        AndroidNetworking.initialize(this, okHttpClient)
//        if (BuildConfig.DEBUG) {
//            AndroidNetworking.enableLogging(HttpLoggingInterceptor.Level.BODY)
//        }
    }

}