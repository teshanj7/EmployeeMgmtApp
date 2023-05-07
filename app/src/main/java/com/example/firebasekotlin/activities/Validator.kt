package com.example.firebasekotlin.activities

object Validator {

    fun validateInput(name: String, age: Int, salary: Int): Boolean {

        return !(age<=0 || salary<=0 || name.isEmpty())
    }
}