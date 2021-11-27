package com.arsenii.my_choice.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.arsenii.my_choice.R
import com.arsenii.my_choice.login_and_registration.LoginActivity
import com.arsenii.my_choice.login_and_registration.UpdatePassword
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class ProfileFragment : Fragment() {

    private val auth = Firebase.auth

    private lateinit var viewOfFragment: View
    private lateinit var my_name: TextView
    private lateinit var my_email: TextView
    private lateinit var my_city: TextView
    private lateinit var change_password: Button
    private lateinit var logout: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewOfFragment = inflater.inflate(R.layout.fragment_profile, container, false)

        my_name = viewOfFragment.findViewById(R.id.my_name)
        my_email = viewOfFragment.findViewById(R.id.my_email)
        my_city = viewOfFragment.findViewById(R.id.my_city)

        change_password = viewOfFragment.findViewById(R.id.change_password_profile)
        change_password.setOnClickListener {
            startActivity(Intent(context, UpdatePassword::class.java))
        }

        logout = viewOfFragment.findViewById(R.id.logout)
        logout.setOnClickListener {
            auth.signOut()
            startActivity(Intent(context, LoginActivity::class.java))
            activity?.finish()
        }

        FirebaseDatabase.getInstance().getReference("Users").child(auth.currentUser!!.uid).get().addOnSuccessListener {
            my_name.text = it.child("name").value.toString()
            my_email.text = it.child("email").value.toString()
            my_city.text = it.child("city").value.toString()
        }.addOnFailureListener{
            Toast.makeText(context, it.toString(), Toast.LENGTH_SHORT).show()
        }

        return inflater.inflate(R.layout.fragment_profile, container, false)
    }
}
