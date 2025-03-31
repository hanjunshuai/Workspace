package com.anningtex.bookmanager

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.View
import androidx.appcompat.widget.AppCompatButton
import com.anningtex.bookmanager.R
import com.anningtex.server.IBookServer

class MainActivity : AppCompatActivity() {
    private var mBookManager: IBookServer? = null

    // 标志当前与服务端连接状况的布尔值，false为未连接，true为连接中
    private var mBound = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<AppCompatButton>(R.id.btnGetBook).setOnClickListener {
            if (!mBound) {
                attemptToBindService()
                return@setOnClickListener
            }
//            val bookId = mBookManager?.bookId
            Log.e("TAG", "bookId : $mBound , size : ${mBookManager?.books}")
//            mBookManager?.setBook()
        }
    }

    fun bindService(view: View) {
        attemptToBindService()
    }

    override fun onStart() {
        super.onStart()
        attemptToBindService()
    }

    /**
     *  尝试与服务端建立连接
     */
    private fun attemptToBindService() {
        var intent = Intent()
        intent.action = "com.anningtex.bookmanager"
        intent.setPackage("com.anningtex.server")
        val bindService = bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE)
        Log.e("TAG", "attemptToBindService  $bindService")
    }

    private var mServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Log.e(localClassName, "service connected")
            mBookManager = IBookServer.Stub.asInterface(service)
            mBound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            Log.e(localClassName, "service disconnected")
            mBound = false
        }

    }
}