package com.example.composecodelab.data

data class TaskItemModel(val id: Int = 0, val label: String = "", var checked: Boolean = false)

fun getTasks() = List(30) { i -> TaskItemModel(i, "Task $i") }
