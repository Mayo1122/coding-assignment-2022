package com.test.codingassignment.ui.main.view.account

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.test.codingassignment.R
import com.test.codingassignment.databinding.ActivitySignInBinding
import com.test.codingassignment.ui.main.view.MainActivity
import com.test.codingassignment.ui.main.viewmodel.SignInViewModel
import com.test.codingassignment.utils.showSnackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInActivity : AppCompatActivity() {

    private val viewModel: SignInViewModel by viewModels()
    private lateinit var binding: ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in)
        binding.executePendingBindings()

        clickListeners()
        setupObserver()
    }

    private fun setupObserver() {
        viewModel.run {
            emailError.observe(this@SignInActivity) {
                if (it) {
                    binding.tilEmail.error = getText(R.string.username_required)
                    binding.tilEmail.isErrorEnabled = true
                } else {
                    binding.tilEmail.error = null
                    binding.tilEmail.isErrorEnabled = false
                }
            }
            passwordError.observe(this@SignInActivity) {
                if (it) {
                    binding.tilPassword.error = getText(R.string.password_required)
                    binding.tilPassword.isErrorEnabled = true
                } else {
                    binding.tilPassword.error = null
                    binding.tilPassword.isErrorEnabled = false
                }
            }

            moveNext.observe(this@SignInActivity) {
                login(
                    binding.tilEmail.editText?.text?.toString() ?: "",
                    binding.tilPassword.editText?.text?.toString() ?: ""
                )
            }
        }
    }

    private fun clickListeners() {
        binding.btnLogin.setOnClickListener {
            viewModel.validateInputs(
                binding.tilEmail.editText?.text.toString(),
                binding.tilPassword.editText?.text.toString()
            )
        }
        binding.btnRegister.setOnClickListener {
            startSignUpActivity()
        }
    }

    private fun login(email: String, password: String) {
        Firebase.auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("TAG", "signInWithEmail:success")
                    startHomeActivity()
                } else {
                    showSnackbar("Wrong username/password!")
                    Log.w("TAG", "signInWithEmail:failure", task.exception)
                }
            }
    }

    private fun startSignUpActivity() {
        val intent = Intent(this@SignInActivity, SignUpActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun startHomeActivity() {
        val intent = Intent(this@SignInActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

}