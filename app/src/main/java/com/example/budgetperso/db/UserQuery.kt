package com.example.budgetperso.db

import android.content.ContentValues
import android.content.Context
import com.example.budgetperso.db.BudgetDatabase.Companion.COLUMN_EMAIL
import com.example.budgetperso.db.BudgetDatabase.Companion.TABLE_USERS
import com.example.budgetperso.db.User
class UserQuery(context: Context) {

    private val dbHelper: BudgetDatabase = BudgetDatabase(context)

    // Ajouter un User dans la DB
    fun addUser(context: Context, user: User) {
        val dbHelper = BudgetDatabase(context)
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("username", user.username)
            put("email", user.email)
            put("password", user.password)
            // Ajoutez d'autres colonnes et valeurs si nécessaire
        }
        val newRowId = db.insert("users", null, values)
        db.close()
    }

    // Met à jour les info du User
    fun updateUser(context: Context, userId: Int, updatedUser: User) {
        val dbHelper = BudgetDatabase(context)
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("username", updatedUser.username)
            put("email", updatedUser.email)
            put("password", updatedUser.password)
        }
        val selection = "id = ?"
        val selectionArgs = arrayOf(userId.toString())
        val count = db.update("users", values, selection, selectionArgs)
        db.close()
    }

    // suppression de compte
    fun deleteUser(context: Context, userId: Int) {
        val dbHelper = BudgetDatabase(context)
        val db = dbHelper.writableDatabase
        val selection = "id = ?"
        val selectionArgs = arrayOf(userId.toString())
        val deletedRows = db.delete("users", selection, selectionArgs)
        db.close()
    }
    // vérifie si un User existe
    fun isUserRegistered(context: Context, email: String): Boolean {
        val dbHelper = BudgetDatabase(context)
        val db = dbHelper.readableDatabase
        val projection = arrayOf("id")
        val selection = "$COLUMN_EMAIL = ?"
        val selectionArgs = arrayOf(email)
        val cursor = db.query(TABLE_USERS, projection, selection, selectionArgs, null, null, null)
        val isRegistered = cursor.count > 0
        cursor.close()
        db.close()
        return isRegistered
    }

    // récupère les info de l'utilisateur
    fun getUserByEmailAndPassword(context: Context, email: String, password: String): User? {
        val dbHelper = BudgetDatabase(context)
        val db = dbHelper.readableDatabase
        val projection = arrayOf("id", "username", "email") // Sélectionnez les colonnes que vous souhaitez récupérer
        val selection = "email = ? AND password = ?"
        val selectionArgs = arrayOf(email, password)
        val cursor = db.query("users", projection, selection, selectionArgs, null, null, null)
        var user: User? = null
        if (cursor.moveToFirst()) {
            val userId = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
            val username = cursor.getString(cursor.getColumnIndexOrThrow("username"))
            val emailFromDb = cursor.getString(cursor.getColumnIndexOrThrow("email"))
            user = User(userId, username, emailFromDb, password)

        }
        cursor.close()
        db.close()
        return user
    }

    // vérification des information avant connexion
    fun verifyCredentials(context: Context, emailForm: String, passwordForm: String): Boolean {
        val dbHelper = BudgetDatabase(context)
        val db = dbHelper.readableDatabase
        val projection = arrayOf("id")
        val selection = "${BudgetDatabase.COLUMN_EMAIL} = ? AND ${BudgetDatabase.COLUMN_PASSWORD} = ?"
        val selectionArgs = arrayOf(emailForm, passwordForm)
        val cursor = db.query(BudgetDatabase.TABLE_USERS, projection, selection, selectionArgs, null, null, null)

        val isValid = cursor.count > 0
        cursor.close()
        db.close()
        return isValid
    }


}