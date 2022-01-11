package com.raghav.mvvmtodo.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "tasks")
data class TaskEntity(
    val title: String,
    val description: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
) : Serializable