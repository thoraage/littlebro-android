package org.arktekk.littlebro.util

import android.os.AsyncTask

/**
 * @author Thor Åge Eldby (thoraageeldby@gmail.com)
 */

object Worker {

  def worker(f: => Unit) = {
    val asyncTask = new AsyncTask[Object, Void, Object]() {
      override def doInBackground(objects: Object*): Object = { f; null }
    }.execute()
    asyncTask
  }

}