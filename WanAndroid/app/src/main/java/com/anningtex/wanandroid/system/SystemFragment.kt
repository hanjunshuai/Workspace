package com.anningtex.wanandroid.system

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.anningtex.wanandroid.R

/**
 * A simple [Fragment] subclass.
 */
class SystemFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_system, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() = SystemFragment()

    }
}
