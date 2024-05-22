package com.example.budgetperso
import android.app.Dialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.example.budgetperso.db.Account
import com.example.budgetperso.db.AccountQuery
import java.time.LocalDateTime

class AddAccountDialog : DialogFragment() {

    private lateinit var selectedIcon: String
    private lateinit var accountQuery: AccountQuery

    private var onDismissListener: OnDismissListener? = null

    interface OnDismissListener {
        fun onDismiss()
    }

    fun setOnDismissListener(listener: OnDismissListener) {
        onDismissListener = listener
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_add_account, container, false)

        val buttonSelectIcon: Button = view.findViewById(R.id.buttonSelectIcon)
        buttonSelectIcon.setOnClickListener {
            showIconPicker()
        }

        accountQuery = AccountQuery(requireContext())

        val userId = getUserId(requireContext())

        val buttonAdd: Button = view.findViewById(R.id.buttonAdd)
        buttonAdd.setOnClickListener {
            val editTextAccountName: EditText = view.findViewById(R.id.editTextAccountName)
            val editTextAccountBalance: EditText = view.findViewById(R.id.editTextAccountBalance)

            val accountName = editTextAccountName.text.toString()
            val accountBalance = editTextAccountBalance.text.toString().toDoubleOrNull()

            if (accountName.isNotBlank() && accountBalance != null) {
                val account = Account(
                    id = 0, // Valeur par défaut pour un nouvel enregistrement, l'ID sera généré automatiquement dans la base de données
                    name = accountName,
                    icone = selectedIcon,
                    balance = accountBalance,
                    date = LocalDateTime.now(), // Date actuelle
                    user_Id = userId
                )

                accountQuery.addAccount(account)
                dismiss() // Fermer le dialogue après l'ajout
            } else {
                Toast.makeText(requireContext(), "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show()
            }
        }


        val buttonCancel: Button = view.findViewById(R.id.buttonCancel)
        buttonCancel.setOnClickListener {
            dismiss()
        }

        return view
    }
    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }
    private fun showIconPicker() {
        val iconPickerDialog = Dialog(requireContext())
        iconPickerDialog.setContentView(R.layout.dialog_icon_picker)

        val iconGridView: GridView = iconPickerDialog.findViewById(R.id.iconGridView)
        val icons = arrayOf(
            R.drawable.ic_baseline_credit_card_24, R.drawable.ic_sack_dollar, R.drawable.cash_multiple, // Add your icons here
        )
        val adapter = IconAdapter(requireContext(), icons)
        iconGridView.adapter = adapter

        iconGridView.setOnItemClickListener { _, _, position, _ ->
            selectedIcon = icons[position].toString() // Store the selected icon
            iconPickerDialog.dismiss()
        }

        iconPickerDialog.show()
    }

    // Adapter for displaying icons in the GridView
    private class IconAdapter(
        context: Context,
        private val icons: Array<Int>
    ) : ArrayAdapter<Int>(context, android.R.layout.simple_list_item_1, icons) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val imageView = convertView as? ImageView ?: ImageView(context)
            imageView.layoutParams = AbsListView.LayoutParams(100, 100) // Adjust size as needed
            imageView.setImageResource(icons[position])
            return imageView
        }
    }

    fun getUserId(context: Context): Int {
        val sharedPreferences = context.getSharedPreferences("user_session",
            AppCompatActivity.MODE_PRIVATE
        )
        return sharedPreferences.getInt("userId", -1)
    }
}
