package com.task_app.springboot_postgres.service

import com.task_app.springboot_postgres.data.Task
import com.task_app.springboot_postgres.data.model.TaskCreateRequest
import com.task_app.springboot_postgres.data.model.TaskDto
import com.task_app.springboot_postgres.data.model.TaskUpdateRequest
import com.task_app.springboot_postgres.exception.TaskNotFoundException
import com.task_app.springboot_postgres.repository.TaskRepository
import org.springframework.data.util.ReflectionUtils
import org.springframework.stereotype.Service
import java.lang.reflect.Field
import java.util.stream.Collectors
import kotlin.reflect.full.memberProperties

@Service
class TaskService(private val repository: TaskRepository) {

    private fun convertEntityToDTO(task: Task): TaskDto {
        return TaskDto(
            task.id,
            task.description,
            task.isReminderSet,
            task.isTaskOpen,
            task.createdOn,
            task.priority
        )
    }

    private fun assignValuesToEntity(task: Task, taskCreateRequest: TaskCreateRequest) {
        task.id = task.id
        task.description = taskCreateRequest.description
        task.isReminderSet = taskCreateRequest.isReminderSet
        task.isTaskOpen = taskCreateRequest.isTaskOpen
        task.createdOn = taskCreateRequest.createdOn
        task.priority = taskCreateRequest.priority
    }

    private fun checkForTaskId(id: Long) {
        if (!repository.existsById(id)) {
            throw TaskNotFoundException("Task with ID :$id does not exist")
        }
    }

    fun getAllTasks(): List<TaskDto> =
        repository.findAll().stream().map(this::convertEntityToDTO).collect(Collectors.toList())

    fun getAllOpenTasks(): List<TaskDto> =
        repository.queryAllOpenTasks().stream().map(this::convertEntityToDTO).collect(Collectors.toList())

    fun getAllClosedTasks(): List<TaskDto> =
        repository.queryAllClosedTasks().stream().map(this::convertEntityToDTO).collect(Collectors.toList())

    fun getTaskByID(id: Long): TaskDto {
        checkForTaskId(id)
        val task: Task = repository.findTaskById(id)
        return convertEntityToDTO(task)
    }

    fun createTask(createRequest: TaskCreateRequest): TaskDto {
//        if(repository.doesDescriptionExits(createRequest.description)){
//            throw BadRequestException("There is already a task with description ${createRequest.description}")
//        }
        val task = Task()
        assignValuesToEntity(task, createRequest)
        val savedTask = repository.save(task)
        return convertEntityToDTO(savedTask)
    }

    fun updateTask(id: Long, updateRequest: TaskUpdateRequest): TaskDto {
        checkForTaskId(id)
        val existingTask: Task = repository.findTaskById(id)

        for (prop in TaskUpdateRequest::class.memberProperties) {
            if (prop.get(updateRequest) != null) {
                val field: Field? = ReflectionUtils.findField(Task::class.java) { it.name == prop.name }
                field?.let {
                    it.isAccessible = true
                    ReflectionUtils.setField(it, existingTask, prop.get(updateRequest))
                }
            }
        }

        val savedTask: Task = repository.save(existingTask)
        return convertEntityToDTO(savedTask)
    }

    fun deleteTask(id: Long): String {
        checkForTaskId(id)
        repository.deleteById(id)
        return "Task with id $id has been deleted."
    }

}

