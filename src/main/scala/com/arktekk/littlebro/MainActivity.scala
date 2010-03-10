package com.arktekk.littlebro

import _root_.android.os.Bundle
import _root_.android.widget.AdapterView.OnItemClickListener
import _root_.android.widget.{AdapterView, ArrayAdapter}
import _root_.android.view.View
import _root_.android.app.ListActivity
import xml.NodeSeq
import java.net.URL

class MainActivity extends ListActivity {
  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    val xml = ServerConnection.getDefault.load
    val domainNodes = xml \\ "domain"
    val names = domainNodes.map({node => (node \ "@name").toString})
    setListAdapter(new ArrayAdapter(this, R.layout.list_item, names.toArray))
    getListView.setOnItemClickListener(new DomainViewOpenListener(this, domainNodes))
  }

}

class DomainViewOpenListener(that: ListActivity, domainNodes: NodeSeq) extends OnItemClickListener {
  def onItemClick(parent: AdapterView[_], view: View, position: Int, id: Long) {
    val url = (domainNodes(position) \ "@mbeansUri").toString
    val xml = ServerConnection.getDefault.loadUri(new URL(url))
    val mbeanNodes = xml \\ "mbean"
    val names = mbeanNodes.map({node => (node \ "@keyProperties").toString})
    that.setListAdapter(new ArrayAdapter(that, R.layout.list_item, names.toArray))
  }
}