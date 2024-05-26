package com.task_app.springboot_postgres.repository

import com.task_app.springboot_postgres.data.Task
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface TaskRepository : JpaRepository<Task, Long> {

    fun findTaskById(id: Long): Task

    @Query(value = "SELECT * FROM tasks_test WHERE is_task_open= TRUE", nativeQuery = true)
    fun queryAllOpenTasks(): List<Task>

    @Query(value = "SELECT * FROM tasks_test WHERE is_task_open= FALSE", nativeQuery = true)
    fun queryAllClosedTasks(): List<Task>


}