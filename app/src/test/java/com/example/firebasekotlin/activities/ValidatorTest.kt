package com.example.firebasekotlin.activities


import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ValidatorTest{

    @Test
    fun whenInputisValid(){
        val name = "Teshan"
        val age = 22
        val salary = 212332

        val result = Validator.validateInput(name, age, salary)

        assertThat(result).isEqualTo(true)

    }

    @Test
    fun whenInputisInvalid(){
        val name = ""
        val age = 0
        val salary = 212332

        val result = Validator.validateInput(name, age, salary)

        assertThat(result).isEqualTo(false)

    }
}