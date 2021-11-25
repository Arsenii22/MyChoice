package com.arsenii.my_choice.login_and_registration

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.*
import com.arsenii.my_choice.MainActivity
import com.arsenii.my_choice.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class RegistrationActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var auth: FirebaseAuth

    private lateinit var go_login: TextView;
    private lateinit var loading: ProgressBar
    private lateinit var name_field: EditText
    private lateinit var email_field: EditText
    private lateinit var password_field: EditText
    private lateinit var register_btn: Button
    private lateinit var city_field: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        auth = Firebase.auth

        go_login = findViewById(R.id.go_login)
        go_login.setOnClickListener(this)

        register_btn = findViewById(R.id.register_btn)
        register_btn.setOnClickListener(this)

        name_field = findViewById(R.id.register_name)
        email_field = findViewById(R.id.register_email)
        password_field = findViewById(R.id.register_password)
        city_field = findViewById(R.id.register_city)

        loading = findViewById(R.id.register_loading)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.go_login -> finish()
            R.id.register_btn -> register_user()
        }
    }

    private fun register_user() {
        var name: String = name_field.text.toString()
        var email: String = email_field.text.toString()
        var password: String = password_field.text.toString()
        var city: String = city_field.selectedItem.toString()

        if(name.isEmpty()) {
            name_field.error = "Введите имя"
            name_field.requestFocus()
            return
        } else if (email.isEmpty()) {
            email_field.error = "Введите электронную почту"
            email_field.requestFocus()
            return
        } else if (password.isEmpty()) {
            password_field.error = "Введите пароль"
            password_field.requestFocus()
            return
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            email_field.error = "Введите корректный адрес электронной почты"
            email_field.requestFocus()
            return
        } else if (password.length < 6) {
            password_field.error = "Слишком короткий пароль"
            password_field.requestFocus()
            return
        }



        loading.visibility = View.VISIBLE

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{ task ->
                    if(task.isSuccessful) {
                        var user = User(name, email, city)

                        Firebase.database.getReference("Users")
                            .child(auth.currentUser!!.uid)
                            .setValue(user).addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Toast.makeText(this, "Регистрация прошла успешно", Toast.LENGTH_LONG).show()

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

                                } else {
                                    Toast.makeText(this, "Ошибка регистрации. Попробуйте ещё раз", Toast.LENGTH_LONG).show()
                                    loading.visibility = View.GONE
                                }
                            }

                    } else {
                        Toast.makeText(this, "Ошибка регистрации, возможно такой пользователь уже существует", Toast.LENGTH_LONG).show()
                        loading.visibility = View.GONE

                    }
                }
            }



}