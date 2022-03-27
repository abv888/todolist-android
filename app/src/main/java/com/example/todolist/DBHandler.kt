package com.example.todolist
import android.annotation.SuppressLint
import android.content.ContentValues
import android.database.sqlite.SQLiteOpenHelper
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.todolist.DTO.ToDo

class DBHandler(val context: Context): SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        val createToDoTable = "CREATE TABLE ToDo (" +
                "\t$COL_ID integer PRIMARY KEY AUTOINCREMENT," +
                "\t$COL_CREATED_AT datetime DEFAULT CURRENT_TIMESTAMP," +
                "\t$COL_NAME varchar" +
                ");"
        val createToDoItemTable = "CREATE TABLE ToDoListItem (" +
                "\t$COL_ID integer PRIMARY KEY AUTOINCREMENT," +
                "\t$COL_CREATED_AT datetime DEFAULT CURRENT_TIMESTAMP," +
                "\t$COL_TODO_ID integer," +
                "\t$COL_ITEM_NAME varchar," +
                "\t$COL_IS_COMPLETED integer" +
                ");"
        db.execSQL(createToDoTable)
        db.execSQL(createToDoItemTable)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    fun addToDo(toDo: ToDo): Boolean {
        val db: SQLiteDatabase = writableDatabase
        val cv = ContentValues()
        cv.put(COL_NAME, toDo.name)
        val result = db.insert(TABLE_TODO, null,cv)
        return  result != (-1).toLong()
    }

    @SuppressLint("Range")
    fun getToDos(): MutableList<ToDo> {
        val result: MutableList<ToDo> = ArrayList()
        val db = readableDatabase
        val queryResult = db.rawQuery("SELECT * from $TABLE_TODO",null)
        if (queryResult.moveToFirst()) {
            do {
                val todo = ToDo()
                todo.id = queryResult.getLong(queryResult.getColumnIndex(COL_ID))
                todo.name = queryResult.getString(queryResult.getColumnIndex(COL_NAME))
                result.add(todo)
            } while (queryResult.moveToNext())
        }
        queryResult.close()
        return result
    }
}