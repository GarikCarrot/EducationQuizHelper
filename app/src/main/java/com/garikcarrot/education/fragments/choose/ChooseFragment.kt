package com.garikcarrot.education.fragments.choose

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.garikcarrot.education.R

abstract class ChooseFragment : Fragment() {
    private var adapter: ChooseAdapter? = null
    private var recycler: RecyclerView? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.choose_layout, container, false)
        adapter = ChooseAdapter(getListener())
        recycler = view?.findViewById(R.id.recycler)
        recycler?.layoutManager = LinearLayoutManager(activity)
        recycler?.adapter = adapter
        load()
        return view
    }

    abstract fun load()

    abstract fun getListener(): (String) -> Unit

    fun setData(data: List<String>) {
        adapter?.data = data
        adapter?.notifyDataSetChanged()
    }
}