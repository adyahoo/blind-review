package com.example.composecodelab.data

data class TaskItemModel(val id:Int=0, val label:String="")

fun getTasks() = List(30) {i -> TaskItemModel(i, "Task $i")}
