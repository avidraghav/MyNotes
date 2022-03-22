package com.raghav.mynotes.models

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "tasks")
@Keep
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = 0,
    val title: String,
    val description: String,
    // this field/column was added in database version 2, hence while migrating
    // a default value has to be provided for all the records who didn't had this field in version 1
    @ColumnInfo(defaultValue = "0")
    val deadLine: String
) : Serializable