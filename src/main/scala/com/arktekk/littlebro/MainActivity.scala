package com.arktekk.littlebro

import _root_.android.app.{AlertDialog, ListActivity}
import _root_.android.content.DialogInterface.OnClickListener
import _root_.android.content.DialogInterface
import _root_.android.os.Bundle
import _root_.android.view.{KeyEvent, View}
import _root_.android.widget.{AdapterView, ArrayAdapter}
import _root_.android.widget.AdapterView.{OnItemClickListener}
import java.net.URL
import collection.mutable.Stack
import xml.{Node, NodeSeq}
import XmlHelper._

/**
 * @author Thor Åge Eldby (thoraageeldby@gmail.com)
 */
class MainActivity extends ListActivity {
  val serverConnection = ServerConnection.getDefault
  val propertyViewStack = new Stack[ListModel]

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

  trait ListModel {
    def names(): Array[String]

    def nodes: Seq[Node]

    def onSelect(position: Int): ListModel
  }

  class DomainListModel extends ListModel {
    // TODO: (xml \\ "span")\["@class" == "management"\]    "span[@class='management']//a[@class='mbean']"
    override def nodes = ((serverConnection.load \\ "span").filterClass("management") \\ "a").filterClass("domain")

    override def names = nodes.map({_.text.trim}).toArray

    override def onSelect(position: Int) = new MBeanListModel(nodes(position))
  }

  class MBeanListModel(domainNode: NodeSeq) extends ListModel {
    private val url = (domainNode \ "@href").toString
    private val xml = ServerConnection.getDefault.loadUri(new URL(url))

    override def nodes = ((xml \\ "span").filterClass("management") \\ "a").filterClass("mbean")

    override def names = nodes.map({_.text.trim}).toArray

    override def onSelect(position: Int) = new AttributeListModel(nodes(position))
  }

  class AttributeListModel(mbeanNode: NodeSeq) extends ListModel {
    private val url = ((mbeanNode) \ "@href").toString
    private val xml = ServerConnection.getDefault.loadUri(new URL(url))

    override def nodes = ((xml \\ "span").filterClass("management") \\ "a").filterClass("attribute")

    override def names = nodes.map({_.text.trim}).toArray

    override def onSelect(position: Int) = {
      val builder = new AlertDialog.Builder(MainActivity.this)
      val url = (nodes(position) \ "@href").toString
      val xml = serverConnection.loadUri(new URL(url))
      val value = ((xml \\ "span").filterClass("management") \\ "div").filterClass("value").text.trim
      builder.setTitle("Value").setMessage(value).setNeutralButton("Ok", new OnClickListener {
        override def onClick(dialog: DialogInterface, id: Int) {
          dialog.dismiss
        }
      })
      builder.create.show      
      null
    }
  }
}
