package com.kingcrab.notes.editor

import com.kingcrab.notes.editor.databinding.ActivityMainBinding
import com.kingcrab.notes.editor.init.base.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override var layoutId: Int
        get() = R.layout.activity_main
        set(value) {}
}