package id.yuhananda.bareksatest.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.yuhananda.bareksatest.util.ContextUtil
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object UtilModule {
    @Singleton
    @Provides
    fun provideContextUtil(context: Context): ContextUtil {
        return ContextUtil(context)
    }
}