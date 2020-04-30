package com.tick.taku.notificationwatcher

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import androidx.annotation.IdRes
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.tick.taku.android.corecomponent.di.ActivityScope
import com.tick.taku.android.corecomponent.di.FragmentScope
import com.tick.taku.android.corecomponent.ktx.dataBinding
import com.tick.taku.android.corecomponent.util.showDialog
import com.tick.taku.notificationwatcher.databinding.ActivityMainBinding
import com.tick.taku.notificationwatcher.view.di.MessageAssistedInjectModule
import com.tick.taku.notificationwatcher.view.message.MessageFragment
import com.tick.taku.notificationwatcher.view.preference.PreferencesActivity
import com.tick.taku.notificationwatcher.view.room.RoomListFragment
import dagger.Module
import dagger.android.AndroidInjector
import dagger.android.ContributesAndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class MainActivity : AppCompatActivity(R.layout.activity_main), HasAndroidInjector {

    @Inject lateinit var injector: DispatchingAndroidInjector<Any>
    override fun androidInjector(): AndroidInjector<Any> = injector

    private val binding: ActivityMainBinding by dataBinding()

    private val navController: NavController by lazy {
        findNavController(R.id.fragment_container)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupToolbar()

        setupDrawer()

        checkForPermission()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(navController)
    }

    private fun setupDrawer() {
        ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.toolbar,
            R.string.content_description_drawer_open,
            R.string.content_description_drawer_close
        )
            .apply {
                isDrawerSlideAnimationEnabled = false
            }.also {
                binding.drawerLayout.addDrawerListener(it)
                it.syncState()
            }

        binding.drawer.setNavigationItemSelectedListener {
            when (DrawerMenu.findById(it.itemId)) {
                DrawerMenu.SETTINGS -> { PreferencesActivity.start(this) }
                else -> {}
            }
            binding.drawerLayout.closeDrawers()
            false
        }
    }

    /**
     * Check permission for notification listener service is granted.
     */
    private fun checkForPermission() {
        if (!NotificationManagerCompat.getEnabledListenerPackages(this).contains(packageName))
            showDialog {
                setMessage(R.string.notification_listener_denied)
                setNegativeButton(R.string.cancel)
                setPositiveButton(R.string.ok) {
                    startActivity(Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS))
                }
            }
    }
}

private enum class DrawerMenu(@IdRes val id: Int) {
    SETTINGS(R.id.menu_settings);
    companion object {
        fun findById(id: Int): DrawerMenu? = values().find { it.id == id }
    }
}

@Module
abstract class MainActivityModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [MainActivityBinder::class])
    abstract fun contributeMainActivity(): MainActivity

    @Module
    abstract class MainActivityBinder {

        @FragmentScope
        @ContributesAndroidInjector(modules = [MessageAssistedInjectModule::class])
        abstract fun contributeMessageFragment(): MessageFragment

        @FragmentScope
        @ContributesAndroidInjector(modules = [MessageAssistedInjectModule::class])
        abstract fun contributeRoomListFragment(): RoomListFragment

    }

}