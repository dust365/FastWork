package com.dust.small

import com.dust.small.callback.OnResultCallBack
import com.dust.small.manager.ScriptManager
import com.dust.small.utils.TerminalTools
import com.dust.small.utils.ToastUtil
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.ui.MessageType
import java.io.File


class FastWorkAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val project = e.getData(PlatformDataKeys.PROJECT)
        if (project != null) {
            ToastUtils.init(project)


            val file = File(project.basePath, "Scripts.xml")
            if (!file.exists()) {
                ToastUtil.make(project, MessageType.ERROR, "文件" + file.absolutePath + "不存在")
                return
            }

            //加载脚本文件
            ScriptManager.getInstance().load(project);
//            println("显示对话矿1111111111")

//            println("获取内容¥ msg")
//            var shPath="sh find_large_png.sh"

//        ToastUtils.showToast("fast worker")
//            ToastUtils.make(project, MessageType.INFO, "fast worker")

           val faster= ScriptManager.getInstance().faster
            val shPath = faster.code
            if (shPath==null){
                ToastUtil.make(project, MessageType.ERROR, "Script_Defult 节点不完整")
                return
            }

            println("${faster.name} +${faster.description} +${faster.code}")

            TerminalTools.sendCmd(project, shPath, object : OnResultCallBack {
                override fun onFail(error: String) {
                    ToastUtils.make(project, MessageType.ERROR, error)
                }

                override fun onSuccess() {
                    ToastUtils.make(project, MessageType.INFO, "fast worker start")
                }
            })
        }
    }
}