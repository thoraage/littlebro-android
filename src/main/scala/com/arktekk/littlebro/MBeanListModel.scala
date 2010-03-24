package com.arktekk.littlebro

import java.net.URL
import xml.NodeSeq
import XmlHelper._

/**
 * @author Thor Åge Eldby (thoraageeldby@gmail.com)
 */
class MBeanListModel(domainNode: NodeSeq) extends ListModel {
  private val url = (domainNode \ "@href").toString
  private val xml = ServerConnection.getDefault.loadUri(new URL(url))

  override def nodes = ((xml \\ "span").filterClass("management") \\ "a").filterClass("mbean")

  override def names = nodes.map({_.text.trim}).toArray

  override def onSelect(position: Int) = new AttributeListModel(nodes(position))
}
