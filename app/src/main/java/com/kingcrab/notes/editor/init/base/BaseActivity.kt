package com.kingcrab.notes.editor.init.base

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupWithNavController

abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity() {

    protected lateinit var binding: T
    abstract var layoutId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(layoutInflater, layoutId, null, false)
        setContentView(binding.root)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = (supportFragmentManager.fragments
            .firstOrNull { it is NavHostFragment } as? NavHostFragment)?.navController
        return if (navController?.navigateUp() == true) {
            true
        } else {
            finish()
            true
        }
    }

    protected fun checkPermission(
        permissionRequest: Array<String>,
        requestPermissionLauncher: ActivityResultLauncher<Array<String>>
    ): Boolean {
        var isGrantedAll = true
        for (permission in permissionRequest) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                isGrantedAll = false
                break
            }
        }
        if (!isGrantedAll) {
            requestPermissionLauncher.launch(permissionRequest)
            return false
        }
        return true
    }

    protected fun setupToolbar(
        toolbar: Toolbar,
        @IdRes navHostFragmentID: Int,
        showBackButtonInStartDestination: Boolean = true
    ) {
        setSupportActionBar(toolbar)
        (supportFragmentManager.findFragmentById(navHostFragmentID) as? NavHostFragment)?.let {
            val navController = it.navController
            val appBarConfiguration = if (showBackButtonInStartDestination) {
                AppBarConfiguration(
                    topLevelDestinationIds = setOf(),
                    fallbackOnNavigateUpListener = ::onSupportNavigateUp
                )
            } else {
                AppBarConfiguration.Builder(navController.graph).build()
            }
            setupWithNavController(toolbar, navController, appBarConfiguration)
            it.navController.addOnDestinationChangedListener { navCtl: NavController, navDestination: NavDestination, bundle: Bundle? ->
                val title =
                    if (navDestination.label == null) "" else navDestination.label.toString()
                if (title.isNotEmpty()) {
                    setTitle(title)
                }
            }
        }
    }
}