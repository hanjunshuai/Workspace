package com.anningtex.wanandroid.main

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.BitmapFactory
import android.util.Log
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.drawerlayout.widget.DrawerLayout
import com.anningtex.wanandroid.R
import com.anningtex.wanandroid.about.AboutActivity
import com.anningtex.wanandroid.base.mvp.BaseMVPActivity
import com.anningtex.wanandroid.login.LoginActivity
import com.anningtex.wanandroid.common.bean.FragmentItem
import com.anningtex.wanandroid.db.DbManager
import com.anningtex.wanandroid.db.bean.User
import com.anningtex.wanandroid.favorite.FavoriteActivity
import com.anningtex.wanandroid.gank.GankFragment
import com.anningtex.wanandroid.main.adapter.MainViewPageAdapter
import com.anningtex.wanandroid.main.contract.MainContract
import com.anningtex.wanandroid.home.HomeFragment
import com.anningtex.wanandroid.main.presenter.MainPresenter
import com.anningtex.wanandroid.main.widgets.MainViewPager
import com.anningtex.wanandroid.meizi.MeiziActivity
import com.anningtex.wanandroid.project.ProjectFragment
import com.anningtex.wanandroid.search.SearchActivity
import com.anningtex.wanandroid.setting.SettingActivity
import com.anningtex.wanandroid.system.SystemFragment
import com.anningtex.wanandroid.util.gotoActivity
import com.anningtex.wanandroid.util.isCookieNotEmpty
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import com.jaeger.library.StatusBarUtil
import kotlin.system.exitProcess

/**
 * 主页
 * https://github.com/xing16/WanAndroid-Kotlin/blob/master/app/src/main/java/com/xing/wanandroid/main/adapter/MainViewPageAdapter.kt
 */
@SuppressLint("WrongConstant")
class MainActivity : BaseMVPActivity<MainContract.View, MainPresenter>(),
    MainContract.View, View.OnClickListener {
    private lateinit var mainMenu: ImageView
    private lateinit var mainSearch: ImageView
    private lateinit var mainTabLayout: TabLayout
    private lateinit var mainViewPager: MainViewPager
    private lateinit var mAdapter: MainViewPageAdapter
    private lateinit var navigationView: NavigationView
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var avatarBackground: ImageView
    private lateinit var usernameTextView: TextView
    private var loggedIn = false

    override fun getLayoutResId(): Int = R.layout.activity_main

    override fun createPresenter(): MainPresenter = MainPresenter()

    override fun initView() {
        drawerLayout = findViewById(R.id.dl_drawer_layout)
        StatusBarUtil.setColorForDrawerLayout(
            this,
            drawerLayout,
            resources.getColor(R.color.colorPrimary),
            0
        )
        navigationView = findViewById(R.id.nv_left_navigation)
        val headerView: View = navigationView.getHeaderView(0)
        usernameTextView = headerView.findViewById(R.id.tv_nav_username)
        avatarBackground = headerView.findViewById(R.id.iv_avatar_background)
        mainMenu = findViewById(R.id.iv_main_menu)
        mainSearch = findViewById(R.id.iv_main_search)
        mainTabLayout = findViewById(R.id.tl_main_tab)
        mainViewPager = findViewById(R.id.vp_main_pager)

        mainMenu.setOnClickListener {
            openDrawer()
        }

        usernameTextView.setOnClickListener(this)

        navigationView.setNavigationItemSelectedListener { item ->
            closeDrawer()
            when (item.itemId) {
                R.id.item_nav_happy_minute -> {
                    gotoActivity(mContext as Activity, MeiziActivity().javaClass)
                }
                R.id.item_nav_favorite -> {
                    gotoActivity(mContext as Activity, FavoriteActivity().javaClass)
                }
                R.id.item_nav_setting -> {
                    gotoActivity(mContext as Activity, SettingActivity().javaClass)
                }
                R.id.item_nav_about -> {
                    gotoActivity(mContext as Activity, AboutActivity().javaClass)
                }
            }
            true
        }

        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.avatar)
//        avatarBackground.setImageBitmap(blur(mContext, bitmap, 22))
    }

    override fun initData() {
        super.initData()
        val list = mutableListOf<FragmentItem>()
        list.add(FragmentItem("首页", HomeFragment.newInstance()))
        list.add(FragmentItem("项目", ProjectFragment.newInstance()))
        list.add(FragmentItem("体系", SystemFragment.newInstance()))
        list.add(FragmentItem("干货", GankFragment.newInstance()))
        mAdapter = MainViewPageAdapter(this, supportFragmentManager, list)
        mainViewPager.adapter = mAdapter
        mainTabLayout.setupWithViewPager(mainViewPager)

        for (i in 0..mainTabLayout.tabCount) {
            val tabView: TabLayout.Tab? = mainTabLayout.getTabAt(i)
            tabView?.customView = mAdapter.getTabView(i)
        }

        // 默认选中第 0 个
        mainViewPager.currentItem = 0
        changeTabView(mainTabLayout.getTabAt(0), 22f, true)

        mainTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                changeTabView(tab, 18f, false)
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                changeTabView(tab, 22f, true)

            }
        })
        setUsernameFromCache()
        presenter.getUserInfo()
        mainSearch.setOnClickListener(this)
    }

    private fun changeTabView(tab: TabLayout.Tab?, textSize: Float, isSelected: Boolean) {
        val view: View? = tab?.customView
        val textView: TextView? = view?.findViewById(R.id.tv_tab_title)
        textView?.textSize = textSize
        if (isSelected) {
            textView?.setTextColor(resources.getColor(android.R.color.black))
            val width = textView?.measuredWidth
            Log.e("debug", "width = $width")
        } else {
            textView?.setTextColor(resources.getColor(R.color.gray_959698))
        }
    }

    private fun setUsernameFromCache() {
        loggedIn = isCookieNotEmpty(mContext)
        if (!loggedIn) {
            usernameTextView.text = getString(R.string.click_to_login)
        } else {
            val user = getCacheUser()
            val username: String
            if (user != null) {
                username = user.username
            } else {
                username = ""
            }
            usernameTextView.text = username
        }
    }

    private fun getCacheUser(): User? {
        val users = DbManager.getInstance().getUserDao().loadAll()
        if (users != null && users.size > 0) {
            return users[0]
        }
        return null
    }

    private fun openDrawer() {
        drawerLayout.openDrawer(Gravity.START)
    }

    private fun closeDrawer() {
        drawerLayout.closeDrawer(Gravity.START)
    }

    override fun onUserInfo(user: User) {
        Log.e("MainActivity", user.toString())
        loggedIn = isCookieNotEmpty(mContext)
        if (loggedIn) {
            usernameTextView.text = user.username
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_nav_username -> {
                loggedIn = isCookieNotEmpty(mContext)
                if (!loggedIn) {
                    gotoLoginActivity()
                }
            }

            R.id.iv_main_search -> {
                gotoSearchActivity()
                overridePendingTransition(0, 0)
            }
        }
    }

    private fun gotoSearchActivity() {
        gotoActivity(this, SearchActivity().javaClass)
    }

    private fun gotoLoginActivity() {
        gotoActivity(this, LoginActivity().javaClass)
    }

    private var lastTime: Long = 0L

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            val now = System.currentTimeMillis()
            if (now - lastTime > 1000) {
                Toast.makeText(mContext, "再按一次,推出应用", Toast.LENGTH_LONG).show()
                lastTime = now
                return false
            }
            finish()
            exitProcess(0)
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
