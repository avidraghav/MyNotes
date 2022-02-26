package com.raghav.mynotes.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "tasks")
data class TaskEntity(
    val title: String,
    val description: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = 0,
    // ColumnInfo annotation is required as to give a value to the field deadline
    // (which is added after migration) for the records in which it was not present earlier.
    @ColumnInfo(defaultValue = "0")
    val deadLine: String
) : Serializable