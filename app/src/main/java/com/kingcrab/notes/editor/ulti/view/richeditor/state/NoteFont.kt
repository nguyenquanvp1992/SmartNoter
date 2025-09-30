package com.kingcrab.notes.editor.ulti.view.richeditor.state

import android.os.Parcel
import com.kingcrab.notes.editor.init.base.ItemBaseAdapter

open class NoteFont : ItemBaseAdapter {
    val fontName: String?
    val fontResId: Int

    protected constructor(`in`: Parcel) {
        this.fontName = `in`.readString()
        fontResId = `in`.readInt()
    }

    override fun areItemsTheSame(oldItem: ItemBaseAdapter): Boolean {
        return false
    }

    override fun areContentsTheSame(oldItem: ItemBaseAdapter): Boolean {
        return false
    }

    companion object {
        val list: MutableList<NoteFont> = ArrayList<NoteFont>()

        @JvmStatic
        fun getFromName(name: String?): NoteFont {
            for (font in list) {
                if (font.fontName.equals(name, ignoreCase = true)) {
                    return font
                }
            }
            return list[0]
        }
    }
}