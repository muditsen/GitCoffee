package com.gitcoffee.jek.presentation.di

import com.gitcoffee.jek.presentation.view.GitTrendActivity
import com.gitcoffee.jek.presentation.viewmodels.ViewModelModule
import dagger.Component
import javax.inject.Singleton


@Singleton @Component(modules = [AppModule::class,ViewModelModule::class])
interface AppComponent {

    /*@Component.Builder
    interface Builder{

        fun build(): AppComponent

        @BindsInstance
        fun application(@Named("appContext") application: Application):Builder

    }*/

    fun inject(gitTrendActivity: GitTrendActivity)

}