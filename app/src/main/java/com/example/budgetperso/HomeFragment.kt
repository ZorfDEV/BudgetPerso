package com.example.budgetperso
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton


class HomeFragment : Fragment() {
    private var rotate = false
    private lateinit var gestCptButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Utilisez l'inflater pour charger le layout XML du fragment
        // Le troisième paramètre "false" indique de ne pas attacher la vue au conteneur (container) immédiatement
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Trouver les vues à l'intérieur de la vue racine du fragment
        val fabAdd: FloatingActionButton = view.findViewById(R.id.fabAdd)
        val fabTransfert: FloatingActionButton = view.findViewById(R.id.fabTransfere)
        val fabRevenus: FloatingActionButton = view.findViewById(R.id.fabRevenus)
        val fabAchat: FloatingActionButton = view.findViewById(R.id.fabAchat)
        gestCptButton = view.findViewById(R.id.gestCptButton)
        // Vous pouvez effectuer des opérations supplémentaires ici, si nécessaire

        gestCptButton.setOnClickListener {
            val intent = Intent(activity, AccountActivity::class.java)
            startActivity(intent)
        }

        initShowOut(fabTransfert)
        initShowOut(fabRevenus)
        initShowOut(fabAchat)

        fabAdd.setOnClickListener {
            rotate = rotateFab(it, !rotate)
            if (rotate) {
                showIn(fabTransfert)
                showIn(fabRevenus)
                showIn(fabAchat)
            } else {
                initShowOut(fabTransfert)
                initShowOut(fabRevenus)
                initShowOut(fabAchat)
            }
        }

        fabTransfert.setOnClickListener {
            // Créer une intention (Intent) pour démarrer l'activité TransfertActivity
            //val intent = Intent(activity, TransfertActivity::class.java)

            // Démarrer l'activité TransfertActivity
            //startActivity(intent)
        }


        fabAchat.setOnClickListener {
           // val intent = Intent(activity, AchatActivity::class.java)

            // Démarrer l'activité TransfertActivity
           // startActivity(intent)
        }
        fabRevenus.setOnClickListener {
           // val intent = Intent(activity, RevenusActivity::class.java)
            // Démarrer l'activité TransfertActivity
           // startActivity(intent)
        }

        return view
    }

}

private fun initShowOut(v: View) {
    v.visibility = View.GONE
    v.translationY = v.height.toFloat()
    v.alpha = 0f
}

private fun rotateFab(v: View, rotate: Boolean): Boolean {
    v.animate().setDuration(200)
        .setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
            }
        })
        .rotation(if (rotate) 135f else 0f)
    return rotate
}

private fun showIn(v: View) {
    v.visibility = View.VISIBLE
    v.alpha = 0f
    v.translationY = v.height.toFloat()
    v.animate()
        .setDuration(200)
        .translationY(0f)
        .setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
            }
        })
        .alpha(1f)
        .start()
}
