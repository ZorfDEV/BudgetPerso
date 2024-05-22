package com.example.budgetperso

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.budgetperso.db.BudgetDatabase
import com.example.budgetperso.db.User



class ForgotPasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        val emailEditText = findViewById<EditText>(R.id.emailEditText)
        val submitButton = findViewById<Button>(R.id.submitButton)

        submitButton.setOnClickListener {
            val emailForm = emailEditText.text.toString()

            // Vérifier si l'e-mail existe dans la base de données
            val user = getUserByEmail(this,emailForm)
            if (user != null) {
                val intent = Intent(this, ResetPasswordActivity::class.java)
                intent.putExtra("email", emailForm)
                startActivity(intent)
            } else {
                Toast.makeText(this, "L'e-mail saisi n'existe pas.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getUserByEmail(context:Context, email: String): User? {
        val dbHelper = BudgetDatabase(context)
        val db = dbHelper.readableDatabase
        val projection = arrayOf(
            BudgetDatabase.User_ID,
            BudgetDatabase.COLUMN_USERNAME,
            BudgetDatabase.COLUMN_EMAIL,
            BudgetDatabase.COLUMN_PASSWORD
        )
        val selection = "${BudgetDatabase.COLUMN_EMAIL} = ?"
        val selectionArgs = arrayOf(email)
        val cursor = db.query(
            BudgetDatabase.TABLE_USERS,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            null
        )
        var user: User? = null
        if (cursor.moveToFirst()) {
            val userId = cursor.getInt(cursor.getColumnIndexOrThrow(BudgetDatabase.User_ID))
            val username = cursor.getString(cursor.getColumnIndexOrThrow(BudgetDatabase.COLUMN_USERNAME))
            val userEmail = cursor.getString(cursor.getColumnIndexOrThrow(BudgetDatabase.COLUMN_EMAIL))
            val password = cursor.getString(cursor.getColumnIndexOrThrow(BudgetDatabase.COLUMN_PASSWORD))
            user = User(userId, username, userEmail, password)
        }
        cursor.close()
        db.close()
        return user
    }

}
