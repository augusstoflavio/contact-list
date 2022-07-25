package br.com.mesainc.contacts

import android.app.Application
import br.com.mesainc.contacts.di.mainModule
import org.koin.core.context.startKoin

class App: Application() {


    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(
                mainModule
            )
        }
    }
}