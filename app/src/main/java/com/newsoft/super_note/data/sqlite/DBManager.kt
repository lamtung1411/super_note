package com.newsoft.super_note.data.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.newsoft.super_note.data.model.CategoryModel
import com.newsoft.super_note.data.model.NoteModel
import com.newsoft.super_note.data.model.TaskModel

import java.util.Arrays
import java.util.Calendar


class DBManager(private val context: Context) : SQLiteOpenHelper(
    context, DATABASE_NAME, null, VERSION
) {
    companion object {
        private var instance: DBManager? = null
        private const val DATABASE_NAME = "databaseSuperNote"
        const val TABLE_NOTE = "note"
        const val TABLE_TASK = "task"
        const val TABLE_TASK_DETAIL = "taskDetail"
        const val TABLE_CATEGORY = "category"
        private const val ID = "id"
        private const val THEME = "theme"
        private const val COLORBAR = "colorBar"
        private const val COLORBACKGROUND = "colorBackGround"
        private const val COLORTEXT = "colortext"
        private const val TITLE = "title"
        private const val CONTENT = "content"

        // noteItem
        private const val IDNOTE = "idnote"

        // TaskItem
        private const val IDTASK = "idtask"
        private const val IMAGE = "image"
        private const val ICON = "icon"
        private const val COLOR = "color"
        private const val COLORBUTTON = "colorButton"
        private const val TICK = "tick"
        private const val BIN = "bin"
        private const val PIN = "pin"
        private const val LOCK = "lock"
        private const val TIMECOUNTPIN = "timeCountPin"

        // CategoryItem
        private const val TITLEDETAIL = "titledetail"
        private const val VERSION = 1

        @Synchronized
        fun getInstance(): DBManager {
            checkNotNull(instance) {
                DBManager::class.java.simpleName +
                        " is not initialized, call initializeInstance(..) method first."
            }
            return instance!!
        }

        @Synchronized
        fun initializeInstance(context: Context) {
            if (instance == null) instance = DBManager(context)
        }
    }

    private val TAG = "DBManager"
    private val SQLQuery_Note = ("CREATE TABLE " + TABLE_NOTE + " ("
            + ID + " integer primary key, "
            + TITLE + " TEXT, "
            + CONTENT + " TEXT, "
            + THEME + " INTERGER, "
            + COLORTEXT + " INTERGER, "
            + COLORBAR + " INTERGER, "
            + COLORBACKGROUND + " INTERGER)")

    private val SQLQuery_Task = ("CREATE TABLE " + TABLE_TASK + " ("
            + ID + " integer primary key, "
            + TITLE + " TEXT, "
            + THEME + " INTERGER, "
            + COLORBAR + " INTERGER,"
            + COLORBACKGROUND + " INTERGER)")

    private val SQLQuery_Task_Detail = ("CREATE TABLE " + TABLE_TASK_DETAIL + " ("
            + ID + " integer primary key, "
            + IDTASK + " INTERGER,"
            + TICK + " TEXT, "
            + CONTENT + " TEXT)")

    private val SQLQuery_Category = ("CREATE TABLE " + TABLE_CATEGORY + " ("
            + ID + " integer primary key, "
            + TITLE + " TEXT, "
            + IDNOTE + " INTERGER,"
            + IDTASK + " INTERGER,"
            + BIN + " TEXT,"
            + LOCK + " TEXT,"
            + IMAGE + " INTERGER,"
            + ICON + " INTERGER,"
            + COLOR + " INTERGER,"
            + COLORBUTTON + " INTERGER,"
            + PIN + " INTERGER,"
            + TIMECOUNTPIN + " TEXT)")


    init {
        Log.d(TAG, "DBManager: ")
    }

    override fun onCreate(sqLiteDatabase: SQLiteDatabase) {
        sqLiteDatabase.execSQL(SQLQuery_Task)
        sqLiteDatabase.execSQL(SQLQuery_Task_Detail)
        sqLiteDatabase.execSQL(SQLQuery_Category)
        sqLiteDatabase.execSQL(SQLQuery_Note)
        Log.d(TAG, "onCreate: ")
    }

    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase, i: Int, i1: Int) {
        Log.d(TAG, "onUpgrade: ")
    }

    fun delete(table: String?, id: Int): Int {
        val db = this.writableDatabase
        return db.delete(table, ID + "=?", arrayOf(id.toString()))
    }

    fun deleteTable(table: String?) {
        Log.e("deleteTable ", table!!)
        val db = this.writableDatabase
        db.delete(table, null, null)
    }

    fun deleteBinItems(table: String?, id: Int): Int {
        val db = this.writableDatabase
        return db.delete(table, ID + "=?", arrayOf(id.toString()))
    }


    /**
     * Task
     */
    fun addTask(taskModel: TaskModel) {
        val noteModelDb = getTaskItem(taskModel.id)
        if (noteModelDb.id != 0) {
            //update
            updateTask(taskModel)
        } else {
            // insert
            insertTask(taskModel)
        }
    }

    fun insertTask(taskModel: TaskModel) {
        val contentValues = putTask(taskModel)
        val db = this.writableDatabase
        db.insert(TABLE_TASK, null, contentValues)
        db.close()
    }

    fun updateTask(taskModel: TaskModel) {
        val contentValues = putTask(taskModel)
        val db = this.writableDatabase
        db.update(TABLE_TASK, contentValues, ID + "=?", arrayOf(taskModel.id.toString()))
        db.close()
    }

    private fun putTask(taskModel: TaskModel): ContentValues {
        val values = ContentValues()
        values.put(ID, taskModel.id)
        values.put(TITLE, taskModel.title)
        values.put(THEME, taskModel.theme)
        values.put(COLORBAR, taskModel.colorBar)
        values.put(COLORBACKGROUND, taskModel.colorBackGround)
        return values
    }

    private fun getTaskItemCursor(cursor: Cursor): TaskModel {
        val taskModel = TaskModel()
        taskModel.id = cursor.getInt(0)
        taskModel.title = cursor.getString(1)
        taskModel.theme = cursor.getInt(2)
        taskModel.colorBar = cursor.getInt(3)
        taskModel.colorBackGround = cursor.getInt(4)
        return taskModel
    }

    val taskItemAll: List<TaskModel>
        get() {
            val taskItem: MutableList<TaskModel> = ArrayList()
            val db = this.writableDatabase
            val cursor = db.rawQuery("SELECT * FROM " + TABLE_TASK, null)
            if (cursor.moveToFirst()) {
                do {
                    taskItem.add(getTaskItemCursor(cursor))
                } while (cursor.moveToNext())
            }
            db.close()
            return taskItem
        }

    fun getTaskItem(id: Int): TaskModel {
        var taskItem = TaskModel()
        val db = this.writableDatabase
        val cursor = db.rawQuery("SELECT * FROM " + TABLE_TASK + " WHERE " + ID + " = " + id, null)
        if (cursor.moveToFirst()) {
            do {
                taskItem = getTaskItemCursor(cursor)
            } while (cursor.moveToNext())
        }
        db.close()
        val detailTaskItems = getTaskDetailItems(taskItem.id)
        taskItem.items = detailTaskItems
        return taskItem
    }

    /**
     * TaskDetail
     */
    fun addTaskDetail(taskDetailModel: TaskModel.Item) {
        val taskDetailModelDb = getTaskDetail(taskDetailModel.id)
        if (taskDetailModelDb.id != 0) {
            //update
            updateTaskDetail(taskDetailModel)
        } else {
            // insert
            insertTaskDetail(taskDetailModel)
        }
    }

    fun insertTaskDetail(taskDetailModel: TaskModel.Item) {
        val contentValues = putTaskDetail(taskDetailModel)
        val db = this.writableDatabase
        db.insert(TABLE_TASK_DETAIL, null, contentValues)
        db.close()
    }

    fun updateTaskDetail(taskDetailModel: TaskModel.Item) {
        val contentValues = putTaskDetail(taskDetailModel)
        val db = this.writableDatabase
        db.update(
            TABLE_TASK_DETAIL,
            contentValues,
            ID + "=?",
            arrayOf(taskDetailModel.id.toString())
        )
        db.close()
    }

    private fun putTaskDetail(taskDetailModel: TaskModel.Item): ContentValues {
        val values = ContentValues()
        values.put(ID, taskDetailModel.id)
        values.put(IDTASK, taskDetailModel.idTask)
        values.put(TICK, taskDetailModel.tick.toString())
        values.put(CONTENT, taskDetailModel.content)
        return values
    }

    private fun getTaskDetail(cursor: Cursor): TaskModel.Item {
        val taskDetailModel = TaskModel.Item()
        taskDetailModel.id = cursor.getInt(0)
        taskDetailModel.idTask = cursor.getInt(1)
        taskDetailModel.tick = java.lang.Boolean.parseBoolean(cursor.getString(2))
        taskDetailModel.content = cursor.getString(3)
        return taskDetailModel
    }

    val taskDetailItemAll: List<TaskModel.Item>
        get() {
            val taskDetailItem: MutableList<TaskModel.Item> = ArrayList()
            val db = this.writableDatabase
            val cursor = db.rawQuery("SELECT * FROM " + TABLE_TASK_DETAIL, null)
            if (cursor.moveToFirst()) {
                do {
                    taskDetailItem.add(getTaskDetail(cursor))
                } while (cursor.moveToNext())
            }
            db.close()
            return taskDetailItem
        }

    fun getTaskDetail(id: Int): TaskModel.Item {
        var taskDetailModel = TaskModel.Item()
        val db = this.writableDatabase
        val cursor =
            db.rawQuery("SELECT * FROM " + TABLE_TASK_DETAIL + " WHERE " + ID + " = " + id, null)
        if (cursor.moveToFirst()) {
            taskDetailModel = getTaskDetail(cursor)
        }
        db.close()
        return taskDetailModel
    }

    fun getTaskDetailItems(id: Int): ArrayList<TaskModel.Item> {
        val taskDetailItem = ArrayList<TaskModel.Item>()
        val db = this.writableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM " + TABLE_TASK_DETAIL + " WHERE " + IDTASK + " = " + id,
            null
        )
        if (cursor.moveToFirst()) {
            do {
                taskDetailItem.add(getTaskDetail(cursor))
            } while (cursor.moveToNext())
        }
        db.close()
        return taskDetailItem
    }

    /**
     * Note
     */
    fun addNote(noteModel: NoteModel) {
        val noteModelDb = getNote(noteModel.id)
        if (noteModelDb.id != 0) {
            //update
            updateNote(noteModel)
        } else {
            // insert
            insertNote(noteModel)
        }
    }

    private fun putNote(noteModel: NoteModel): ContentValues {
        val values = ContentValues()
        values.put(ID, noteModel.id)
        values.put(TITLE, noteModel.title)
        values.put(CONTENT, noteModel.content)
        values.put(THEME, noteModel.theme)
        values.put(COLORTEXT, noteModel.colorText)
        values.put(COLORBAR, noteModel.colorBar)
        values.put(COLORBACKGROUND, noteModel.colorBackGround)
        return values
    }

    fun updateNote(noteModel: NoteModel) {
        val contentValues = putNote(noteModel)
        val db = this.writableDatabase
        db.update(TABLE_NOTE, contentValues, ID + "=?", arrayOf(noteModel.id.toString()))
        db.close()
    }

    fun insertNote(noteModel: NoteModel) {
        val contentValues = putNote(noteModel)
        val db = this.writableDatabase
        db.insert(TABLE_NOTE, null, contentValues)
        db.close()
    }

    private fun getNoteItem(cursor: Cursor): NoteModel {
        val noteModel = NoteModel()
        noteModel.id = cursor.getInt(0)
        noteModel.title = cursor.getString(1)
        noteModel.content = cursor.getString(2)
        noteModel.theme = cursor.getInt(3)
        noteModel.colorText = cursor.getInt(4)
        noteModel.colorBar = cursor.getInt(5)
        noteModel.colorBackGround = cursor.getInt(6)
        return noteModel
    }

    val noteItemAll: List<NoteModel>
        get() {
            val noteItems: MutableList<NoteModel> = ArrayList()
            val db = this.writableDatabase
            val cursor = db.rawQuery("SELECT * FROM " + TABLE_NOTE, null)
            if (cursor.moveToFirst()) {
                do {
                    noteItems.add(getNoteItem(cursor))
                } while (cursor.moveToNext())
            }
            db.close()
            return noteItems
        }

    fun getNote(id: Int): NoteModel {
        var noteItemModel = NoteModel()
        val db = this.writableDatabase
        val cursor = db.rawQuery("SELECT * FROM " + TABLE_NOTE + " WHERE " + ID + " = " + id, null)
        if (cursor.moveToFirst()) {
            noteItemModel = getNoteItem(cursor)
        }
        db.close()
        return noteItemModel
    }

    /**
     * Category
     */
    fun addCategory(categoryModel: CategoryModel.Item) {
        val categoryModelDb = getCategory(categoryModel.id)
        if (categoryModelDb.id != 0) {
            //update
            updateCategory(categoryModel)
        } else {
            // insert
            insertCategory(categoryModel)
        }
    }

    fun insertCategory(categoryModel: CategoryModel.Item) {
        val contentValues = putCategory(categoryModel)
        val db = this.writableDatabase
        db.insert(TABLE_CATEGORY, null, contentValues)
        db.close()
    }

    fun updateCategory(categoryModel: CategoryModel.Item) {
        val contentValues = putCategory(categoryModel)
        val db = this.writableDatabase
        db.update(TABLE_CATEGORY, contentValues, ID + "=?", arrayOf(categoryModel.id.toString()))
        db.close()
    }

    //TODO cập nhật lại thuộc tính bin nếu true đang được sử dụng, false chuyển vào thùng rác
    fun updateCategoryBinDelete(categoryModel: CategoryModel.Item, bin: Boolean) {
        categoryModel.bin = bin
        val contentValues = putCategory(categoryModel)
        val db = this.writableDatabase
        db.update(TABLE_CATEGORY, contentValues, ID + "=?", arrayOf(categoryModel.id.toString()))
        db.close()
    }


    private fun putCategory(categoryModel: CategoryModel.Item): ContentValues {
        val values = ContentValues()
        values.put(ID, categoryModel.id)
        values.put(TITLE, categoryModel.title)
        values.put(IDNOTE, categoryModel.idNote)
        values.put(IDTASK, categoryModel.idTask)
        values.put(BIN, categoryModel.bin.toString())
        values.put(LOCK, categoryModel.lock.toString())
        values.put(IMAGE, categoryModel.image)
        values.put(ICON, categoryModel.icon)
        values.put(COLOR, categoryModel.color)
        values.put(COLORBUTTON, categoryModel.colorButton)
        values.put(PIN, categoryModel.pin)
        values.put(TIMECOUNTPIN, categoryModel.timeCountPin)
        return values
    }

    private fun getCategoryItem(cursor: Cursor): CategoryModel.Item {
        val categoryModel = CategoryModel.Item()
        categoryModel.id = cursor.getInt(0)
        categoryModel.title = cursor.getString(1)
        categoryModel.idNote = cursor.getInt(2)
        categoryModel.idTask = cursor.getInt(3)
        categoryModel.bin = java.lang.Boolean.parseBoolean(cursor.getString(4))
        categoryModel.lock = java.lang.Boolean.parseBoolean(cursor.getString(5))
        categoryModel.image = cursor.getInt(6)
        categoryModel.icon = cursor.getInt(7)
        categoryModel.color = cursor.getInt(8)
        categoryModel.colorButton = cursor.getInt(9)
        categoryModel.pin = java.lang.Boolean.parseBoolean(cursor.getString(10))
        categoryModel.timeCountPin = cursor.getLong(11)
        return categoryModel
    }

    val categoryItemAll: List<CategoryModel.Item>
        get() {
            val categoryItems: MutableList<CategoryModel.Item> = ArrayList()
            val db = this.writableDatabase
            val cursor = db.rawQuery("SELECT * FROM " + TABLE_CATEGORY, null)
            if (cursor.moveToFirst()) {
                do {
                    categoryItems.add(getCategoryItem(cursor))
                } while (cursor.moveToNext())
            }
            db.close()
            return categoryItems
        }

    fun getCategory(id: Int): CategoryModel.Item {
        var categoryModel = CategoryModel.Item()
        val db = this.writableDatabase
        val cursor =
            db.rawQuery("SELECT * FROM $TABLE_CATEGORY WHERE $ID = $id", null)
        if (cursor.moveToFirst()) {
            categoryModel = getCategoryItem(cursor)
        }
        db.close()
        return categoryModel
    }

    fun getCategoryItemsSearch(title: String = ""): ArrayList<CategoryModel.Item> {
        val categoryBinItems = ArrayList<CategoryModel.Item>()
        val db = this.writableDatabase
        val query =
            if (title.isEmpty()) "SELECT * FROM $TABLE_CATEGORY"
            else "SELECT * FROM $TABLE_CATEGORY WHERE $TITLE LIKE '%$title%'"
        val cursor = db.rawQuery(
            query,
            null
        )
        if (cursor.moveToFirst()) {
            do {
                categoryBinItems.add(getCategoryItem(cursor))
            } while (cursor.moveToNext())
        }
        db.close()
        return categoryBinItems
    }


    fun getCategoryItemsIsBin(bin: Boolean): ArrayList<CategoryModel.Item> {
        val categoryBinItems = ArrayList<CategoryModel.Item>()
        val db = this.writableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM $TABLE_CATEGORY WHERE $BIN = '$bin'",
            null
        )
        if (cursor.moveToFirst()) {
            do {
                categoryBinItems.add(getCategoryItem(cursor))
            } while (cursor.moveToNext())
        }
        db.close()

        categoryBinItems.sortBy { itemCategory ->
            itemCategory.pin
        }

        categoryBinItems.sortByDescending { itemCategory ->
            itemCategory.timeCountPin
        }



        return categoryBinItems
    }

}