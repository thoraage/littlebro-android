package com.arktekk.littlebro.util

/**
 * @author Thor Åge Eldby (thoraageeldby@gmail.com)
 */

object Worker {

  def worker(f: => Unit): Thread = {
    val thread = new Thread() {
      override def run: Unit = f
    }
    thread.start
    thread
  }

}