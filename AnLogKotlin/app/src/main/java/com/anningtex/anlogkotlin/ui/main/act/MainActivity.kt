package com.anningtex.anlogkotlin.ui.main.act

import android.view.MenuItem
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.anningtex.anlogkotlin.R
import com.anningtex.anlogkotlin.base.BaseActivity
import com.anningtex.anlogkotlin.ui.main.adapter.TabPagerAdapter
import com.anningtex.anlogkotlin.ui.main.contract.MainContract
import com.anningtex.anlogkotlin.ui.main.fragment.DeliveryGoodsFragment
import com.anningtex.anlogkotlin.ui.main.fragment.InspectionsFragment
import com.anningtex.anlogkotlin.ui.main.presenter.MainPresenter
import com.anningtex.anlogkotlin.weight.CustomViewPager
import com.anningtex.baselibrary.manager.AppManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MainActivity : BaseActivity<MainContract.View, MainPresenter>(), MainContract.View {
    var mainViewpager: CustomViewPager? = null
    var mMainBottomNav: BottomNavigationView? = null
    var navigationView: NavigationView? = null
    var drawerLayout: DrawerLayout? = null

    private var fragments: MutableList<Fragment>? = null
    private var mTitleList: MutableList<String>? = null
    private var lastTime: Long = 0
    override val layoutResId: Int = R.layout.activity_main

    override fun createPresenter(): MainPresenter =
        MainPresenter()

    override fun initViews() {
        mainViewpager = findViewById(R.id.main_fragment_container)
        mMainBottomNav = findViewById(R.id.main_bottom_nav)
        navigationView = findViewById(R.id.nav)
        drawerLayout = findViewById(R.id.activity_na)

        fragments = ArrayList()
        mTitleList = mutableListOf()

        initViewPager();
        navigationListener();
        setupBottomNavigationView();
    }

    private fun setupBottomNavigationView() {
        mMainBottomNav?.setOnNavigationItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.home -> {
                    mainViewpager!!.currentItem = 0
                }
                R.id.mine -> {
                    mainViewpager!!.currentItem = 1
                }
            }
            true
        }
    }

    private fun navigationListener() {
        navigationView!!.setNavigationItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.menu_scan -> toast("扫一扫")
                R.id.menu_switch_name -> toast("切换账号")
                R.id.menu_update_app -> toast("软件更新")
                R.id.menu_change_pwd -> toast("修改密码")
                R.id.menu_version -> toast("版本")
                else -> throw IllegalStateException("Unexpected value: " + item.itemId)
            }
            drawerLayout!!.closeDrawer(navigationView!!)
            true
        }
    }

    private fun initViewPager() {
        fragments?.add(InspectionsFragment(drawerLayout, navigationView))
        fragments?.add(DeliveryGoodsFragment())

        mainViewpager?.adapter = TabPagerAdapter(
            supportFragmentManager,
            mTitleList,
            fragments
        )
        //设置默认选中页
        mainViewpager?.currentItem = 0
    }

    override fun onBackPressed() {
        var currentTime = System.currentTimeMillis()
        if (currentTime - lastTime < 2 * 1000) {
            super.onBackPressed()
            AppManager.getAppManager().AppExit(this)
        } else {
            toast("再按一次退出应用")
            lastTime = currentTime
        }
    }
}
