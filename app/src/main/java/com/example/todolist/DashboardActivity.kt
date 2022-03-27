package com.example.todolist

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.DTO.ToDo

class DashboardActivity : AppCompatActivity() {

    lateinit var dbHandler: DBHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        val rvDashboard = findViewById<RecyclerView>(R.id.rv_dashboard)
        rvDashboard.layoutManager = LinearLayoutManager(this)
        val dahsboardToolBar = findViewById<Toolbar>(R.id.dashboard_toolbar)
        setSupportActionBar(dahsboardToolBar)
        title = "Dashboard"
        dbHandler = DBHandler(this)

        val button: View = findViewById(R.id.add_button)
        button.setOnClickListener {
            val dialog = AlertDialog.Builder(this)
            val view = layoutInflater.inflate(R.layout.dialog_dashboard, null)
            val toDoName = view.findViewById<EditText>(R.id.ev_todo)
            dialog.setView(view)
            dialog.setPositiveButton("Add") { _: DialogInterface, _: Int ->
                if (toDoName.text.isNotEmpty()) {
                    val toDo = ToDo()
                    toDo.name = toDoName.text.toString()
                    dbHandler.addToDo(toDo)
                }
                refreshList()
            }
            dialog.setNegativeButton("Cancel"){ _: DialogInterface, _: Int ->

            }
            dialog.show()
        }

    }

    override fun onResume() {
        refreshList()
        super.onResume()
    }

    private fun refreshList() {
        val rvDashboard = findViewById<RecyclerView>(R.id.rv_dashboard)
        rvDashboard.adapter = DashboardAdapter(this, dbHandler.getToDos())
    }

    class DashboardAdapter(val context: Context, val list: MutableList<ToDo>): RecyclerView.Adapter<DashboardAdapter.ViewHolder>() {

        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
            return ViewHolder(LayoutInflater.from(context).inflate(R.layout.rv_child_dashboard, p0, false))
        }

        override fun onBindViewHolder(holder: ViewHolder, p1: Int) {
            holder.toDoName.text = list[p1].name
        }

        override fun getItemCount(): Int {
            return list.size
        }

        class ViewHolder(v: View): RecyclerView.ViewHolder(v) {
            val toDoName: TextView = v.findViewById(R.id.tv_todo_name)

        }
    }

}