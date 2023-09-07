package com.nanaya.adjump

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.graphics.Path
import android.graphics.Rect
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo

class ADJumpService : AccessibilityService() {
  val tag = this::class.java.name
  private val gestureCallBack = object:GestureResultCallback(){
    override fun onCompleted(gestureDescription: GestureDescription?) {
      super.onCompleted(gestureDescription)
      Log.d(tag,"onCompleted")
    }

    override fun onCancelled(gestureDescription: GestureDescription?) {
      super.onCancelled(gestureDescription)
      Log.d(tag,"onCancelled")
    }
  }
  override fun onAccessibilityEvent(event: AccessibilityEvent?) {
    if(event==null) return
    val w = rootInActiveWindow?:return
    val nodeInfoList = w.findAccessibilityNodeInfosByText("跳过")
    if (nodeInfoList.isNotEmpty()){
      val node = nodeInfoList.first()
      // node.performAction(AccessibilityNodeInfo.ACTION_CLICK)
      Log.d(this@ADJumpService::class.java.name,node.toString())
      val rect = Rect()
      node.getBoundsInScreen(rect)
      val x=rect.left
      val y=rect.top
      val path = Path()
      path.moveTo(x.toFloat(),y.toFloat())
      val gesture=GestureDescription.Builder().addStroke(GestureDescription.StrokeDescription(path,0,200)).build()
      dispatchGesture(gesture,gestureCallBack,null)
    }

  }

  override fun onInterrupt() {
  }

  override fun onServiceConnected() {
    super.onServiceConnected()
    instance=this
  }

  override fun onDestroy() {
    super.onDestroy()
    instance=null
  }

  companion object{
    var instance:ADJumpService?=null
    val isEnabled:Boolean
      get() = instance!=null
  }
}