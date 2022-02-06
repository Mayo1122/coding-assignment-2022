package com.test.codingassignment.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.codingassignment.ui.main.models.Gender
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor() : ViewModel() {

    private val _emailError = MutableLiveData<Boolean>()
    val emailError: LiveData<Boolean> = _emailError

    private val _passwordError = MutableLiveData<Boolean>()
    val passwordError: LiveData<Boolean> = _passwordError

    private val _genderError = MutableLiveData<Boolean>()
    val genderError: LiveData<Boolean> = _genderError

    private val _moveNext = MutableLiveData<Boolean>()
    val moveNext: LiveData<Boolean> = _moveNext

    // gender values
    val genders = arrayOf(Gender.MALE.name, Gender.FEMALE.name, Gender.OTHER.name)

    fun validateInputs(email: String?, password: String?, gender: String?): Boolean {
        return if (validateEmail(email) && validatePassword(password) && validateGender(gender)){
            _moveNext.value = true
            return true
        } else false
    }

    private fun validateEmail(email: String?): Boolean {
        _emailError.value = email?.isEmpty() ?: true
        return (emailError.value?.not()) ?: false
    }

    private fun validatePassword(password: String?): Boolean {
        _passwordError.value = password?.isEmpty() ?: true
        return (passwordError.value?.not()) ?: false
    }

    private fun validateGender(gender: String?): Boolean {
        _genderError.value = gender?.isEmpty() ?: true
        return (genderError.value?.not()) ?: false
    }

}