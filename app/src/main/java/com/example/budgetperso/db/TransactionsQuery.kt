package com.example.budgetperso.db
import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.provider.DocumentsContract.Document.COLUMN_ICON
import android.view.SurfaceControl.Transaction
import androidx.annotation.RequiresApi
import com.example.budgetperso.db.BudgetDatabase.Companion.COLUMN_ACCOUNT_ID
import com.example.budgetperso.db.BudgetDatabase.Companion.COLUMN_AMOUNT
import com.example.budgetperso.db.BudgetDatabase.Companion.COLUMN_DATE
import com.example.budgetperso.db.BudgetDatabase.Companion.COLUMN_DESCRIPTION
import com.example.budgetperso.db.BudgetDatabase.Companion.COLUMN_ID
import com.example.budgetperso.db.BudgetDatabase.Companion.COLUMN_NAME
import com.example.budgetperso.db.BudgetDatabase.Companion.TABLE_TRANSACTIONS
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class TransactionsQuery(context: Context) {

    private val dbHelper: BudgetDatabase = BudgetDatabase(context)

    // Fonction pour ajouter une dépense
    fun addTransactions(context: Context, transaction: Transactions) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, transaction.name)
            put(COLUMN_DESCRIPTION, transaction.description)
            put(COLUMN_AMOUNT, transaction.amount)
            put(COLUMN_DATE, "datetime('now')")
            put(COLUMN_ACCOUNT_ID, transaction.account_id)
        }
        db.insert(TABLE_TRANSACTIONS, null, values)
        db.close()
    }

    // Fonction pour modifier une dépense
    fun updatetransaction(transaction: Transactions) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, transaction.name)
            put(COLUMN_DESCRIPTION, transaction.description)
            put(COLUMN_AMOUNT, transaction.amount)
            put(COLUMN_DATE, "datetime('now')")
            put(COLUMN_ACCOUNT_ID, transaction.account_id)
        }
        db.update(TABLE_TRANSACTIONS, values, "$COLUMN_ID=?", arrayOf(transaction.id.toString()))
        db.close()
    }

    // Fonction pour supprimer une dépense
    fun deletetransaction(transactionId: Int) {
        val db = dbHelper.writableDatabase
        db.delete(TABLE_TRANSACTIONS, "$COLUMN_ID=?", arrayOf(transactionId.toString()))
        db.close()
    }

    // Fonction pour récupérer toutes les dépenses triées par date
    @RequiresApi(Build.VERSION_CODES.O)
    fun getAlltransactionsSortedByDate(): MutableList<Transactions> {
        val transactionsList = mutableListOf<Transactions>()
        val db = dbHelper.readableDatabase
        val cursor = db.query(TABLE_TRANSACTIONS, null, null, null, null, null, "$COLUMN_DATE ASC")
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
            val description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION))
            val amount = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_AMOUNT))
            val dateString = cursor.getString(cursor.getColumnIndexOrThrow(BudgetDatabase.COLUMN_DATE))
            // Définir le format de date attendu (jour/mois/année)
            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.getDefault())
// Analyser la chaîne de caractères en un objet LocalDateTime
            val date = LocalDateTime.parse(dateString, formatter)
            val account_id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ACCOUNT_ID))
            val transaction = Transactions(id,name, description, amount, date, account_id)
            transactionsList.add(transaction)
        }

        cursor.close()
        db.close()
        return transactionsList
    }
}




