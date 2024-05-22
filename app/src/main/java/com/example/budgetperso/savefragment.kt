/*package com.example.budgetperso

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.budgetperso.Adapter.FirstItemAdapter
import com.example.budgetperso.Adapter.SecondItemAdapter
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate


class HomeFragment : Fragment(), FirstItemAdapter.ItemClickListener {

    private lateinit var firstitemRecyclerView: RecyclerView
    private lateinit var firstitemAdapter: FirstItemAdapter

    private lateinit var pieChart: PieChart

    private lateinit var seconditemRecyclerView: RecyclerView
    private lateinit var seconditemAdapter: SecondItemAdapter

    private lateinit var textView: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val firstLayoutManager = GridLayoutManager(context, 4)
        val secondLayoutManager = GridLayoutManager(context, 4)

        // Item1 RecyclerView
        firstitemRecyclerView = view.findViewById(R.id.firstitemrecyclerview)
        firstitemRecyclerView.setHasFixedSize(true)
        firstitemRecyclerView.layoutManager = firstLayoutManager

        val itemList = ArrayList<FirstItemAdapter.Item>()
        itemList.add(FirstItemAdapter.Item(1, "Loyer", R.drawable.ic_home_work_28, "0 FCFA"))
        itemList.add(FirstItemAdapter.Item(2, "Santé", R.drawable.heart_pulse, "0 FCFA"))
        itemList.add(FirstItemAdapter.Item(3, "Etudes", R.drawable.ic_school_28, "0 FCFA"))
        itemList.add(FirstItemAdapter.Item(4, "Shopping", R.drawable.ic_shopping_cart_28, "0 FCFA"))

        firstitemAdapter = FirstItemAdapter(itemList)
        firstitemAdapter.setItemClickListener(this)
        firstitemRecyclerView.adapter = firstitemAdapter

        textView = view.findViewById(R.id.namedr1)
        //Chart RecyclerView

        pieChart = view.findViewById(R.id.chartpie)
        displayPieChart()

        // Item2 RecyclerView

        seconditemRecyclerView = view.findViewById(R.id.seconditemrecyclerview)
        seconditemRecyclerView.setHasFixedSize(true)
        seconditemRecyclerView.layoutManager = secondLayoutManager

        val itemList2 = ArrayList<SecondItemAdapter.Item>()
        itemList2.add(SecondItemAdapter.Item(5, "Loyer", R.drawable.ic_home_work_28, "0 FCFA"))
        itemList2.add(SecondItemAdapter.Item(6, "Santé", R.drawable.heart_pulse, "0 FCFA"))
        itemList2.add(SecondItemAdapter.Item(7, "Etudes", R.drawable.ic_school_28, "0 FCFA"))
        itemList2.add(SecondItemAdapter.Item(8, "Shopping", R.drawable.ic_shopping_cart_28, "0 FCFA"))

        seconditemAdapter = SecondItemAdapter(itemList2)
        seconditemRecyclerView.adapter = seconditemAdapter

        val button3: Button = view.findViewById(R.id.Revenus)
        button3.setOnClickListener {
            showInputDialog()
        }

        return view
    }

    private fun displayPieChart() {
        // Créer une liste d'entrées (PieEntry) avec les données spécifiées
        val entries = listOf(
            PieEntry(25f, ""),
            PieEntry(30f, ""),
            PieEntry(15f, ""),
            PieEntry(35f, ""),
            PieEntry(10f, ""),
            PieEntry(20f, ""),
            PieEntry(45f, ""),
            PieEntry(50f, ""),
            PieEntry(40f, ""),
            PieEntry(55f, ""),
            PieEntry(60f, "")
        )
        // Créer un ensemble de données (PieDataSet) avec les entrées
        val dataSet = PieDataSet(entries, "")
        dataSet.colors = ColorTemplate.COLORFUL_COLORS.asList()
        // Diminuer l'épaisseur du graphique
        pieChart.holeRadius = 70f // Modifier la valeur pour ajuster l'épaisseur
        pieChart.transparentCircleRadius = 55f // Modifier la valeur pour ajuster l'épaisseur de la bordure
        pieChart.description.isEnabled = false

        // Mettre un texte au centre du graphique
        pieChart.setDrawCenterText(true)
        pieChart.centerText = "Texte au centre" // Modifier le texte selon vos besoins
        pieChart.setCenterTextColor(Color.BLACK) // Modifier la couleur du texte selon vos besoins

        // Supprimer la légende du graphique
        pieChart.legend.isEnabled = false

        // Créer un objet PieData avec le PieDataSet
        val pieData = PieData(dataSet)

        // Appliquer le PieData au PieChart
        pieChart.data = pieData
        pieChart.invalidate() // Rafraîchir le graphique
    }

    @SuppressLint("MissingInflatedId")
    private fun showInputDialog() {
        val dialogView = LayoutInflater.from(activity).inflate(R.layout.dialog_input, null)
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
            .setView(dialogView)
            .setTitle("Saisir des données")

        //val input = dialogView.findViewById<EditText>(R.id.inputmontant)
        //val imageView = dialogView.findViewById<ImageView>(R.id.dialogImage)
        val buttonValidate = dialogView.findViewById<Button>(R.id.btnValidate)
        val buttonCancel = dialogView.findViewById<Button>(R.id.btnCancel)

        // Ajouter du code pour personnaliser l'image et les boutons si nécessaire

        val dialog = builder.create()

        buttonValidate.setOnClickListener {
            val enteredData = input.text.toString()
            // Faites quelque chose avec les données saisies (par exemple, les afficher dans un TextView)
            textView.text = enteredData
            dialog.dismiss()
        }

        buttonCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }


    override fun onItemClick(item: FirstItemAdapter.Item) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Élément cliqué")
        builder.setMessage("Vous avez cliqué sur l'élément : ${item.name}")

        builder.setPositiveButton("OK") { _, _ ->
            // Action à effectuer lorsque l'utilisateur clique sur OK dans le dialogue
        }

        builder.show()
    }
}*/