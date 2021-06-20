package id.yuhananda.bareksatest.di

import android.content.Context
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import id.yuhananda.bareksatest.data.source.DefaultProductRepository
import id.yuhananda.bareksatest.data.source.ProductDataSource
import id.yuhananda.bareksatest.data.source.ProductRepository
import id.yuhananda.bareksatest.data.source.local.BareksaTestDatabase
import id.yuhananda.bareksatest.data.source.local.ProductLocalDataSource
import id.yuhananda.bareksatest.data.source.remote.ProductRemoteDataSource
import id.yuhananda.bareksatest.util.ContextUtil
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {
    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class ProductRemoteDataSource

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class ProductLocalDataSource

    @Singleton
    @ProductRemoteDataSource
    @Provides
    fun provideProductRemoteDataSource(contextUtil: ContextUtil): ProductDataSource {
        return ProductRemoteDataSource(contextUtil)
    }

    @Singleton
    @ProductLocalDataSource
    @Provides
    fun provideProductLocalDataSource(
        database: BareksaTestDatabase,
        ioDispatcher: CoroutineDispatcher
    ): ProductDataSource {
        return ProductLocalDataSource(
            database.productDao(), ioDispatcher
        )
    }

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): BareksaTestDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            BareksaTestDatabase::class.java,
            "bareksaTest.db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideContextUtil(@ApplicationContext context: Context): ContextUtil {
        return ContextUtil(context)
    }

    @Singleton
    @Provides
    fun provideIoDispatcher() = Dispatchers.IO
}

@Module
@InstallIn(SingletonComponent::class)
abstract class ApplicationModuleBinds {
    @Binds
    abstract fun bindRepository(productRepository: DefaultProductRepository): ProductRepository
}