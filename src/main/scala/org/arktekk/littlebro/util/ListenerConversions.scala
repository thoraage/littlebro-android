package org.arktekk.littlebro.util

import _root_.android.widget.AdapterView.OnItemClickListener
import _root_.android.view.View
import _root_.android.widget.{ListView, AdapterView}
import _root_.android.app.AlertDialog
import _root_.android.content.DialogInterface

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

  class AmendedAlertDialogBuilder(builder: AlertDialog.Builder) {
    def onNeutralButton(text: String, f: (DialogInterface, Int) => Unit): AlertDialog.Builder =
      builder.setNeutralButton(text, new DialogInterface.OnClickListener() {
        def onClick(dialog: DialogInterface, id: Int): Unit = f
      })
  }

  implicit def alertDialogBuilder2AmendedAlertDialogBuilder(builder: AlertDialog.Builder) = new AmendedAlertDialogBuilder(builder);

}