package com.arktekk.littlebro

import _root_.android.app.{AlertDialog, ListActivity}
import _root_.android.content.DialogInterface
import _root_.android.content.DialogInterface.OnClickListener
import _root_.android.os.Bundle
import _root_.android.view.{KeyEvent, View}
import _root_.android.widget.{AdapterView, ArrayAdapter}
import _root_.android.widget.AdapterView.{OnItemClickListener}
import collection.mutable.Stack
import java.net.URL
import XmlHelper._

/**
 * @author Thor Åge Eldby (thoraageeldby@gmail.com)
 */
class MainActivity extends ListActivity {
  val propertyViewStack = new Stack[ListModel]
  val serverConnection = ServerConnection.getDefault

  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    propertyViewStack.push(new DomainListModel)
    populate
    getListView.setOnItemClickListener(new OnItemClickListener {
      def onItemClick(parent: AdapterView[_], view: View, position: Int, id: Long) {
        val listModel = propertyViewStack.top.onSelect(position)
        if (listModel != null) {
          propertyViewStack.push(listModel)
          populate
        }
        propertyViewStack.top match {
          case model: AttributeListModel =>
            val builder = new AlertDialog.Builder(MainActivity.this)
            val url = (propertyViewStack.top.nodes(position) \ "@href").toString
            val xml = serverConnection.loadUri(new URL(url))
            val value = ((xml \\ "span").filterClass("management") \\ "div").filterClass("value").text.trim
            builder.setTitle("Value").setMessage(value).setNeutralButton("Ok", new OnClickListener {
              override def onClick(dialog: DialogInterface, id: Int) {
                dialog.dismiss
              }
            })
            builder.create.show
          case _ =>
        }
      }
    })
  }

  override def onKeyDown(keyCode: Int, event: KeyEvent) = {
    if (keyCode == KeyEvent.KEYCODE_BACK && propertyViewStack.size > 1) {
      propertyViewStack.pop
      populate
      true
    } else {
      super.onKeyDown(keyCode, event)
    }
  }

  def populate() {
    setListAdapter(new ArrayAdapter(this, R.layout.list_item, propertyViewStack.top.names))
  }

}
