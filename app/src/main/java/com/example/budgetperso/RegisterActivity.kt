package com.example.budgetperso

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.budgetperso.db.User
import com.example.budgetperso.db.UserQuery

class RegisterActivity : AppCompatActivity() {
    private lateinit var usernameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var registerButton: Button
    private lateinit var loginLinkTextView: TextView
    private lateinit var userQuery: UserQuery

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        usernameEditText = findViewById(R.id.usernameEditText)
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText)
        registerButton = findViewById(R.id.registerButton)
        loginLinkTextView = findViewById(R.id.loginLinkTextView)
        userQuery = UserQuery(this)

        loginLinkTextView.setOnClickListener {
            // Redirection vers LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        registerButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()
            val email = emailEditText.text.toString()
            val confirmPassword = confirmPasswordEditText.text.toString()

            if (password == confirmPassword) {

             val   isUserRegistered = userQuery.isUserRegistered(this,email)
                if (isUserRegistered){
                    // Afficher un message
                    Toast.makeText(this@RegisterActivity, "Cette utilisateur existe déjà !", Toast.LENGTH_SHORT).show()
                    // Rediriger vers l'activité de connexion
                    val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                else {
                    // Enregistrer les identifiants
                    saveCredentials(username, email, password)
                }
            } else {
                // Afficher un message d'erreur si les mots de passe ne correspondent pas
                Toast.makeText(this@RegisterActivity, "Les mots de passe ne correspondent pas", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveCredentials(username: String, email: String, password: String) {
        // Ajouter l'utilisateur à la base de données
        val newUser = User(0, username, email, password)

        userQuery.addUser(this,newUser)

        // Afficher un message de succès
        Toast.makeText(this@RegisterActivity, "Inscription réussie !", Toast.LENGTH_SHORT).show()

        // Rediriger vers l'activité de connexion
        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}
