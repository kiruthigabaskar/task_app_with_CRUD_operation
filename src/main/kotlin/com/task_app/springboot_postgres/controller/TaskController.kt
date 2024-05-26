package com.task_app.springboot_postgres.controller

import com.task_app.springboot_postgres.data.model.TaskCreateRequest
import com.task_app.springboot_postgres.data.model.TaskDto
import com.task_app.springboot_postgres.data.model.TaskUpdateRequest
import com.task_app.springboot_postgres.service.TaskService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/")
class TaskController(private val service: TaskService) {

    @PostMapping("/create")
    fun createTask(@RequestBody createRequest: TaskCreateRequest): ResponseEntity<TaskDto> =
        ResponseEntity(service.createTask(createRequest), HttpStatus.OK)

    @GetMapping("/all-tasks")
    fun getAllTasks(): ResponseEntity<List<TaskDto>> =
        ResponseEntity(service.getAllTasks(), HttpStatus.OK)

    @GetMapping("/open-tasks")
    fun getAllOpenTasks(): ResponseEntity<List<TaskDto>> =
        ResponseEntity(service.getAllOpenTasks(), HttpStatus.OK)

    @GetMapping("/closed-tasks")
    fun getAllClosedTasks(): ResponseEntity<List<TaskDto>> =
        ResponseEntity(service.getAllClosedTasks(), HttpStatus.OK)

    @GetMapping("tasks/{id}")
    fun getTaskById(@PathVariable id: Long): ResponseEntity<TaskDto> =
        ResponseEntity(service.getTaskByID(id), HttpStatus.OK)

    @PatchMapping("update/{id}")
    fun updateTask(
        @PathVariable id: Long,
        @RequestBody updateRequest: TaskUpdateRequest
    ):
            ResponseEntity<TaskDto> = ResponseEntity(service.updateTask(id, updateRequest), HttpStatus.OK)

    @DeleteMapping("delete/{id}")
    fun deleteTask(@PathVariable id: Long): ResponseEntity<String> =
        ResponseEntity(service.deleteTask(id), HttpStatus.OK)
}