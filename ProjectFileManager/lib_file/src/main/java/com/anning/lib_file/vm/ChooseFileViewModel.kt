package com.anning.lib_file.vm

import android.os.Environment
import androidx.lifecycle.ViewModel
import com.anning.lib_file.ChooseFileInfo
import com.anning.lib_file.adapter.FileListAdapter
import com.anning.lib_file.adapter.FileNavAdapter

class ChooseFileViewModel : ViewModel() {

    val mNavPathList = arrayListOf<ChooseFileInfo>()
    var mNavAdapter: FileNavAdapter? = null

    val mFileList = arrayListOf<ChooseFileInfo>()
    var mFileListAdapter: FileListAdapter? = null


    //根目录
    val rootPath = Environment.getExternalStorageDirectory().absolutePath

    var rootChoosePos = 0  //根目录文档选中的索引

    //当前选择的路径
    var mCurPath = Environment.getExternalStorageDirectory().absolutePath
}