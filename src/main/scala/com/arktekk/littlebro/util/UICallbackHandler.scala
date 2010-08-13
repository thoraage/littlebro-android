package com.arktekk.littlebro.util

import _root_.android.app.{Activity, ProgressDialog}
import _root_.android.content.Context
import _root_.android.os.Handler
import _root_.android.view.Window

/**
 * @author Thor Åge Eldby (thoraageeldby@gmail.com)
 */

trait UICallbackHandler extends Activity {

  private val handler = new Handler()

  def handleUI(f: => Unit): Unit = handler.post(new Runnable() {
    def run: Unit = f
  })

  def getContext: Context

  def busy(f: => Thread): Unit = {
    val thread = f
    new Thread {
      override def run: Unit = {
        thread.join(1000)
        if (thread.isAlive) {
          handleUI {
            val dialog = ProgressDialog.show(getContext, "Busy", "Retrieving")
            new Thread() {
              override def run: Unit = {
                thread.join
                handleUI {
                  dialog.hide
                }
              }
            }.start
          }
        }
      }
    }.start
  }

}