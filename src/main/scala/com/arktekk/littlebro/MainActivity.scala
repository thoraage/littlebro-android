package com.arktekk.littlebro

import _root_.android.os.Bundle
import _root_.android.widget.AdapterView.OnItemClickListener
import _root_.android.widget.{AdapterView, ArrayAdapter}
import _root_.android.view.View
import _root_.android.app.ListActivity
import java.net.URL
import collection.mutable.Stack
import xml.{Node, NodeSeq}
import XmlHelper._

/**
 * @author Thor Åge Eldby (thoraageeldby@gmail.com)
 */
class MainActivity extends ListActivity {
  val serverConnection = ServerConnection.getDefault
  val propertyViewStack = new Stack[ViewList]

  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    propertyViewStack.push(new DomainViewList)
    populate
    getListView.setOnItemClickListener(new OnItemClickListener {
      def onItemClick(parent: AdapterView[_], view: View, position: Int, id: Long) {
        val viewList = propertyViewStack.top.onSelect(position)
        if (viewList != null) {
          propertyViewStack.push(viewList)
          populate
        }
      }
    })
  }

  def populate() {
    setListAdapter(new ArrayAdapter(this, R.layout.list_item, propertyViewStack.top.names))
  }

  trait ViewList {
    def names(): Array[String]

    def nodes: Seq[Node]

    def onSelect(position: Int): ViewList
  }

  class DomainViewList extends ViewList {
    // TODO: (xml \\ "span")["@class" == "management"]
    override def nodes = ((serverConnection.load \\ "span").filterClass("management") \\ "a").filterClass("domain")

    override def names = nodes.map({_.text.trim}).toArray

    override def onSelect(position: Int) = new MBeanViewList(nodes(position))
  }

  class MBeanViewList(domainNode: NodeSeq) extends ViewList {
    private val url = (domainNode \ "@href").toString
    private val xml = ServerConnection.getDefault.loadUri(new URL(url))

    override def nodes = ((xml \\ "span").filterClass("management") \\ "a").filterClass("mbean")

    override def names = nodes.map({_.text.trim}).toArray

    override def onSelect(position: Int) = new AttributeViewList(nodes(position))
  }

  class AttributeViewList(mbeanNode: NodeSeq) extends ViewList {
    private val url = ((mbeanNode) \ "@href").toString
    private val xml = ServerConnection.getDefault.loadUri(new URL(url))

    override def nodes = ((xml \\ "span").filterClass("management") \\ "a").filterClass("attribute")

    override def names = nodes.map({_.text.trim}).toArray

    override def onSelect(position: Int) = null
  }
}
