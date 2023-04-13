package com.example.myapplication.base

import android.view.ViewGroup
import com.example.domain.BaseModel

interface AdapterDelegate {
    fun onCreateViewHolder(parent: ViewGroup): BaseViewHolder

    fun isValidType(baseModel: BaseModel): Boolean
}