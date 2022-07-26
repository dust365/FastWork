package com.dust.small

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.project.Project

class Application : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val project = e.getData(PlatformDataKeys.PROJECT)
        initToast(project)


        //启动主面板
        val dialog = PlaneDialog()
        dialog.isVisible = true



    }

    private fun initToast(application: Project?) {
        ToastUtils.init(application)
    }

    override fun displayTextInToolbar(): Boolean {
        return true
    }


}