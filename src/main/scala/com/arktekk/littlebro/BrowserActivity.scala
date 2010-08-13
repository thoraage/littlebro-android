package com.arktekk.littlebro

import _root_.android.app.{AlertDialog, ListActivity}
import _root_.android.content.DialogInterface.OnClickListener
import _root_.android.content.DialogInterface
import _root_.android.os.Bundle
import _root_.android.view.{KeyEvent, View}
import _root_.android.widget.{AdapterView, ArrayAdapter}
import _root_.android.widget.AdapterView.{OnItemClickListener}
import collection.mutable.Stack
import util.UICallbackHandler
import java.net.URI
import util.Worker._
import XmlHelper._

/**
 * @author Thor Åge Eldby (thoraageeldby@gmail.com)
 */
class BrowserActivity extends ListActivity with AndroidCredentialsProvider with UICallbackHandler {
  val propertyViewStack = new Stack[ListModel]

  def getContext = this

  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    val domainUri = new URI(getIntent.getDataString)
    busy {
      worker {
        val domainList = new DomainListModel(ServerConnection(BrowserActivity.this, domainUri).get)
        handleUI {
          propertyViewStack.push(domainList)
          populate
        }
      }
    }
    getListView.setOnItemClickListener(new OnItemClickListener {
      def onItemClick(parent: AdapterView[_], view: View, position: Int, id: Long) {
        val listModel = propertyViewStack.top.onSelect(position)
        if (listModel != null) {
          propertyViewStack.push(listModel)
          populate
        }
        propertyViewStack.top match {
          case model: AttributeListModel =>
            val builder = new AlertDialog.Builder(BrowserActivity.this)
            val href = (model.nodes(position) \ "@href").toString
            val xml = model.serverConnection.withPath(href).get
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
    setListAdapter(new ArrayAdapter(this, R.layout.browser, propertyViewStack.top.names))
  }

}
