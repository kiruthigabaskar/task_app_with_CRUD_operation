package com.task_app.springboot_postgres.data.model

import org.jetbrains.annotations.NotNull
import java.time.LocalDateTime

data class TaskCreateRequest(
    val id: Long,
    @field: NotNull
    val description: String,
    val isReminderSet: Boolean,
    val isTaskOpen: Boolean,
    val createdOn: LocalDateTime,
    val priority: Priority
)
