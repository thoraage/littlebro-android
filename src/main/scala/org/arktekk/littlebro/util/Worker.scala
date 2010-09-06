package org.arktekk.littlebro.util

import android.app.ProgressDialog
import android.content.Context

/**
 * @author Thor Åge Eldby (thoraageeldby@gmail.com)
 */

object Worker {

  def asyncTask(f: => Unit): Thread = {
    val thread = new Thread() {
      override def run: Unit = f
    }
    thread.start
    thread
  }

}

trait Worker {

  def getContext: Context
  def runOnUiThread(runnable: Runnable): Unit

  def onUiThread(f: => Unit): Unit = runOnUiThread(new Runnable() {
    def run: Unit = f
  })

  def busyAsyncTask(f: => Unit): Unit = busy { Worker.asyncTask { f } }

  def busy(f: => Thread): Unit = {
    val thread = f
    new Thread {
      override def run: Unit = {
        thread.join(1000)
        if (thread.isAlive) {
          onUiThread {
            val dialog = ProgressDialog.show(getContext, "Busy", "Retrieving")
            new Thread() {
              override def run: Unit = {
                thread.join
                onUiThread {
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
