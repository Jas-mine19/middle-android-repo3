package ru.yandex.architectureproject

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.yandex.architectureproject.model.Task
import ru.yandex.architectureproject.model.TaskAction
import ru.yandex.architectureproject.model.TaskState



class TaskViewModel : ViewModel() {

    private val _state = MutableStateFlow(TaskState())
    val state: StateFlow<TaskState> = _state

    fun reduce(action: TaskAction) {
        when (action) {

            is TaskAction.LoadTasks -> {
                // пока ничего не делаем
            }

            is TaskAction.AddTask -> {
                val newTask = Task(
                    id = (_state.value.tasks.maxOfOrNull { it.id } ?: 0) + 1,
                    text = action.text,
                    isDone = false
                )
                _state.value = _state.value.copy(
                    tasks = _state.value.tasks + newTask
                )
            }

            is TaskAction.UpdateTaskStatus -> {
                _state.value = _state.value.copy(
                    tasks = _state.value.tasks.map {
                        if (it.id == action.taskId)
                            it.copy(isDone = action.isDone)
                        else it
                    }
                )
            }

            is TaskAction.DeleteTask -> {
                _state.value = _state.value.copy(
                    tasks = _state.value.tasks.filterNot { it.id == action.taskId }
                )
            }
        }
    }
}