package com.arsenii.my_choice_kids.login_and_registration

import com.google.firebase.database.Exclude

class User(name: String? = null, email: String? = null, city: String? = null) {
    var name: String
    var email: String
    var city: String

    init {
        this.name = name!!
        this.email = email!!
        this.city = city!!
    }

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "name" to name,
            "email" to email,
            "city" to city
        )
    }
}