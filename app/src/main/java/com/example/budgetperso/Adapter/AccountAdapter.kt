package com.example.budgetperso.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.budgetperso.databinding.ItemAccountBinding
import com.example.budgetperso.db.Account

class AccountAdapter(
    private val accounts: MutableList<Account>,
    private val onEdit: (Account) -> Unit,
    private val onDelete: (Account) -> Unit
) : RecyclerView.Adapter<AccountAdapter.AccountViewHolder>() {

    inner class AccountViewHolder(private val binding: ItemAccountBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(account: Account) {
            binding.accountName.text = account.name
            binding.accountBalance.text = "${account.balance} FCFA"
            binding.accountDate.text = account.date.toString()
            val iconResId = binding.root.context.resources.getIdentifier(
                account.icone, "drawable", binding.root.context.packageName
            )
            binding.accountIcon.setImageResource(iconResId)

            // Set click listeners for edit and delete buttons
            binding.btnEdit.setOnClickListener {
                onEdit(account)
            }
            binding.btnDelete.setOnClickListener {
                onDelete(account)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountViewHolder {
        val binding = ItemAccountBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return AccountViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AccountViewHolder, position: Int) {
        holder.bind(accounts[position])
    }

    override fun getItemCount(): Int {
        return accounts.size
    }

    fun updateAccounts(newAccounts: List<Account>) {
        accounts.clear()
        accounts.addAll(newAccounts)
        notifyDataSetChanged()
    }
}
