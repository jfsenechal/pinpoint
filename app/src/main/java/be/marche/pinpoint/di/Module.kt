package be.marche.pinpoint.di

import be.marche.pinpoint.category.CategoryViewModel
import be.marche.pinpoint.database.AppDatabase
import be.marche.pinpoint.database.DatabaseProvider
import be.marche.pinpoint.item.ItemViewModel
import be.marche.pinpoint.network.ConnectivityManager
import be.marche.pinpoint.network.ConnectivityViewModel
import be.marche.pinpoint.sync.SyncViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    // Provide Database
    single {
        DatabaseProvider.getDatabase(get())
    }

    // Provide DAO
    single { get<AppDatabase>().categoryDao() }
    single { get<AppDatabase>().itemDao() }
    single { ConnectivityManager(get()) }

    // Provide ViewModel
    viewModel { SyncViewModel(get()) }
    viewModel { ItemViewModel(get()) }
    viewModel { CategoryViewModel(get()) }
    viewModel { ConnectivityViewModel(get()) }

    //  viewModelOf(::SyncViewModel)
    //   viewModelOf(::ItemViewModel)

}
