package com.example.skinsync.helper

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.TextView
import com.example.skinsync.R
import android.widget.Toast

class MultiSelectAdapter(
    context: Context,
    private val resource: Int,
    private val items: List<String>
) : ArrayAdapter<String>(context, resource, items) {

    private val selectedItems = mutableSetOf<String>()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createViewFromResource(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createViewFromResource(position, convertView, parent)
    }

    private fun createViewFromResource(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View = convertView ?: LayoutInflater.from(context).inflate(resource, parent, false)
        val textView = view.findViewById<TextView>(R.id.textView)
        val checkBox = view.findViewById<CheckBox>(R.id.checkBox)

        textView.text = items[position]
        checkBox.isChecked = selectedItems.contains(items[position])

        view.setOnClickListener {
            checkBox.isChecked = !checkBox.isChecked
            if (checkBox.isChecked) {
                selectedItems.add(items[position])
            } else {
                selectedItems.remove(items[position])
            }
            notifyDataSetChanged()
        }

        checkBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                selectedItems.add(items[position])
            } else {
                selectedItems.remove(items[position])
            }
            Toast.makeText(context, "Selected items: $selectedItems", Toast.LENGTH_SHORT).show()
        }

        return view
    }

    fun getSelectedItems(): Set<String> {
        return selectedItems
    }
}