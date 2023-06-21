package com.dust.small.utils

import com.dust.small.callback.OnResultCallBack
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowManager
//import com.intellij.openapi.wm.impl.ToolWindowImpl
import com.intellij.terminal.JBTerminalPanel
import java.awt.Toolkit
import javax.swing.JComponent

object TerminalTools {
    private const val ID_TERMINAL = "Terminal"
    fun sendCmd(project: Project, cmd: String, callBack: OnResultCallBack?) {
        val terminal: ToolWindow? = getTerminalToolWindow(project)
        if (terminal!!.isVisible) {
            sendCmd(cmd, callBack, terminal)
        } else {
            terminal.show { sendCmd(cmd, callBack, terminal) }
        }
    }

    private fun sendCmd(cmd: String, callBack: OnResultCallBack?, terminal: ToolWindow?) {
        var currentComponent = terminal!!.component
        while (true) {
            if (currentComponent is JBTerminalPanel) {
                val terminalOutputStream = currentComponent.terminalOutputStream
                if (terminalOutputStream == null) {
                    callBack?.onFail("由于终端对象首次创建，请重试！")
                } else {
                    terminalOutputStream.sendString(String.format("\n\r %s \n\r", cmd))
                    callBack?.onSuccess()
                }
                break
            }
            val componentCount = currentComponent.componentCount
            if (componentCount <= 0) {
                callBack?.onFail("命令执行失败，请在终端手动执行！")
                break
            }
            currentComponent = currentComponent.getComponent(0) as JComponent
        }
    }

    fun showTerminal(project: Project) {
        val terminal = getTerminalToolWindow(project)
        if (!terminal!!.isVisible) {
            terminal.show(null)
        }
        val suitHeight = Toolkit.getDefaultToolkit().screenSize.height / 5 * 2
        val currentHeight = terminal.component.size.height
//        terminal.stretchHeight(suitHeight - currentHeight)
//        terminal is
    }

    private fun getTerminalToolWindow(project: Project): ToolWindow? {
        return ToolWindowManager.getInstance(project).getToolWindow(ID_TERMINAL)
    }
}