package org.arktekk.littlebro.util

import android.widget.AdapterView.OnItemClickListener
import android.view.View
import android.widget.{ListView, AdapterView}
import android.app.AlertDialog
import android.content.DialogInterface

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

  class AmendedView(view: View) {
    def onClick(f: View => Unit) {
      view.setOnClickListener(new View.OnClickListener {
        override def onClick(v: View) = f(v)
      })
    }
  }

  implicit def view2AmendedView(view: View) = new AmendedView(view);

}