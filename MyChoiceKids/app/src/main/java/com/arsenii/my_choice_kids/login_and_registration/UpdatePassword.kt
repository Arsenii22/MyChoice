package com.arsenii.my_choice_kids.login_and_registration

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.arsenii.my_choice_kids.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class UpdatePassword : AppCompatActivity() {

    private val auth = Firebase.auth

    private lateinit var email_field: EditText
    private lateinit var btn: Button
    private lateinit var loading: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_password)

        email_field = findViewById(R.id.change_password_email)
        btn = findViewById(R.id.change_password_btn)
        loading = findViewById(R.id.change_password_oading)

        btn.setOnClickListener {
            resetPassword()
        }
    }

    private fun resetPassword() {
        var email = email_field.text.toString()

        if (email.isEmpty()) {
            email_field.error = "Введите адрес электронной почты"
            email_field.requestFocus()
            return
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            email_field.error = "Некорректный адрес электронной почты!"
            email_field.requestFocus()
            return
        }

        loading.visibility = View.VISIBLE

        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener{ task ->
                if(task.isSuccessful) {
                    Toast.makeText(this, "Проверьте вашу электронную почту", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "Такого пользователя не существует", Toast.LENGTH_LONG).show()
                }
                loading.visibility = View.GONE
            }
    }
}