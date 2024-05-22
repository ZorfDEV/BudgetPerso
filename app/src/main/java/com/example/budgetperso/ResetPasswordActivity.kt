package com.example.budgetperso

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.budgetperso.db.BudgetDatabase

class ResetPasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        val email = intent.getStringExtra("email")

        val newPasswordEditText = findViewById<EditText>(R.id.newpasswordEditText)
        val submitButton = findViewById<Button>(R.id.resetPassButton)

        // Afficher l'e-mail récupéré sur l'interface utilisateur
        val emailTextView = findViewById<TextView>(R.id.emailTextView)
        emailTextView.text = email

        submitButton.setOnClickListener {
            val newPassword = newPasswordEditText.text.toString()

            // Mettre à jour le mot de passe dans la base de données
            updateUserPassword(this, email.toString(), newPassword)

            Toast.makeText(this, "Mot de passe réinitialisé avec succès.", Toast.LENGTH_SHORT).show()
            // Rediriger vers LoginActivity
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun updateUserPassword(context: Context, email: String, newPassword: String) {
        val dbHelper = BudgetDatabase(context)
        val db = dbHelper.writableDatabase

        // Créer un ContentValues pour mettre à jour le mot de passe
        val values = ContentValues().apply {
            put(BudgetDatabase.COLUMN_PASSWORD, newPassword)
        }

        // Clause WHERE pour sélectionner l'utilisateur par email
        val selection = "${BudgetDatabase.COLUMN_EMAIL} = ?"
        val selectionArgs = arrayOf(email)

        // Mettre à jour le mot de passe dans la base de données
        val rowsUpdated = db.update(BudgetDatabase.TABLE_USERS, values, selection, selectionArgs)

        // Fermer la connexion à la base de données
        db.close()

        // Vérifier si la mise à jour a réussi
        if (rowsUpdated > 0) {
            // La mise à jour a réussi
            Log.d(TAG, "Mot de passe mis à jour pour l'utilisateur avec l'email: $email")
        } else {
            // La mise à jour a échoué
            Log.e(TAG, "Échec de la mise à jour du mot de passe pour l'utilisateur avec l'email: $email")
        }
    }

}
