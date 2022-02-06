package com.test.codingassignment.ui.main.view.account

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View.OnFocusChangeListener
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.test.codingassignment.R
import com.test.codingassignment.databinding.ActivitySignUpBinding
import com.test.codingassignment.ui.main.viewmodel.SignUpViewModel
import com.test.codingassignment.utils.showSnackbar
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {

    private var builder: AlertDialog.Builder? = null
    private val viewModel: SignUpViewModel by viewModels()
    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
        binding.executePendingBindings()

        setupObserver()
        initViews()
        clickListeners()
        focusChangeListeners()
    }

    private fun setupObserver() {
        viewModel.run {
            emailError.observe(this@SignUpActivity) {
                if (it) {
                    binding.tilEmail.error = getText(R.string.username_required)
                    binding.tilEmail.isErrorEnabled = true
                } else {
                    binding.tilEmail.error = null
                    binding.tilEmail.isErrorEnabled = false
                }
            }
            passwordError.observe(this@SignUpActivity) {
                if (it) {
                    binding.tilPassword.error = getText(R.string.password_required)
                    binding.tilPassword.isErrorEnabled = true
                } else {
                    binding.tilPassword.error = null
                    binding.tilPassword.isErrorEnabled = false
                }
            }
            genderError.observe(this@SignUpActivity) {
                if (it) {
                    binding.tilGender.error = getText(R.string.gender_required)
                    binding.tilGender.isErrorEnabled = true
                } else {
                    binding.tilGender.error = null
                    binding.tilGender.isErrorEnabled = false
                }
            }

            moveNext.observe(this@SignUpActivity) {
                firebaseSignUp(
                    binding.tilEmail.editText?.text?.toString() ?: "",
                    binding.tilPassword.editText?.text?.toString() ?: ""
                )
            }
        }
    }

    private fun initViews() {
        // make dialogue builder for gender selection
        builder = AlertDialog.Builder(this@SignUpActivity)
        builder!!.setTitle("Select Gender")
        builder!!.setItems(viewModel.genders) { dialog, which ->
            binding.tilGender.editText!!.setText(viewModel.genders.get(which))
            dialog.dismiss()
        }
    }

    private fun focusChangeListeners() {
        binding.tilGender.editText!!.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.tilGender.clearFocus()
                builder!!.show()
            }
        }
    }

    private fun clickListeners() {
        binding.btnRegister.setOnClickListener {
            viewModel.validateInputs(
                binding.tilEmail.editText?.text?.toString(),
                binding.tilPassword.editText?.text?.toString(),
                binding.tilGender.editText?.text?.toString()
            )
        }

        binding.btnLogin.setOnClickListener {
            startSignInActivity()
        }
    }

    private fun firebaseSignUp(email: String, password: String) {
        Firebase.auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("TAG", "createUserWithEmail:success")
                    startSignInActivity()
                } else {
                    Log.w("TAG", "createUserWithEmail:failure", task.exception)
                    showSnackbar("Something went wrong!")
                }
            }
    }

    private fun startSignInActivity() {
        val intent = Intent(this@SignUpActivity, SignInActivity::class.java)
        startActivity(intent)
        finish()
    }
}