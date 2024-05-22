package com.example.budgetperso

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.budgetperso.db.UserQuery



class LoginActivity : AppCompatActivity() {
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var regiterLinkTextView : TextView
    private lateinit var forgotPasswordLink :TextView
    private lateinit var userQuery: UserQuery

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login2)

        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        loginButton = findViewById(R.id.loginButton)
        regiterLinkTextView = findViewById(R.id.regiterLinkTextView)
        userQuery = UserQuery(this)
        forgotPasswordLink = findViewById(R.id.forgotPasswordLink)

        regiterLinkTextView.setOnClickListener {
            // Redirection vers LoginActivity
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        forgotPasswordLink.setOnClickListener {
            // Redirection vers ForgotPassword
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }
        // start login
        loginButton.setOnClickListener {
            val emailForm = emailEditText.text.toString()
            val passwordForm = passwordEditText.text.toString()

            // Validation des identifiants

            val isValidCredentials = userQuery.verifyCredentials(this, emailForm, passwordForm)
            if (isValidCredentials) {
                // Authentification réussie, récupérer l'ID et le nom d'utilisateur de l'utilisateur
                val user = userQuery.getUserByEmailAndPassword(this,emailForm,passwordForm )
                if (user != null) {
                    val userId = user.id
                    val username = user.username

                    // Enregistrer les informations de session de l'utilisateur
                    saveUserSession(this, userId, username)

                    // Rediriger vers l'activité principale
                    val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this@LoginActivity, "Erreur lors de la récupération des informations de l'utilisateur", Toast.LENGTH_SHORT).show()
                }
            }
            else {
                // Afficher un message d'erreur pour les identifiants incorrects
                Toast.makeText(this@LoginActivity, "Identifiants incorrects", Toast.LENGTH_SHORT).show()
            }
        }
    }
    fun saveUserSession(context: Context, userId: Int, username: String) {
        val sharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("userId", userId)
        editor.putString("username", username)
        editor.apply()
    }

}
