package com.kingcrab.notes.editor.init.base

import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toDrawable
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.kingcrab.notes.editor.ulti.Const

abstract class BaseDialogFragment<T : ViewDataBinding> : DialogFragment() {

    protected lateinit var binding: T
    protected var percentHeight = 0f
    abstract var layoutId: Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            layoutId, container, false
        )
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val percent =
            if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                0.5f
            } else {
                0.9f
            }
        val dm = Resources.getSystem().displayMetrics
        val rect = dm.run { Rect(0, 0, widthPixels, heightPixels) }
        val percentWidth = rect.width() * percent
        val height = when (percentHeight) {
            0f -> {
                ViewGroup.LayoutParams.WRAP_CONTENT
            }

            1f -> {
                ViewGroup.LayoutParams.MATCH_PARENT
            }

            else -> {
                (percentHeight * rect.height()).toInt()
            }
        }
        dialog?.window?.setLayout(percentWidth.toInt(), height)
        dialog?.window?.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())
    }

    protected fun back() {
        try {
            findNavController().popBackStack()
        } catch (_: Exception) {
            dismiss()
        }
    }

    protected fun getRequestKey(): String {
        return arguments?.let {
            it.getString("requestKey") ?: Const.REQUEST_KEY_DEFAULT
        } ?: run { Const.REQUEST_KEY_DEFAULT }
    }
}