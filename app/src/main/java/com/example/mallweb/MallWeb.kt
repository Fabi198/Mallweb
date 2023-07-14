package com.example.mallweb


import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mallweb.adapters.NewBannersAdapter
import com.example.mallweb.adapters.RoundBottomsAdapter
import com.example.mallweb.auth.AuthFragment
import com.example.mallweb.databinding.ActivityMallWebBinding
import com.example.mallweb.db.ArrayBrands.arrayBrands
import com.example.mallweb.db.DbMallweb
import com.example.mallweb.entities.dbEntities.Brand
import com.example.mallweb.fragments.CategoryFragment
import com.example.mallweb.fragments.ProductDetailFragment
import com.example.mallweb.fragments.SubCategoryFragment
import com.example.mallweb.fragmentsDrawerMenu.*
import com.example.mallweb.fragmentsSubAllCategories.*
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import java.util.*


@Suppress("DEPRECATION")
class MallWeb: AppCompatActivity() {

    private lateinit var binding: ActivityMallWebBinding
    private lateinit var animFadeOut: Animation
    private lateinit var animFadeIn: Animation
    private lateinit var animFrag: Animation
    private val doubleClickTimeDelta = 300 // Intervalo de tiempo en milisegundos para el doble clic
    private var lastClickTime: Long = 0 // Variable para almacenar el tiempo del último clic en el botón "back"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMallWebBinding.inflate(layoutInflater)
        setContentView(binding.root)



