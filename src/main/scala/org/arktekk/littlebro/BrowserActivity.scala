package org.arktekk.littlebro

import android.app.{AlertDialog, ListActivity}
import android.content.DialogInterface.OnClickListener
import android.content.DialogInterface
import android.os.Bundle
import android.view.KeyEvent
import android.widget.ArrayAdapter
import collection.mutable.Stack
import model.{AttributeModel, DomainListModel, ListModel}
import util.UICallbackHandler
import java.net.URI
import util.Worker._
import util.XmlHelper._
import util.ListenerConversions._

/**
 * @author Thor Åge Eldby (thoraageeldby@gmail.com)
 */
class BrowserActivity extends ListActivity with AndroidCredentialsProvider with UICallbackHandler {
  val propertyViewStack = new Stack[ListModel]

  def getContext = this

  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    val uri = new URI(getIntent.getDataString)
    val serverConnection = new ServerConnection(BrowserActivity.this, uri)
    busyWorker {
      serverConnection.get match {
        case Left(node) =>
          val domainList = new DomainListModel(node)
          propertyViewStack.push(domainList)
          populate
        case Right(httpError) =>
          runOnUiThread {finish}
      }
    }
    getListView.onItemClick {
      (_, _, position: Int, _) =>
        busyWorker {
          propertyViewStack.top.onSelect(position) {serverConnection.get(_)} match {
            case Some(Left(model)) =>
              model match {
                case model: ListModel =>
                  propertyViewStack.push(model)
                  populate
                case model: AttributeModel =>
                  showAlert(model.value)
              }
            case Some(Right(httpError)) => println("No worky: " + httpError.code) // TODO
            case None =>
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
      setListAdapter(new ArrayAdapter(this, R.layout.browser, propertyViewStack.top.names))
    }
  }

  def showAlert(text: String) {
    runOnUiThread {
      val builder = new AlertDialog.Builder(BrowserActivity.this)
      builder.setTitle("Value").setMessage(text).onNeutralButton("Ok", {(dialog, _) => dialog.dismiss})
      builder.create.show
    }
  }

}
