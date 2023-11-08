package com.example.composecodelab.ui.viewmodel

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.example.composecodelab.data.TaskItemModel
import com.example.composecodelab.data.getTasks


/**
 * Created by Maulidy Ady in 2023
 * Copyright (c) PT. TIMEDOOR INDONESIA
 */

class BasicStateViewModel: ViewModel() {
    private val _tasks = getTasks().toMutableStateList()
    val tasks: List<TaskItemModel>
        get() = _tasks

    fun remove(item: TaskItemModel) {
        _tasks.remove(item)
    }

    fun updateCheckedValue(item: TaskItemModel, checked: Boolean) {
        _tasks.find { it.id == item.id }?.let { task ->
            task.checked = checked
        }
    }
}