package com.arktekk.littlebro.util

import _root_.android.app.{Activity, ProgressDialog}
import _root_.android.content.Context

/**
 * @author Thor Åge Eldby (thoraageeldby@gmail.com)
 */

trait UICallbackHandler extends Activity {

  def runOnUiThread(f: => Unit): Unit = runOnUiThread(new Runnable() {
    def run: Unit = f
  })

  def getContext: Context

  def busy(f: => Thread): Unit = {
    val thread = f
    new Thread {
      override def run: Unit = {
        thread.join(1000)
        if (thread.isAlive) {
          runOnUiThread {
            val dialog = ProgressDialog.show(getContext, "Busy", "Retrieving")
            new Thread() {
              override def run: Unit = {
                thread.join
                runOnUiThread {
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