        bundleFromAuth()
        setupDrawerNavigationBar()
        setupRoundBottoms()
        setupBannersRV()
        setShoppingCartButtonToolbar()
        setMyAccountButtonToolbar()
        setSearchText()
    }

    private fun bundleFromAuth() {
        // Si el usuario inicio sesión mientras realizaba alguna operacion, el fragmento agrega un
        // string que permite saber donde estaba posicionado el usuario y devolverlo ahi mismo

        // If the user log in while he's doing some operation, the fragment add a string
        // to know where he's position was at, and lead him there after the activity refresh
        val extras = intent.extras
        if (extras?.getString("task") != null) {
            when (extras.getString("task")) {
                "Open Account Fragment" -> { showFragment(
                    AccountFragment(),
                    "AccountFragment"
                ) }
                "Open Product Detail Fragment" -> { showFragment(ProductDetailFragment(), "ProductDetailFragment", idClient = extras.getInt("idClient"), idProduct = extras.getInt("idProduct")) }
                "Open Shopping Cart Fragment Step 1" -> { showFragmentStep1() }
            }
        }
    }


    private fun setSearchText() {
        binding.etSearch.setOnEditorActionListener(OnEditorActionListener { _, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_DONE || keyEvent.action == KeyEvent.ACTION_DOWN || keyEvent.action == KeyEvent.KEYCODE_ENTER) {
                if (binding.etSearch.text.isNotEmpty()) {
                    var findBrand = false
                    var brand = Brand()

                    arrayBrands.forEach { brandResult -> if (binding.etSearch.text.toString().lowercase() == brandResult.name.lowercase()) { findBrand = true; brand = brandResult } }

                    if (findBrand) {
                        val dbMallWeb = DbMallweb(this)
                        showFragment(
                            CategoryFragment(),
                            "CategoryFragment",
                            brand.name,
                            dbMallWeb.queryForCategoryCant(brand.id),
                            brand.id
                        )
                    } else {
                        showFragment(
                            SubCategoryFragment(),
                            "SubCategoryFragment",
                            name = binding.etSearch.text.toString(),
                            searchString = binding.etSearch.text.toString().lowercase()
                        )
                    }
                    hideKeyboard()
                    binding.etSearch.setText("")
                }
                return@OnEditorActionListener true
            }
            false
        })
        binding.btnSearch.setOnClickListener {
            if (binding.etSearch.text.isNotEmpty()) {
                var findBrand = false
                var brand = Brand()

                arrayBrands.forEach { brandResult -> if (binding.etSearch.text.toString().lowercase() == brandResult.name.lowercase()) { findBrand = true; brand = brandResult } }

                if (findBrand) {
                    val dbMallWeb = DbMallweb(this)
                    showFragment(
                        CategoryFragment(),
                        "CategoryFragment",
                        brand.name,
                        dbMallWeb.queryForCategoryCant(brand.id),
                        brand.id
                    )
                } else {
                    showFragment(
                        SubCategoryFragment(),
                        "SubCategoryFragment",
                        name = binding.etSearch.text.toString(),
                        searchString = binding.etSearch.text.toString().lowercase()
                    )
                }
                hideKeyboard()
                binding.etSearch.setText("")
            }
        }
    }

    private fun session(): Boolean {
        val prefs: SharedPreferences = getSharedPreferences("MY PREF", MODE_PRIVATE)
        val email = prefs.getString("email", null)
        val provider = prefs.getString("provider", null)
        return email != null && provider != null
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            val currentTime = System.currentTimeMillis()
            if (currentTime - lastClickTime < doubleClickTimeDelta) {
                supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                val animLeftOut: Animation = AnimationUtils.loadAnimation(this, R.anim.left_out)
                binding.mallwebHomeContainer.startAnimation(animLeftOut)
                animLeftOut.setAnimationListener(object: AnimationListener{
                    override fun onAnimationStart(animation: Animation?) {}
                    override fun onAnimationEnd(animation: Animation?) { supportFragmentManager.popBackStack() }
                    override fun onAnimationRepeat(animation: Animation?) {}
                })
                allVisible()
            } else {
                lastClickTime = currentTime
                val prefs = getSharedPreferences("MY PREF", MODE_PRIVATE)
                val isToastShown = prefs.getBoolean("isToastShown", false)
                if (!isToastShown) {
                    Toast.makeText(this, "Presiona 2 veces para volver al inicio", Toast.LENGTH_SHORT).show()
                    val editor = prefs.edit()
                    editor.putBoolean("isToastShown", true)
                    editor.apply()
                }
                supportFragmentManager.popBackStack()
            }
        } else if (supportFragmentManager.backStackEntryCount == 1) {
            val animLeftOut: Animation = AnimationUtils.loadAnimation(this, R.anim.left_out)
            binding.mallwebHomeContainer.startAnimation(animLeftOut)
            animLeftOut.setAnimationListener(object: AnimationListener{
                override fun onAnimationStart(animation: Animation?) {}
                override fun onAnimationEnd(animation: Animation?) { supportFragmentManager.popBackStack() }
                override fun onAnimationRepeat(animation: Animation?) {}
            })
            allVisible()
        } else {
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        val prefs = getSharedPreferences("MY PREF", MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putBoolean("isToastShown", false)
        editor.apply()
    }

    private fun allVisible() {
        animFadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        binding.tvBottomToolbar.visibility = View.VISIBLE
        binding.tvBottomToolbar.startAnimation(animFadeIn)
        binding.lltvBottomToolbar.visibility = View.VISIBLE
        binding.lltvBottomToolbar.startAnimation(animFadeIn)
        binding.rvRoundBottoms.visibility = View.VISIBLE
        binding.rvRoundBottoms.startAnimation(animFadeIn)
        binding.rvBanners.visibility = View.VISIBLE
        binding.rvBanners.startAnimation(animFadeIn)
        binding.cvSearch.visibility = View.VISIBLE
        binding.cvSearch.startAnimation(animFadeIn)
    }

    private fun allGone() {
        animFadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out)
        binding.tvBottomToolbar.visibility = View.GONE
        binding.tvBottomToolbar.startAnimation(animFadeOut)
        binding.lltvBottomToolbar.visibility = View.GONE
        binding.lltvBottomToolbar.startAnimation(animFadeOut)
        binding.rvRoundBottoms.visibility = View.GONE
        binding.rvRoundBottoms.startAnimation(animFadeOut)
        binding.rvBanners.visibility = View.GONE
        binding.rvBanners.startAnimation(animFadeOut)
        binding.cvSearch.visibility = View.GONE
        binding.cvSearch.startAnimation(animFadeOut)
    }

    private fun setupDrawerNavigationBar() {
        if (session()) {
            binding.nvLateralMallweb.menu.findItem(R.id.item_close_mallweb_session).isVisible = true
            binding.btnMyAccount.visibility = View.VISIBLE
        }
        animFrag = AnimationUtils.loadAnimation(this, R.anim.left_in)
        setSupportActionBar(binding.toolbarMallwebHome)
        supportActionBar?.title = null
        Picasso.get().load(R.drawable.mallweb_logo).centerCrop().resize(600, 200).into(binding.ivToolbarMallwebHome)
        binding.ivToolbarMallwebHome.setOnClickListener { refresh() }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val drawerToggle = ActionBarDrawerToggle(
            this,
            binding.dlMallweb,
            binding.toolbarMallwebHome,
            R.string.abrir,
            R.string.cerrar
        )
        binding.dlMallweb.addDrawerListener(drawerToggle)
        drawerToggle.syncState()
        drawerToggle.drawerArrowDrawable.color = resources.getColor(R.color.white)
        binding.nvLateralMallweb.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.item_home -> { refresh() }
                R.id.item_categories -> { showFragment(
                    AllCategoriesFragment(),
                    "AllCategoriesFragment"
                ) }
                R.id.item_shop_container -> { if (session()) { showFragmentStep1() } else { showFragment(
                    AuthFragment(),
                    "AuthFragment",
                    tagForAuth = "ShoppingCartFragmentStep1"
                ) } }
                R.id.item_your_orders -> { if (session()) { showFragment(
                    AccountFragment(),
                    "AccountFragment"
                ) } else { showFragment(
                    AuthFragment(),
                    "AuthFragment",
                    tagForAuth = "AccountFragment"
                ) } }
                R.id.item_personal_data -> { if (session()) { showFragment(
                    AccountFragment(),
                    "AccountFragment"
                ) } else { showFragment(
                    AuthFragment(),
                    "AuthFragment",
                    tagForAuth = "AccountFragment"
                ) } }
                R.id.item_who_are_we -> { showFragment(
                    WhoAreWeFragment(),
                    "WhoAreWeFragment"
                ) }
                R.id.item_faq -> { showFragment(
                    FAQFragment(),
                    "FAQFragment"
                ) }
                R.id.item_deliver_method -> { showFragment(
                    DeliverMethodsFragment(),
                    "DeliverMethodsFragment"
                ) }
                R.id.item_payment_method -> { showFragment(
                    BankDataFragment(),
                    "BankDataFragment"
                ) }
                R.id.item_contact -> { showFragment(
                    ContactUsFragment(),
                    "ContactUsFragment"
                ) }
                R.id.item_close_mallweb_session -> { signOut() }
            }
            binding.dlMallweb.closeDrawers()
            true
        }
    }

    private fun refresh() {
        startActivity(Intent(this, MallWeb::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

    private fun signOut() {
        FirebaseAuth.getInstance().signOut()
        val prefs: SharedPreferences = getSharedPreferences("MY PREF", MODE_PRIVATE)
        val prefsEd = prefs.edit()
        prefsEd.clear()
        prefsEd.apply()
        refresh()
    }

    private fun setupRoundBottoms() {
        binding.rvRoundBottoms.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        binding.rvRoundBottoms.adapter = RoundBottomsAdapter {
            when(it) {
                "Computación" -> {showFragment(
                    NotebooksFragment(),
                    "NotebooksFragment"
                )}
                "Comp. PC" -> {showFragment(
                    ComponentsFragment(),
                    "ComponentsFragment"
                )}
                "Storage" -> {showFragment(
                    StorageMenu(),
                    "StorageMenu"
                )}
                "Periféricos" -> {showFragment(
                    PeripheralsMenu(),
                    "PeripheralsMenu"
                )}
                "Conectividad" -> {showFragment(
                    ConnectivityFragment(),
                    "ConnectivityFragment"
                )}
                "Impresión" -> {showFragment(
                    PrintFragment(),
                    "PrintFragment"
                )}
                "Audio y Video" -> {showFragment(
                    AudioAndVideoFragment(),
                    "AudioAndVideoFragment"
                )}
                "Zona Gamer" -> {showFragment(
                    GamerZoneFragment(),
                    "GamerZoneFragment"
                )}
            }
        }
    }

    private fun setupBannersRV() {
        val dbMallWeb = DbMallweb(this)
        binding.rvBanners.layoutManager = LinearLayoutManager(this)
        binding.rvBanners.adapter = NewBannersAdapter {
            when(it) {
                "Mi Cuenta" -> {if (session()) { showFragment(
                    AccountFragment(),
                    "Account Fragment"
                ) } else { showFragment(
                    AuthFragment(),
                    "Auth Fragment",
                    tagForAuth = "AccountFragment"
                )} }
                "Marcas Destacadas" -> {showFragment(
                    FeaturedFragment(),
                    "FeaturedFragment"
                )}
                "Comunidad" -> {showFragment(
                    ContactUsFragment(),
                    "ContactUsFragment"
                )}
                "Zona Gamer" -> {showFragment(
                    CategoryFragment(),
                    "CategoryFragment",
                    "ZONA GAMER",
                    setArrayCategory(arrayOf(15,16,17,43,44,3,46,26,42,41,45,47))
                )}
                "Promociones" -> {showFragment(
                    PromosFragment(),
                    "PromosFragment"
                )}
                "Corsair" -> {showFragment(
                    CategoryFragment(),
                    "CategoryFragment",
                    "CORSAIR",
                    dbMallWeb.queryForCategoryCant(9),
                    9
                )}
                "Logitech" -> {showFragment(
                    CategoryFragment(),
                    "CategoryFragment",
                    "LOGITECH",
                    dbMallWeb.queryForCategoryCant(27),
                    27
                )}
                "Seagate" -> {showFragment(
                    CategoryFragment(),
                    "CategoryFragment",
                    "SEAGATE",
                    dbMallWeb.queryForCategoryCant(37),
                    37
                )}
                "Computación" -> {showFragment(
                    CategoryFragment(),
                    "CategoryFragment",
                    "NOTEBOOKS",
                    setArrayCategory(arrayOf(30,31,29,28))
                )}
                "Comp. PC" -> {showFragment(
                    CategoryFragment(),
                    "CategoryFragment",
                    "COMPONENTES PARA PC",
                    setArrayCategory(arrayOf(9,10,11,12,13,14,15,16,17))
                )}
                "Almacenamiento" -> { showFragment(
                    CategoryFragment(),
                    "CategoryFragment",
                    "ALMACENAMIENTO",
                    setArrayCategory(arrayOf(1, 2, 3, 4))
                )}
                "Periféricos" -> {showFragment(
                    CategoryFragment(),
                    "CategoryFragment",
                    "PERIFÉRICOS",
                    setArrayCategory(arrayOf(32,33,36,38,35,36,39,37,34))
                )}
                "Conectividad" -> {showFragment(
                    CategoryFragment(),
                    "CategoryFragment",
                    "CONECTIVIDAD",
                    setArrayCategory(arrayOf(19,18,21,20))
                )}
                "Impresión" -> {showFragment(
                    CategoryFragment(),
                    "CategoryFragment",
                    "IMPRESIÓN",
                    setArrayCategory(arrayOf(22,23,24,25))
                )}
                "Audio y Video" -> {showFragment(
                    CategoryFragment(),
                    "CategoryFragment",
                    "AUDIO Y VIDEO",
                    setArrayCategory(arrayOf(5,6,7,8))
                )}
            }
        }
    }

    private fun setArrayCategory(i: Array<Int>): ArrayList<Int> {
        val idCArray = ArrayList<Int>()
        i.forEach { idCArray.add(it) }
        return idCArray
    }

    private fun setShoppingCartButtonToolbar() {
        animFrag = AnimationUtils.loadAnimation(this, R.anim.left_in)
        if (session()) {
            val dbMallWeb = DbMallweb(this)
            val prefs: SharedPreferences = getSharedPreferences("MY PREF", MODE_PRIVATE)
            val email = prefs.getString("email", null)
            if (email != null) { binding.tvCantShoppingCart.text = dbMallWeb.queryForProductsOnCart(dbMallWeb.queryForClient(email).id).size.toString() }
        }
        binding.btnShoppingCartToolbar.setOnClickListener {
            if (session()) {
                showFragmentStep1()
            } else {
                showFragment(AuthFragment(), "AuthFragment", tagForAuth = "ShoppingCartFragmentStep1")
            }
        }
    }

    private fun showFragmentStep1() {
        val prefs: SharedPreferences = getSharedPreferences("MY PREF", MODE_PRIVATE)
        val email = prefs.getString("email", null)
        if (email != null) {
            val dbMallweb = DbMallweb(this)
            showFragment(
                ShoppingCartFragmentStep1(),
                "ShoppingCartFragmentStep1",
                idClient = dbMallweb.queryForClient(email).id
            )
        }
    }

    private fun setMyAccountButtonToolbar() {
        animFrag = AnimationUtils.loadAnimation(this, R.anim.left_in)
        binding.btnMyAccount.setOnClickListener {
            showFragment(
                AccountFragment(),
                "AccountFragment"
            )
        }
    }

    private fun showFragment(
        fragment: Fragment,
        tag: String,
        name: String? = null,
        idCArray: ArrayList<Int>? = null,
        idBrand: Int? = null,
        searchString: String? = null,
        idClient: Int? = null,
        idProduct: Int? = null,
        tagForAuth: String? = null
    ) {
        animFrag = AnimationUtils.loadAnimation(this, R.anim.left_in)
        val bundle = Bundle()
        bundle.putInt("ContainerID", binding.mallwebHomeContainer.id)
        if (name != null) { bundle.putString("NameCategory", name) }
        if (idCArray != null) { bundle.putIntegerArrayList("IDCategoryArray", idCArray) }
        if (idBrand != null) { bundle.putInt("IdBrand", idBrand) }
        if (searchString != null) { bundle.putString("Search", searchString) }
        if (idClient != null) { bundle.putInt("IdClient", idClient) }
        if (idProduct != null) { bundle.putInt("IDProduct", idProduct) }
        if (tagForAuth != null) { bundle.putString("tagForAuth", tagForAuth) }
        fragment.arguments = bundle
        supportFragmentManager.beginTransaction()
            .replace(binding.mallwebHomeContainer.id, fragment, tag)
            .addToBackStack(tag)
            .commit()
        allGone()
        binding.mallwebHomeContainer.visibility = View.VISIBLE
        binding.mallwebHomeContainer.startAnimation(animFrag)
    }

    private fun hideKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.dlMallweb.windowToken, 0)
    }
}