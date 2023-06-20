package com.dust.small

import com.intellij.notification.NotificationDisplayType
import com.intellij.notification.NotificationGroup
import com.intellij.notification.NotificationType
import com.intellij.notification.impl.NotificationGroupEP
import com.intellij.openapi.application.Application
import com.intellij.openapi.project.Project

object ToastUtils {

   private var mApplication:Project ?=null

    fun init(application: Project?){
        mApplication=application


    }


     fun showToast(msg:String) {
//        Application.project = e.getProject() //全局变量
        val notify = NotificationGroup("com.yatoufang.notify", NotificationDisplayType.BALLOON, true)
        notify.createNotification(msg, NotificationType.INFORMATION).notify(mApplication)
    }

}