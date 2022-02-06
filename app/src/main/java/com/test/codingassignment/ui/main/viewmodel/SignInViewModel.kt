package com.test.codingassignment.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.codingassignment.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(): ViewModel() {

    private val _emailError = MutableLiveData<Boolean>()
    val emailError: LiveData<Boolean> = _emailError

    private val _passwordError = MutableLiveData<Boolean>()
    val passwordError: LiveData<Boolean> = _passwordError

    private val _moveNext = MutableLiveData<Boolean>()
    val moveNext: LiveData<Boolean> = _moveNext

    fun validateInputs(email: String?, password: String?): Boolean {
        return if (validateEmail(email) && validatePassword(password)) {
            _moveNext.value = true
            return true
        } else false
    }

    private fun validateEmail(email: String?): Boolean {
        _emailError.value = email?.isEmpty()
        return (emailError.value?.not()) ?: false
    }

    private fun validatePassword(password: String?): Boolean {
        _passwordError.value = password?.isEmpty()
        return (passwordError.value?.not()) ?: false
    }
}