package com.arsenii.my_choice_kids

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.arsenii.my_choice_kids.ui.AllFragment
import com.arsenii.my_choice_kids.ui.InterestsFragment
import com.arsenii.my_choice_kids.ui.ProfileFragment
import com.arsenii.my_choice_kids.login_and_registration.LoginActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private val auth = Firebase.auth

    private val interestsFragment = InterestsFragment()
    private val allFragment = AllFragment()
    private val profileFragment = ProfileFragment()

    private lateinit var bottom_nav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (auth.currentUser == null) {
            Toast.makeText(this, "Войдите в аккаунт или зарегистрируйтесь.", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        } else if (!auth.currentUser!!.isEmailVerified) {
            auth.currentUser!!.sendEmailVerification()
        }

        replace_fragment(interestsFragment)

        bottom_nav = findViewById(R.id.bottom_nav)
        bottom_nav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_interests -> replace_fragment(interestsFragment)
                R.id.navigation_all -> replace_fragment(allFragment)
                R.id.navigation_profile -> replace_fragment(profileFragment)
            }
            true
        }
    }

    private fun replace_fragment(fr: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fr)
        transaction.commit()
    }
}