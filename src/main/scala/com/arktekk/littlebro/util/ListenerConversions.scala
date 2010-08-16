package com.arktekk.littlebro.util

import _root_.android.widget.AdapterView.OnItemClickListener
import _root_.android.widget.AdapterView
import _root_.android.view.View

/**
 * @author Thor Åge Eldby (thoraageeldby@gmail.com)
 */

object ListenerConversions {

  implicit def closure2OnItemClickListener(f: (AdapterView[_], View, Int, Long) => Unit): OnItemClickListener = new OnItemClickListener {
    def onItemClick(parent: AdapterView[_], view: View, position: Int, id: Long): Unit = f(parent, view, position, id)
  }

}