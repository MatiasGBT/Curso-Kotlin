package com.mgbt.fragmentexample

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

const val NAME_BUNDLE = "name_bundle"
const val ADRESS_BUNDLE = "adress_bundle"

class FirstFragment : Fragment() {
    private var name: String? = null
    private var adress: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            name = it.getString(NAME_BUNDLE)
            adress = it.getString(ADRESS_BUNDLE)
            Log.i("test", name.orEmpty())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FirstFragment().apply {
                arguments = Bundle().apply {
                    putString(NAME_BUNDLE, param1)
                    putString(ADRESS_BUNDLE, param2)
                }
            }
    }
}