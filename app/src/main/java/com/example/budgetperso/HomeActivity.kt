package com.example.budgetperso
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.sagarkoli.chetanbottomnavigation.chetanBottomNavigation


class HomeActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toolbar: Toolbar
    private lateinit var toolbar2: Toolbar
    private lateinit var bottomNavigation: chetanBottomNavigation
    private val myhome:  Int = 1
    private val mydep:   Int = 2
    private val mybud:   Int = 3
    private val mycompt:  Int = 4
    private val mystat:  Int = 5
   // private lateinit var usernamesess: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        toolbar = findViewById(R.id.main_toolbar)
        //toolbar2= findViewById(R.id.toolbar2)
        //setSupportActionBar(toolbar)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view_tag, HomeFragment())
            .commit()
        // Récupérez votre menu
        val menu = toolbar.menu
        // Récupérer l'élément de menu "Compte"
        val navigationView = findViewById<NavigationView>(R.id.navigation_view)
        val headerView = navigationView.getHeaderView(0)
        val usernameTextView = headerView.findViewById<TextView>(R.id.usernameTextView)
        val  username = getUsername(this)
        usernameTextView.text = username
        
// Associer les menus aux Toolbars
       //toolbar.inflateMenu(R.menu.main_menu1)
        //toolbar2.inflateMenu(R.menu.main_menu2)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        drawerLayout = findViewById(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()



// End drawer---------------------------------------

        //toolbar footer
        // textView   = findViewById(R.id.textView)
        bottomNavigation = findViewById(R.id.bottomNavigation)

        bottomNavigation.add(chetanBottomNavigation.Model(myhome,R.drawable.ic_home))
        bottomNavigation.add(chetanBottomNavigation.Model(mybud,R.drawable.ic_sack_dollar))
        bottomNavigation.add(chetanBottomNavigation.Model(mydep,R.drawable.ic_depense))
        bottomNavigation.add(chetanBottomNavigation.Model(mycompt,R.drawable.ic_compte))
        bottomNavigation.add(chetanBottomNavigation.Model(mystat,R.drawable.finance))



        bottomNavigation.circleColor = Color.parseColor("#38B597")
        bottomNavigation.backgroundBottomColor = Color.parseColor("#38B597")
        bottomNavigation.countBackgroundColor = Color.parseColor("#ff6f00")
        bottomNavigation.countTextColor = Color.parseColor("#ffffff")
        bottomNavigation.defaultIconColor = Color.parseColor("#ffffff")
        bottomNavigation.selectedIconColor = Color.parseColor("#ffffff")

        //bottomNavigation.setCount(mycompt,"3")

        bottomNavigation.setOnShowListener { item ->
            val name: String = when (item.id) {
                myhome -> "Accueil"
                mydep ->  "Dépenses"
                mybud ->  "Budget"
                mystat -> "Analyse"
                mycompt ->"Revenus"
                else -> ""
            }
            toolbar.title = name
        }

        bottomNavigation.setOnClickMenuListener { item ->

            val fragment = when (item.id) {
                myhome -> HomeFragment()
                mydep -> DepenseFragment()
                mybud -> BudgetFragment()
                mystat -> StatistFragment()
                mycompt -> CompteFragment()
                else -> HomeFragment()
            }

            fragment.let {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_view_tag, it as Fragment)
                    .commit()
                // Toast.makeText(this@HomeActivity, "item clicked", Toast.LENGTH_SHORT).show()
            }
        }

        bottomNavigation.setOnReselectListener {
            Toast.makeText(this@HomeActivity, "item clicked", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu1, menu)
        return true
    }


    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun openDrawer() {
        drawerLayout.openDrawer(GravityCompat.START)
    }

    fun openDrawer(view: View) {}

    fun isUserLoggedIn(context: Context): Boolean {
        val sharedPreferences = context.getSharedPreferences("user_session", MODE_PRIVATE)
        val userId = sharedPreferences.getInt("userId", -1)
        return userId != -1
    }

    fun getUsername(context: Context): String {
        val sharedPreferences = context.getSharedPreferences("user_session", MODE_PRIVATE)
        return sharedPreferences.getString("username", "") ?: ""
    }


}
