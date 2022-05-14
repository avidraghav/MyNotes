package com.raghav.mynotes.di.module

import android.content.Context
import androidx.room.Room
import com.raghav.mynotes.db.Migrations.MIGRATION_2_3
import com.raghav.mynotes.db.TasksDao
import com.raghav.mynotes.db.TasksDatabase
import com.raghav.mynotes.prefstore.TaskDatastore
import com.raghav.mynotes.prefstore.TaskDatastoreImpl
import com.raghav.mynotes.repository.TasksRepository
import com.raghav.mynotes.repository.TasksRepositoryImpl
import com.raghav.mynotes.utils.dispatchers.DefaultDispatchers
import com.raghav.mynotes.utils.dispatchers.DispatchersProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideTaskDatabase(
        @ApplicationContext context: Context,
    ) = Room.databaseBuilder(
        context,
        TasksDatabase::class.java,
        "tasks.db"
    ).addMigrations(MIGRATION_2_3)
        .build()

    @Singleton
    @Provides
    fun provideTaskDao(database: TasksDatabase) = database.dao()

    @Singleton
    @Provides
    fun provideTaskRepository(dao: TasksDao, datastore: TaskDatastore): TasksRepository {
        return TasksRepositoryImpl(dao, datastore)
    }

    @Singleton
    @Provides
    fun provideTaskDataStore(@ApplicationContext context: Context): TaskDatastore {
        return TaskDatastoreImpl(context)
    }

    @Singleton
    @Provides
    fun provideDispatchersProvider(): DispatchersProvider {
        return DefaultDispatchers()
    }
}