package com.example.firebasekotlin.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.firebasekotlin.R
import com.example.firebasekotlin.models.EmployeeModel

class EmpAdapter(private val empList: ArrayList<EmployeeModel>) : RecyclerView.Adapter<EmpAdapter.ViewHolder>(){

    private lateinit var mlistner: OnItemClickListner

    interface OnItemClickListner{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListner(clickListner: OnItemClickListner){
        mlistner = clickListner
    }

    class ViewHolder(itemView:View, clickListner: OnItemClickListner) : RecyclerView.ViewHolder(itemView){
        val tvEmpName : TextView = itemView.findViewById(R.id.tvEmpName)

        init {
            itemView.setOnClickListener{
                  clickListner.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmpAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.emp_list_item, parent, false)
        return ViewHolder(itemView, mlistner)
    }

    //setting the name in the cardview
    override fun onBindViewHolder(holder: EmpAdapter.ViewHolder, position: Int) {

        val currentEmp = empList[position]
        holder.tvEmpName.text = currentEmp.empName
    }

    override fun getItemCount(): Int {
        return empList.size
    }

}