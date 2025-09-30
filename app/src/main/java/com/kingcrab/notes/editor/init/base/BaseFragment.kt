package com.kingcrab.notes.editor.init.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.kingcrab.notes.editor.ulti.extension.setFragmentResultListener
import kotlinx.coroutines.cancelChildren

abstract class BaseFragment<T : ViewDataBinding> : Fragment() {

    protected lateinit var binding: T
    abstract var layoutId: Int

    abstract fun handleAction()
    abstract fun handleResponse()
    abstract fun initUI()
    open fun requestData() {}
    open fun onCreateRequestKey(): MutableList<String> = mutableListOf()
    open fun onResultListener(requestKey: String, result: Bundle) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val requestKeys = onCreateRequestKey()
        if (requestKeys.isNotEmpty()) {
            setFragmentResultListener(requestKeys) { requestKey, bundle ->
                onResultListener(requestKey, bundle)
            }
        }
    }

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        handleAction()
        handleResponse()
        requestData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        lifecycleScope.coroutineContext.cancelChildren()
    }

    protected fun navigate(directions: NavDirections) {
        try {
            findNavController().navigate(directions)
        } catch (_: IllegalStateException) {
        } catch (_: IllegalArgumentException) {
        }
    }

    protected fun setTitle(title: String) {
        (activity as? AppCompatActivity)?.supportActionBar?.title = title
    }

    protected fun showMessage(message: String) {
        context?.let {
            Toast.makeText(it, message, Toast.LENGTH_SHORT).show()
        }
    }
}