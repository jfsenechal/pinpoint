package be.marche.pinpoint.di

import androidx.room.Room
import be.marche.pinpoint.database.AppDatabase
import be.marche.pinpoint.sync.SyncViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {

    // Provide Database
    single {
        Room.databaseBuilder(get(), AppDatabase::class.java, "app_database")
            .build()
    }

    // Provide DAO
    single { get<AppDatabase>().categoryDao() }

    // Provide ViewModel
    viewModel { SyncViewModel(get()) }

    viewModelOf(::SyncViewModel)

}
