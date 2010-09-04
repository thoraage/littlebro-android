package org.arktekk.littlebro.util

import android.app.{Activity, ProgressDialog}
import android.content.Context
import android.os.AsyncTask
import java.util.concurrent.TimeUnit

/**
 * @author Thor Åge Eldby (thoraageeldby@gmail.com)
 */

trait UICallbackHandler extends Activity {

  def runOnUiThread(f: => Unit): Unit = runOnUiThread(new Runnable() {
    def run: Unit = f
  })

  def getContext: Context

  def busyWorker(f: => Unit): Unit = busy { Worker.worker { f } }

  def busy(f: => AsyncTask[Object, Void, Object]): Unit = {
    val asyncTask = f
    Worker.worker {
      asyncTask.get(1, TimeUnit.SECONDS)
      if (asyncTask.getStatus != AsyncTask.Status.FINISHED) {
        runOnUiThread {
          val dialog = ProgressDialog.show(getContext, "Busy", "Retrieving")
          asyncTask.get
          runOnUiThread {
            dialog.hide
          }
        }
      }
    }
  }

}