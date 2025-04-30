package com.anning.lib_file.detector

import com.anning.lib_file.ChooseFileInfo

/**
 * 不同版本获取文件的策略方式
 */
internal interface IChooseFilePolicy {

    fun getFileList(
        rootPath: String,
        callback: (fileList: List<ChooseFileInfo>, topInfo: ChooseFileInfo?) -> Unit,
    )

}