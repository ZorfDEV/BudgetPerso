package com.example.budgetperso.db

import android.content.ContentValues
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.budgetperso.AddAccountDialog
import com.example.budgetperso.db.BudgetDatabase.Companion.COLUMN_ACCOUNT_BALANCE
import com.example.budgetperso.db.BudgetDatabase.Companion.COLUMN_ACCOUNT_ICON
import com.example.budgetperso.db.BudgetDatabase.Companion.COLUMN_ACCOUNT_ID_ACCOUNTS
import com.example.budgetperso.db.BudgetDatabase.Companion.COLUMN_ACCOUNT_NAME
import com.example.budgetperso.db.BudgetDatabase.Companion.COLUMN_USER_ID
import com.example.budgetperso.db.BudgetDatabase.Companion.TABLE_ACCOUNTS
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class AccountQuery(context: Context) {


        private val dbHelper: BudgetDatabase = BudgetDatabase(context)

        // Fonction pour ajouter un compte
        fun addAccount(account: Account) {
            val db = dbHelper.writableDatabase
            val values = ContentValues().apply {
                put(COLUMN_ACCOUNT_NAME, account.name)
                put(COLUMN_ACCOUNT_ICON, account.icone)
                put(COLUMN_ACCOUNT_BALANCE, account.balance)
                put(COLUMN_USER_ID, account.user_Id)
            }
            db.insert(TABLE_ACCOUNTS, null, values)
            db.close()
        }

        // Fonction pour modifier un compte
        fun updateAccount(account: Account) {
            val db = dbHelper.writableDatabase
            val values = ContentValues().apply {
                put(COLUMN_ACCOUNT_NAME, account.name)
                put(COLUMN_ACCOUNT_ICON, account.icone)
                put(COLUMN_ACCOUNT_BALANCE, account.balance)
            }
            db.update(TABLE_ACCOUNTS, values, "$COLUMN_ACCOUNT_ID_ACCOUNTS=?", arrayOf(account.id.toString()))
            db.close()
        }

        // Fonction pour supprimer un compte
        fun deleteAccount(accountId: Int) {
            val db = dbHelper.writableDatabase
            db.delete(TABLE_ACCOUNTS, "$COLUMN_ACCOUNT_ID_ACCOUNTS=?", arrayOf(accountId.toString()))
            db.close()
        }

    // Fonction pour lister les comptes de l'utilisateur connecté
    @RequiresApi(Build.VERSION_CODES.O)
    fun getUserAccounts(user_Id: Int): List<Account> {
        val accountsList = mutableListOf<Account>()
        val db = dbHelper.readableDatabase
        val selection = "$COLUMN_USER_ID = ?"
        val selectionArgs = arrayOf(user_Id.toString())
        val cursor = db.query(TABLE_ACCOUNTS, null, selection, selectionArgs, null, null, null)
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ACCOUNT_ID_ACCOUNTS))
            val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ACCOUNT_NAME))
            val icone = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ACCOUNT_ICON))
            val balance = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_ACCOUNT_BALANCE))
            val dateString = cursor.getString(cursor.getColumnIndexOrThrow(BudgetDatabase.COLUMN_DATE_ACCOUNT))
            // Définir le format de date attendu (jour/mois/année)
            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.getDefault())
// Analyser la chaîne de caractères en un objet LocalDateTime
            val date = LocalDateTime.parse(dateString, formatter)

            val account = Account(id, name, icone, balance,date,user_Id)
            accountsList.add(account)
        }
        cursor.close()
        db.close()
        return accountsList
    }


}

