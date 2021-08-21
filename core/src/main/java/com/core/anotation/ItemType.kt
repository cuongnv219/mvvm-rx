package com.core.anotation

import androidx.annotation.IntDef

@IntDef(ItemType.HEADER, ItemType.SHIMMER, ItemType.NORMAL, ItemType.FOOTER)
@Retention(AnnotationRetention.SOURCE)
annotation class ItemType {
    companion object {
        const val HEADER = 0
        const val SHIMMER = 1
        const val NORMAL = 2
        const val FOOTER = 3
    }
}