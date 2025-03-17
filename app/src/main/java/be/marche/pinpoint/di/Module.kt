package be.marche.pinpoint.di

import be.marche.pinpoint.database.AppDatabase
import be.marche.pinpoint.database.DatabaseProvider
import be.marche.pinpoint.item.ItemViewModel
import be.marche.pinpoint.sync.SyncViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {

    // Provide Database
    single {
        DatabaseProvider.getDatabase(get())
    }

    // Provide DAO
    single { get<AppDatabase>().categoryDao() }
    single { get<AppDatabase>().itemDao() }

    // Provide ViewModel
    viewModel { SyncViewModel(get()) }
    viewModel { ItemViewModel(get()) }

    viewModelOf(::SyncViewModel)
    viewModelOf(::ItemViewModel)

}
