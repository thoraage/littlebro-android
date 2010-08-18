package com.arktekk.littlebro

import _root_.android.app.{AlertDialog, ListActivity}
import _root_.android.content.DialogInterface.OnClickListener
import _root_.android.content.DialogInterface
import _root_.android.os.Bundle
import _root_.android.view.KeyEvent
import _root_.android.widget.ArrayAdapter
import collection.mutable.Stack
import util.UICallbackHandler
import java.net.URI
import util.Worker._
import XmlHelper._
import util.ListenerConversions._

/**
 * @author Thor Åge Eldby (thoraageeldby@gmail.com)
 */
class BrowserActivity extends ListActivity with AndroidCredentialsProvider with UICallbackHandler {
  val propertyViewStack = new Stack[Model]

  def getContext = this

  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    val domainUri = new URI(getIntent.getDataString)
    val serverConnection = new ServerConnection(BrowserActivity.this, domainUri)
    busy {
      worker {
        serverConnection.get match {
          case Left(node) =>
            val domainList = new DomainListModel(node)
            runOnUiThread {
              propertyViewStack.push(domainList)
              populate
            }
          case Right(httpError) =>
            runOnUiThread {
              finish
            }
        }
      }
    }
    getListView.onItemClick {
      (_, _, position: Int, _) =>
        busy {
          worker {
            propertyViewStack.top.onSelect(position) {serverConnection.get(_)} match {
              case Some(Left(model)) =>
                propertyViewStack.push(model)
                populate
              case Some(Right(httpError)) => println("No worky: " + httpError.code) // TODO
              case None =>
            }
          }
        }
    }
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
    runOnUiThread {
      propertyViewStack.top match {
        case model: ListModel =>
          setListAdapter(new ArrayAdapter(this, R.layout.browser, model.names))
        case model: AttributeModel =>
          val builder = new AlertDialog.Builder(BrowserActivity.this)
          builder.setTitle("Value").setMessage(model.value).setNeutralButton("Ok", new OnClickListener {
            override def onClick(dialog: DialogInterface, id: Int) {
              dialog.dismiss
            }
          })
          builder.create.show
      }
    }
  }

}
