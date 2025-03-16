package be.marche.pinpoint.di

import be.marche.pinpoint.camera.CameraViewModel
import be.marche.pinpoint.database.AppDatabase
import be.marche.pinpoint.database.DatabaseProvider
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

    // Provide ViewModel
    viewModel { SyncViewModel(get()) }
    viewModel { CameraViewModel() }

    viewModelOf(::SyncViewModel)

}
