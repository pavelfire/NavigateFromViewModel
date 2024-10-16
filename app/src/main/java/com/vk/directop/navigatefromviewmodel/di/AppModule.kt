package com.vk.directop.navigatefromviewmodel.di

import com.vk.directop.navigatefromviewmodel.DefaultNavigator
import com.vk.directop.navigatefromviewmodel.Destination
import com.vk.directop.navigatefromviewmodel.DetailViewModel
import com.vk.directop.navigatefromviewmodel.HomeViewModel
import com.vk.directop.navigatefromviewmodel.LoginViewModel
import com.vk.directop.navigatefromviewmodel.Navigator
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    single<Navigator> {
        DefaultNavigator(startDestination = Destination.AuthGraph)
    }

    viewModelOf(::LoginViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::DetailViewModel)
}