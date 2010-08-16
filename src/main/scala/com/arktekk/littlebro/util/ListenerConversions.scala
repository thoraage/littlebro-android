package com.arktekk.littlebro.util

import _root_.android.widget.AdapterView.OnItemClickListener
import _root_.android.view.View
import _root_.android.widget.{ListView, AdapterView}

/**
 * @author Thor Åge Eldby (thoraageeldby@gmail.com)
 */

object ListenerConversions {

  class AmendedListView(listView: ListView) {
    def onItemClick(f: (AdapterView[_], View, Int, Long) => Unit): Unit = listView.setOnItemClickListener(new OnItemClickListener {
      def onItemClick(parent: AdapterView[_], view: View, position: Int, id: Long): Unit = f(parent, view, position, id)
    })
  }
  
  implicit def listView2AmendedListView(listView: ListView) = new AmendedListView(listView)

}