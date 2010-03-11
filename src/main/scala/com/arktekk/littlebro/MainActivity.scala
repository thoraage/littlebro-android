package com.arktekk.littlebro

import _root_.android.os.Bundle
import _root_.android.widget.AdapterView.OnItemClickListener
import _root_.android.widget.{AdapterView, ArrayAdapter}
import _root_.android.view.View
import _root_.android.app.ListActivity
import java.net.URL
import collection.mutable.Stack
import xml.{Elem, Node, NodeSeq}
import XmlHelper._

class MainActivity extends ListActivity {

  val serverConnection = ServerConnection.getDefault
  val propertyViewStack = new Stack[ViewList]

  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    populate(serverConnection.load)
  }

  def populate(xml: Node) {
    val domainNodes = ((xml \\ "span").filterClass("management") \\ "a").filterClass("domain")
    val names = domainNodes.map({_.text.trim}).toArray
    setListAdapter(new ArrayAdapter(this, R.layout.list_item, names))
    getListView.setOnItemClickListener(new MBeanViewList(this, domainNodes))
  }

}

trait ViewList {
  def getElementList();
  def onSelect(): ViewList;
}

abstract class DomainViewList(that: ListActivity) extends ViewList {
}

class MBeanViewList(that: ListActivity, domainNodes: NodeSeq) extends OnItemClickListener {
  def onItemClick(parent: AdapterView[_], view: View, position: Int, id: Long) {
    val url = (domainNodes(position) \ "@href").toString
    val xml = ServerConnection.getDefault.loadUri(new URL(url))
    val mbeanNodes = ((xml \\ "span").filterClass("management") \\ "a").filterClass("mbean")
    val names = mbeanNodes.map({_.text.trim}).toArray
    that.setListAdapter(new ArrayAdapter(that, R.layout.list_item, names))
    that.getListView.setOnItemClickListener(new AttributeViewList(that, mbeanNodes))
  }
}

class AttributeViewList(that: ListActivity, mbeanNodes: NodeSeq) extends OnItemClickListener {
  def onItemClick(parent: AdapterView[_], view: View, position: Int, id: Long) {
    val url = ((mbeanNodes(position)) \ "@href").toString
    val xml = ServerConnection.getDefault.loadUri(new URL(url))
    val attributeNodes = ((xml \\ "span").filterClass("management") \\ "a").filterClass("attribute")
    val names = attributeNodes.map({_.text.trim}).toArray
    that.setListAdapter(new ArrayAdapter(that, R.layout.list_item, names))
    that.getListView.setOnItemClickListener(null)
  }
}
