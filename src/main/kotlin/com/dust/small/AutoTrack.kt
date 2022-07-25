package com.dust.small

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys

class AutoTrack : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val project = e.getData(PlatformDataKeys.PROJECT)
        ToastUtils.init(project)
//        val edit = e.getData(PlatformDataKeys.EDITOR)
//        var text = edit?.selectionModel?.selectedText
//        if (text.isNullOrEmpty()){
//            println("获取选中的文本$text")
//        }


        //        Messages.showDialog("message","tille",option,1,Messages.getErrorIcon());
        println("显示对话矿1111111111")
//      var msg=  Messages.showInputDialog(project, "desc", "请输入内容", Messages.getErrorIcon())

//          TestDialog().isVisible=true


        val dialog = ControlDialog()
//        dialog.setSize(500,500)
//        dialog.pack()
        dialog.isVisible = true

        println("获取内容¥ msg")
    }
}