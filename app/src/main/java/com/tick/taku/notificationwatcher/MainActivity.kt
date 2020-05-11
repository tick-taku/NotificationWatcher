package com.tick.taku.notificationwatcher

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import androidx.annotation.IdRes
import androidx.core.app.NotificationManagerCompat
import androidx.core.view.GravityCompat
import androidx.lifecycle.observe
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import coil.api.load
import coil.transform.CircleCropTransformation
import com.linecorp.linesdk.Scope
import com.linecorp.linesdk.auth.LineAuthenticationParams
import com.linecorp.linesdk.auth.LineLoginApi
import com.tick.taku.android.corecomponent.di.ActivityScope
import com.tick.taku.android.corecomponent.di.FragmentScope
import com.tick.taku.android.corecomponent.ktx.dataBinding
import com.tick.taku.android.corecomponent.ktx.getBinding
import com.tick.taku.android.corecomponent.ktx.viewModelProvider
import com.tick.taku.android.corecomponent.util.showDialog
import com.tick.taku.notificationwatcher.databinding.ActivityMainBinding
import com.tick.taku.notificationwatcher.databinding.LayoutDrawerHeaderBinding
import com.tick.taku.notificationwatcher.dialog.IconDialog
import com.tick.taku.notificationwatcher.domain.api.entity.AccountEntity
import com.tick.taku.notificationwatcher.view.di.MessageAssistedInjectModule
import com.tick.taku.notificationwatcher.view.message.MessageFragment
import com.tick.taku.notificationwatcher.view.preference.PreferencesActivity
import com.tick.taku.notificationwatcher.view.room.RoomListFragment
import com.tick.taku.notificationwatcher.viewmodel.MainViewModel
import dagger.Module
import dagger.android.AndroidInjector
import dagger.android.ContributesAndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import onactivityresult.ActivityResult
import javax.inject.Inject
import javax.inject.Provider

class MainActivity : AppCompatActivity(R.layout.activity_main), HasAndroidInjector {

    @Inject lateinit var injector: DispatchingAndroidInjector<Any>
    override fun androidInjector(): AndroidInjector<Any> = injector

    private val binding: ActivityMainBinding by dataBinding()
    private val drawerHeaderBinding: LayoutDrawerHeaderBinding by lazy {
        binding.drawer.getBinding<LayoutDrawerHeaderBinding>()
    }

    @Inject lateinit var viewModelFactory: Provider<MainViewModel>
    private val viewModel: MainViewModel by viewModelProvider {
        viewModelFactory.get()
    }

    private val navController: NavController by lazy {
        findNavController(R.id.fragment_container)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupToolbar()

        checkForPermission()
    }

    override fun onBackPressed() {
        binding.drawerLayout.run {
            when (isDrawerOpen(GravityCompat.START)) {
                true -> closeDrawer(GravityCompat.START)
                false -> super.onBackPressed()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(binding.drawerLayout)
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)

        val config = AppBarConfiguration(setOf(R.id.room_list_fragment), binding.drawerLayout)
        setupActionBarWithNavController(navController, config)

        setupDrawer()
    }

    private fun setupDrawer() {
        viewModel.account.observe(this) {
            drawerHeaderBinding.setupAccount(it)
        }

        drawerHeaderBinding.accountIcon.setOnClickListener {
            IconDialog.show(supportFragmentManager, viewModel.account.value?.iconUrl)
        }

        binding.drawer.setNavigationItemSelectedListener {
            when (DrawerMenu.findById(it.itemId)) {
                DrawerMenu.ACCOUNT_LINK -> { linkAccount() }
                DrawerMenu.SETTINGS -> { PreferencesActivity.start(this) }
                else -> {}
            }
            binding.drawerLayout.closeDrawers()
            false
        }
    }

    private fun LayoutDrawerHeaderBinding.setupAccount(accountInfo: AccountEntity) {
        accountName.text = accountInfo.name
        accountMsg.text = accountInfo.status
        accountIcon.load(accountInfo.iconUrl) {
            transformations(CircleCropTransformation())
        }
    }

    private fun linkAccount() {
        val intent = LineAuthenticationParams.Builder().scopes(mutableListOf(Scope.PROFILE)).let {
            LineLoginApi.getLoginIntent(applicationContext, BuildConfig.LINE_CHANNEL_ID, it.build())
        }
        startActivityForResult(intent, MainViewModel.ACCOUNT_LINK_RESULT)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        ActivityResult.onResult(requestCode, resultCode, data).into(viewModel)
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
    ACCOUNT_LINK(R.id.account_link),
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