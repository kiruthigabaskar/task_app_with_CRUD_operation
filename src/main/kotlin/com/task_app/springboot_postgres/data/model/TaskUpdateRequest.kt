package com.task_app.springboot_postgres.data.model

data class TaskUpdateRequest(
    val description: String?,
    val isReminderSer: Boolean?,
    val isTaskOpen: Boolean?,
    val priority: Priority
)
