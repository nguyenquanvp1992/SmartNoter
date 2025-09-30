package com.kingcrab.notes.editor.init.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kingcrab.notes.editor.ulti.Const

abstract class BaseBottomSheetDialogFragment<T : ViewDataBinding> : BottomSheetDialogFragment() {

    protected lateinit var binding: T
    abstract var layoutId: Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        return binding.root
    }

    protected fun back() {
        findNavController().popBackStack()
    }

    protected fun getRequestKey(): String {
        return arguments?.let {
            it.getString("requestKey") ?: Const.REQUEST_KEY_DEFAULT
        } ?: run { Const.REQUEST_KEY_DEFAULT }
    }
}