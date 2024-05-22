package com.example.budgetperso

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.budgetperso.Adapter.AccountAdapter
import com.example.budgetperso.db.AccountQuery
import com.example.budgetperso.databinding.ActivityAccountBinding
import com.example.budgetperso.db.Account
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class AccountActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAccountBinding
    private lateinit var accountQuery: AccountQuery
    private lateinit var adapter: AccountAdapter

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        accountQuery = AccountQuery(this)

        val userId = getUserId(this)
        val accounts = accountQuery.getUserAccounts(userId)

        adapter = AccountAdapter(
            accounts as MutableList<Account>,
            onEdit = { account ->
                // Gestion de l'action d'édition
                Snackbar.make(binding.root, "Edit ${account.name}", Snackbar.LENGTH_LONG).show()
                // Vous pouvez ouvrir une activité ou une boîte de dialogue pour éditer le compte ici
            },
            onDelete = { account ->
                // Gestion de l'action de suppression
                Snackbar.make(binding.root, "Delete ${account.name}", Snackbar.LENGTH_LONG).show()
                accountQuery.deleteAccount(account.id)
                // Mettre à jour la liste des comptes dans l'adaptateur
                val newAccounts = accountQuery.getUserAccounts(userId)
                adapter.updateAccounts(newAccounts)
            }
        )

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        val fabAddAccount: FloatingActionButton = findViewById(R.id.fabAddcpte)
        fabAddAccount.setOnClickListener {
            val addAccountDialog = AddAccountDialog()
            addAccountDialog.show(supportFragmentManager, "AddAccountDialog")
        }

        binding.backArrow.setOnClickListener {
            onBackPressed()
        }
    }

    private fun getUserId(context: Context): Int {
        val sharedPreferences = context.getSharedPreferences("user_session", MODE_PRIVATE)
        return sharedPreferences.getInt("userId", -1)
    }
}
