package com.rejowan.lmsteamprofile.di

import com.rejowan.lmsteamprofile.domain.repository.MainRepository
import com.rejowan.lmsteamprofile.domain.repositoryImpl.MainRepositoryImpl
import com.rejowan.lmsteamprofile.viewmodel.MainViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    single<MainRepository> { MainRepositoryImpl() }
    viewModel { MainViewModel(get()) }
}