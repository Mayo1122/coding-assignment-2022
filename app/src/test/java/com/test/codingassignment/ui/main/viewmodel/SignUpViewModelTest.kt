package com.test.codingassignment.ui.main.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.test.codingassignment.utils.getOrAwaitValue
import org.junit.Assert.*
import org.junit.Rule

import org.junit.Test
import org.junit.rules.TestRule

class SignUpViewModelTest {

    @Rule
    @JvmField
    var rule: TestRule = InstantTaskExecutorRule()

    @Test
    fun testValidInputs_inputOptionals_shouldValidateFail() {
        val vm: SignUpViewModel = SignUpViewModel()
        assertFalse("Expected to validate null inputs as not valid but failed",vm.validateInputs(null,null,null))
        assertTrue("Expected to validate email null input as not valid but failed", vm.emailError.getOrAwaitValue())
    }

    @Test
    fun testValidInputs_inputEmptyValues_shouldValidateFail() {
        val vm: SignUpViewModel = SignUpViewModel()
        vm.validateInputs("","","")
        assertTrue("Expected to validate empty inputs as not valid but failed", vm.emailError.getOrAwaitValue())
    }

    @Test
    fun testValidInputs_correctInputs_shouldValidateSuccess() {
        val vm: SignUpViewModel = SignUpViewModel()
        vm.validateInputs("wjahat.mayo@gmail.com","1122","Male")
        assertFalse("Expected to validate correct email input as valid but failed", vm.emailError.getOrAwaitValue())
        assertFalse("Expected to validate correct password input as valid but failed", vm.passwordError.getOrAwaitValue())
        assertFalse("Expected to validate correct gender input as valid but failed", vm.genderError.getOrAwaitValue())
        assertTrue("Expected to move Next but failed", vm.moveNext.getOrAwaitValue())
    }
}