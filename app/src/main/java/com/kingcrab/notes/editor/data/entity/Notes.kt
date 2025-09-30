package com.kingcrab.notes.editor.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Notes(
    @PrimaryKey
    val noteID: Long,
    val content: String
)