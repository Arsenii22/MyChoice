package com.arsenii.my_choice.login_and_registration

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.arsenii.my_choice.MainActivity

import com.arsenii.my_choice.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var auth: FirebaseAuth

    private lateinit var go_register: TextView;
    private lateinit var loading: ProgressBar
    private lateinit var email_field: EditText
    private lateinit var password_field: EditText
    private lateinit var sign_in_btn: Button
    private lateinit var forgot_password: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = Firebase.auth

        go_register = findViewById(R.id.go_register)
        go_register.setOnClickListener(this)

        forgot_password = findViewById(R.id.forgot_password)
        forgot_password.setOnClickListener(this)

        sign_in_btn = findViewById(R.id.sign_in_btn)
        sign_in_btn.setOnClickListener(this)

        email_field = findViewById(R.id.sign_in_email)
        password_field = findViewById(R.id.sign_in_password)

        loading = findViewById(R.id.sign_in_loading)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.go_register -> startActivity(Intent(this, RegistrationActivity::class.java))
            R.id.sign_in_btn -> login_user()
            R.id.forgot_password -> startActivity(Intent(this, UpdatePassword::class.java))
        }
    }

    private fun login_user() {
        var email = email_field.text.toString()
        var password = password_field.text.toString()

        if (email.isEmpty()) {
            email_field.error = "Введите логин"
            email_field.requestFocus()
            return
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            email_field.error = "Введите корректный адрес электронной почты"
            email_field.requestFocus()
            return

        } else if (password.isEmpty()) {
            password_field.error = "Введите пароль"
            password_field.requestFocus()
            return

        }

        loading.visibility = View.VISIBLE

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener{ task ->

            if (task.isSuccessful) {
                loading.visibility = View.GONE

                Toast.makeText(this, "Вы успешно вошли в систему", Toast.LENGTH_LONG).show()

                startActivity(Intent(this, MainActivity::class.java))

            } else {
                Toast.makeText(this, "Не удалось войти в аккаунт. Проверте интернет соединение и правильность данных", Toast.LENGTH_LONG).show()
                loading.visibility = View.GONE

            }
        }
    }
}