package com.arktekk.littlebro.util

import _root_.android.os.Handler

/**
 * @author Thor Åge Eldby (thoraageeldby@gmail.com)
 */

trait UICallbackHandler {
  private val handler = new Handler()

  def handleUI(f: => Unit): Unit = handler.post(new Runnable() {
    def run: Unit = f
  })
  
}