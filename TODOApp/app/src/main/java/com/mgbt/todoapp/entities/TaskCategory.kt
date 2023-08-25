package com.mgbt.todoapp.entities

//Las sealed class son empleadas para construir una herencia cerrada en la que
//el compilador conoce cuales son las únicas clases hijas, ya que no puede haber otras
sealed class TaskCategory(var isSelected: Boolean = true) {
    object Personal : TaskCategory();
    object Business : TaskCategory();
    object Other : TaskCategory();
}