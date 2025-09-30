package com.kingcrab.notes.editor.ui.detail

import androidx.fragment.app.viewModels
import com.kingcrab.notes.editor.R
import com.kingcrab.notes.editor.databinding.FragmentDetailBinding
import com.kingcrab.notes.editor.init.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoteDetailFragment : BaseFragment<FragmentDetailBinding>() {

    private val viewmodel by viewModels<NoteDetailViewModel>()

    override var layoutId: Int
        get() = R.layout.fragment_detail
        set(value) {}

    override fun handleAction() {
        TODO("Not yet implemented")
    }

    override fun handleResponse() {
        TODO("Not yet implemented")
    }

    override fun initUI() {
        TODO("Not yet implemented")
    }
}