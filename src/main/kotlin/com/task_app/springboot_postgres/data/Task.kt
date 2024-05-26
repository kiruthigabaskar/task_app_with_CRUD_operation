package com.task_app.springboot_postgres.data

import com.task_app.springboot_postgres.data.model.Priority
import jakarta.persistence.*
import java.time.LocalDateTime


@Entity
@Table(name = "tasks_test")
class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var id: Long = 0

    @Column(name = "description", unique = true)
    var description: String = ""

    @Column(name = "is_reminder_set", nullable = false)
    var isReminderSet: Boolean = false

    @Column(name = "is_task_open", nullable = false)
    var isTaskOpen: Boolean = false

    @Column(name = "created_on", nullable = false)
    var createdOn: LocalDateTime = LocalDateTime.now()

    @Enumerated(EnumType.STRING)
    var priority: Priority = Priority.HIGH

}