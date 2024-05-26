package com.task_app.springboot_postgres.data.model

import java.time.LocalDateTime

data class TaskDto(
    val id: Long,
    val description: String,
    val isReminderSet: Boolean,
    val isTaskOpen: Boolean,
    val createdOn: LocalDateTime,
    val priority: Priority

)
