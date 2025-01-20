package com.toucheese.app.data.model.sign_up

data class SignUpData(
    val email: String,
    val password: String,
    val name: String,
    val phone: String
){
    override fun toString(): String {
        return "\nemail: ${email},\n" +
                "password: $password,\n" +
                "name: $name\n" +
                "phone: $phone"
    }
}
