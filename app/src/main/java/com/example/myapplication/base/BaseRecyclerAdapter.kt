package com.example.myapplication.base

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.BaseModel
import com.example.domain.model.FilmDomainModel
import java.util.Locale

open class BaseRecyclerAdapter(
    delegates: List<AdapterDelegate>,
) : ListAdapter<BaseModel, BaseViewHolder>(BaseDiffUtil()) {

    private val delegateManager = AdapterDelegateManager()

    init {
        delegates.forEach {
            delegateManager.addDelegate(it)
        }
        //delegateManager.addDelegate(EmptySearchDelegate(), EmptyItemHolderDelegate(clickToAction), LoaderPostDelegate(), EmptyListHolderDelegate()
        //) //TODO implement these delegates and remove comments after that
    }

    private var recyclerView: RecyclerView? = null

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder =
        delegateManager.getDelegate(viewType).onCreateViewHolder(parent)

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(
        holder: BaseViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        Log.d("currentViewHolder", holder.javaClass.name.toString())
        if (payloads.isEmpty())
            super.onBindViewHolder(holder, position, payloads)
        else
            holder.bindPayload(getItem(position), holder, payloads[0] as MutableList<Any>)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(getItem(position), holder)
    }

    override fun getItemViewType(position: Int): Int =
        delegateManager.getItemViewType(getItem(position))

    fun getItemByPosition(position: Int) =
        if (position != -1 && position < currentList.size) getItem(position) else null

    fun getItems() = currentList

    override fun getItemCount() = currentList.size

    override fun submitList(list: MutableList<BaseModel>?) {
        synchronized(currentList) {
            super.submitList(list) {
                Handler(Looper.getMainLooper()).post { recyclerView?.invalidateItemDecorations() }
            }
        }
    }

    fun submitItem(item: BaseModel) {
        synchronized(currentList) {
            super.submitList(mutableListOf<BaseModel>().apply { add(item) })
        }
    }

    override fun submitList(list: MutableList<BaseModel>?, commitCallback: Runnable?) {
        synchronized(currentList) {
            super.submitList(list, commitCallback)
        }
    }

    override fun onCurrentListChanged(
        previousList: MutableList<BaseModel>,
        currentList: MutableList<BaseModel>
    ) {
        super.onCurrentListChanged(previousList, currentList)
    }

    val customFilter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredList = mutableListOf<BaseModel>()
            if (constraint == null || constraint.isEmpty()) {
                filteredList.addAll(currentList)
            } else {
                for (item in currentList) {
                    if ((item as FilmDomainModel).title?.lowercase(Locale.ROOT)?.startsWith(
                            constraint.toString()
                                .lowercase(Locale.ROOT)
                        ) == true
                    ) {
                        filteredList.add(item)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(constraint: CharSequence?, filterResults: FilterResults?) {
            submitList(filterResults?.values as MutableList<BaseModel>)
        }
    }
}