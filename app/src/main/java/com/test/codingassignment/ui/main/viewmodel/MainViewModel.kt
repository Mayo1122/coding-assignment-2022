package com.test.codingassignment.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import com.test.codingassignment.data.repository.MainRepository
import javax.inject.Inject

class MainViewModel @Inject constructor(private val mainRepository: MainRepository) : ViewModel()