package com.newsoft.super_note.ui.base

import android.graphics.Bitmap
import com.newsoft.nscustom.ext.value.toJson
import com.newsoft.super_note.data.model.CategoryModel
import com.newsoft.super_note.data.model.NoteModel
import com.newsoft.super_note.data.model.TaskModel
import com.newsoft.super_note.data.model.base.ProfileUserModel
import com.socks.library.KLog
import kotlin.collections.ArrayList


class AppLayerManager {

    //    var data = CampainModel.Data()
    var profileUserModel = ProfileUserModel()
    var tokenFirebase = ""
    var imageUrlLoginBitmap: Bitmap? = null
    var imageUrlLogin = ""
    var imageIntroduce = ArrayList<Bitmap>()
    var totalPending = 0
    var isOpenApp = false


    var noteModels =  ArrayList<NoteModel>()

    var taskModels = ArrayList<TaskModel>()

    var categoryModels = ArrayList<CategoryModel.Item>()

    fun insertOrUpdateCategoryModels(itemCategory: CategoryModel.Item) {
        var isUpdate = false
        var index = 0
        categoryModels.forEach { categoryModel ->
            if (categoryModel.id == itemCategory.id) {
                isUpdate = true
                index = categoryModels.indexOf(categoryModel)
            }
        }

        if (isUpdate) {
            categoryModels[index] = itemCategory
        } else {
            categoryModels.add(itemCategory)
        }
    }

    fun insertOrUpdateTaskModels(itemModel: TaskModel) {
        KLog.json(toJson(itemModel))
        var isUpdate = false
        var index = 0
        taskModels.forEach { taskModel ->
            if (taskModel.id == itemModel.id) {
                isUpdate = true
                index = taskModels.indexOf(taskModel)
            }
        }

            taskModels[index] = itemModel
            if (isUpdate) {
        } else {
            taskModels.add(itemModel)
        }

    }

    fun insertOrUpdateNoteModels(itemModel: NoteModel) {
        KLog.json(toJson(itemModel))
        var isUpdate = false
        var index = 0

        noteModels.forEach { noteModel ->
            if (noteModel.id == itemModel.id) {
                isUpdate = true
                index = noteModels.indexOf(noteModel)
            }
        }

        if (isUpdate) {
            noteModels[index] = itemModel
        } else {
            noteModels.add(itemModel)
        }

    }

    fun genIdTask(): Int {
        var idTask = 0
        if (categoryModels.size > 0) {
            idTask = categoryModels[categoryModels.size - 1].idTask + 1
        } else {
            idTask = 1
        }
        return idTask
    }

    fun genIdNote(): Int {
        var idNote = 0
        if (categoryModels.size > 0) {
            idNote = categoryModels[categoryModels.size - 1].idNote + 1
        } else {
            idNote = 1
        }
        return idNote
    }

    companion object {
        var sInstance: AppLayerManager? = null

        @Synchronized
        fun getInstance(): AppLayerManager {
            if (sInstance == null)
                sInstance = AppLayerManager()

            return sInstance as AppLayerManager
        }
    }

    constructor() {
    }


}