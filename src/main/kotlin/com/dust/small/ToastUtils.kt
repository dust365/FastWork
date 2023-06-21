package com.dust.small

import com.intellij.notification.NotificationDisplayType
import com.intellij.notification.NotificationGroup
import com.intellij.notification.NotificationType
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.application.ModalityState
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.MessageType
import com.intellij.openapi.ui.popup.Balloon
import com.intellij.openapi.ui.popup.JBPopupFactory
import com.intellij.openapi.wm.WindowManager
import com.intellij.ui.awt.RelativePoint
import javax.swing.JComponent

object ToastUtils {

   private var mApplication:Project ?=null

    fun init(application: Project?){
        mApplication=application


    }



    @Deprecated("尽量不要用")
     fun showToast(msg:String) {
//        Application.project = e.getProject() //全局变量
        val notify = NotificationGroup("com.yatoufang.notify", NotificationDisplayType.BALLOON, true)
        notify.createNotification(msg, NotificationType.INFORMATION).notify(mApplication)
    }

    private fun make(jComponent: JComponent, type: MessageType, text: String) {
        JBPopupFactory.getInstance()
            .createHtmlTextBalloonBuilder(text, type, null)
            .setFadeoutTime(7500)
            .createBalloon()
            .show(RelativePoint.getCenterOf(jComponent), Balloon.Position.above)
    }


    fun make(project: Project?, type: MessageType, text: String) {
        val showRunnable = Runnable {
            val statusBar = WindowManager.getInstance().getStatusBar(project!!)
            make(statusBar.component, type, text)
        }
        val application = ApplicationManager.getApplication()
        if (application.isDispatchThread) {
            showRunnable.run()
        } else {
            application.invokeLater(showRunnable, ModalityState.any())
        }
    }

}