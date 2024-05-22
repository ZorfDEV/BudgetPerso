package com.example.budgetperso.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class BudgetDatabase (context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "budget_manager.db"
        private const val DATABASE_VERSION = 2
        // table Users
         const val TABLE_USERS = "users"
         const val User_ID = "id"
         const val COLUMN_USERNAME = "username"
         const val COLUMN_EMAIL = "email"
         const val COLUMN_PASSWORD = "password"


        // Constantes pour le nom de la table et les noms de colonnes de la table des comptes
        const val TABLE_ACCOUNTS = "accounts"
        const val COLUMN_ACCOUNT_ID_ACCOUNTS = "id" // Renommé pour éviter les conflits de noms
        const val COLUMN_ACCOUNT_NAME = "name"
        const val COLUMN_ACCOUNT_ICON ="icone"
        const val COLUMN_ACCOUNT_BALANCE = "balance"
        const val COLUMN_DATE_ACCOUNT = "date"
        const val COLUMN_USER_ID = "user_id"

        // Constantes pour le nom de la table et les noms de colonnes de la table des transaction
        const val TABLE_TRANSACTIONS = "transactions"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_AMOUNT = "amount"
        const val COLUMN_DATE = "date"
        const val COLUMN_ACCOUNT_ID = "account_id"

    }

    override fun onCreate(db: SQLiteDatabase) {
        // Création de la table pour les utilisateurs
        val createUsersTable = ("CREATE TABLE $TABLE_USERS (" +
                "$User_ID INTEGER PRIMARY KEY," +
                "$COLUMN_USERNAME TEXT," +
                "$COLUMN_EMAIL TEXT," +
                "$COLUMN_PASSWORD TEXT)")
        db.execSQL(createUsersTable)


        val createAccountsTable = ("CREATE TABLE $TABLE_ACCOUNTS (" +
                "$COLUMN_ACCOUNT_ID_ACCOUNTS INTEGER PRIMARY KEY," +
                "$COLUMN_ACCOUNT_NAME TEXT," +
                "$COLUMN_ACCOUNT_ICON TEXT," +
                "$COLUMN_ACCOUNT_BALANCE REAL," +
                "$COLUMN_USER_ID INTEGER," +
                "$COLUMN_DATE_ACCOUNT DATETIME," +
                "FOREIGN KEY($COLUMN_USER_ID) REFERENCES $TABLE_USERS($COLUMN_USER_ID) " +
                "ON DELETE CASCADE)")
        db.execSQL(createAccountsTable)

        val createExpensesTable = ("CREATE TABLE $TABLE_TRANSACTIONS (" +
                "$COLUMN_ID INTEGER PRIMARY KEY," +
                "$COLUMN_NAME TEXT," +
                "$COLUMN_DESCRIPTION TEXT," +
                "$COLUMN_AMOUNT REAL," +
                "$COLUMN_DATE DATETIME," +
                "$COLUMN_ACCOUNT_ID INTEGER," +
                "FOREIGN KEY($COLUMN_ACCOUNT_ID) REFERENCES $TABLE_ACCOUNTS($COLUMN_ACCOUNT_ID_ACCOUNTS) " +
                "ON DELETE CASCADE)")
        db.execSQL(createExpensesTable)

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_TRANSACTIONS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_ACCOUNTS")
        onCreate(db)
    }



}
