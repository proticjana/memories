package jpro.memories.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import jpro.memories.models.MemoryModel
import java.sql.SQLException

class DatabaseHandler(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "MemoriesDatabase"
        private const val TABLE_MEMORIES = "MemoriesTable"

        // Memories table columns
        private const val KEY_ID = "_id"
        private const val KEY_NAME = "name"
        private const val KEY_DESCRIPTION = "description"
        private const val KEY_DATE = "date"
        private const val KEY_LOCATION = "location"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createMemoriesTable = ("CREATE TABLE $TABLE_MEMORIES "
                + "($KEY_ID INTEGER PRIMARY KEY,"
                + "$KEY_NAME TEXT,"
                + "$KEY_DESCRIPTION TEXT,"
                + "$KEY_DATE TEXT,"
                + "$KEY_LOCATION TEXT)")
        db?.execSQL(createMemoriesTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_MEMORIES")
        onCreate(db)
    }

    fun addMemory(memory: MemoryModel): Long {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(KEY_NAME, memory.name)
        contentValues.put(KEY_DESCRIPTION, memory.description)
        contentValues.put(KEY_DATE, memory.date)
        contentValues.put(KEY_LOCATION, memory.location)

        val result = db.insert(TABLE_MEMORIES, null, contentValues)

        db.close()
        return result
    }

    fun updateMemory(memory: MemoryModel): Int {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(KEY_NAME, memory.name)
        contentValues.put(KEY_DESCRIPTION, memory.description)
        contentValues.put(KEY_DATE, memory.date)
        contentValues.put(KEY_LOCATION, memory.location)

        val success = db.update(TABLE_MEMORIES, contentValues, KEY_ID + "=" + memory.id, null)

        db.close()
        return success
    }

    fun deleteMemory(memory: MemoryModel): Int {
        val db = this.writableDatabase

        val success = db.delete(TABLE_MEMORIES, KEY_ID + "=" + memory.id, null)

        db.close()
        return success
    }

    fun getMemoriesList(): ArrayList<MemoryModel> {
        val memories = ArrayList<MemoryModel>()
        val selectQuery = "SELECT * FROM $TABLE_MEMORIES"
        val db = this.readableDatabase

        try {
            val cursor: Cursor = db.rawQuery(selectQuery, null)

            if (cursor.moveToFirst()) {
                do {
                    val memory = MemoryModel(
                        cursor.getInt(cursor.getColumnIndex(KEY_ID)),
                        cursor.getString(cursor.getColumnIndex(KEY_NAME)),
                        cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION)),
                        cursor.getString(cursor.getColumnIndex(KEY_DATE)),
                        cursor.getString(cursor.getColumnIndex(KEY_LOCATION)),
                    )
                    memories.add(memory)
                } while (cursor.moveToNext())
            }
            cursor.close()
        } catch (e: SQLException) {
            return ArrayList()
        }

        return memories
    }

    fun queryMemoriesList(query: String?): ArrayList<MemoryModel> {
        if (query.isNullOrEmpty()) {
            return getMemoriesList()
        }

        val memories = ArrayList<MemoryModel>()
        val selectQuery = "SELECT * FROM $TABLE_MEMORIES WHERE " +
                "$KEY_NAME like \"%$query%\" OR " +
                "$KEY_DESCRIPTION like \"%$query%\" OR " +
                "$KEY_DATE like \"%$query%\" OR " +
                "$KEY_LOCATION like \"%$query%\""
        val db = this.readableDatabase

        try {
            val cursor: Cursor = db.rawQuery(selectQuery, null)

            if (cursor.moveToFirst()) {
                do {
                    val memory = MemoryModel(
                        cursor.getInt(cursor.getColumnIndex(KEY_ID)),
                        cursor.getString(cursor.getColumnIndex(KEY_NAME)),
                        cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION)),
                        cursor.getString(cursor.getColumnIndex(KEY_DATE)),
                        cursor.getString(cursor.getColumnIndex(KEY_LOCATION)),
                    )
                    memories.add(memory)
                } while (cursor.moveToNext())
            }
            cursor.close()
        } catch (e: SQLException) {
            return ArrayList()
        }

        return memories
    }
}