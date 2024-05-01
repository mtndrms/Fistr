package com.fistr.fistr.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.fistr.fistr.data.model.User
import com.fistr.fistr.utils.Supabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import javax.inject.Named
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private val Context.preferencesDataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    private val Context.appDataStore: DataStore<Preferences> by preferencesDataStore(name = "fistr")

    @Provides
    @Singleton
    fun provideSupabaseClient(): SupabaseClient {
        return createSupabaseClient(
            supabaseUrl = Supabase.URL,
            supabaseKey = Supabase.KEY
        ) {
            install(Postgrest)
        }
    }

    @Provides
    @Singleton
    @Named("settings")
    fun providePreferencesDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.preferencesDataStore
    }

    @Provides
    @Singleton
    @Named("app")
    fun provideAppDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.appDataStore
    }
}
