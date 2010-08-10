package com.arktekk.littlebro.util

/**
 * @author Thor Åge Eldby (thoraageeldby@gmail.com)
 */

object Worker {

  def worker(f: => Unit): Unit = {
    new Thread() {
      override def run: Unit = f
    }.start
  }

}