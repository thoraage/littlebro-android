package com.arktekk.littlebro

import xml.NodeSeq
import java.net.URL
import XmlHelper._

/**
 * @author Thor Åge Eldby (thoraageeldby@gmail.com)
 */
class AttributeListModel(mbeanNode: NodeSeq) extends ListModel {
  private val url = ((mbeanNode) \ "@href").toString
  private val xml = ServerConnection.getDefault.loadUri(new URL(url))

  override def nodes = ((xml \\ "span").filterClass("management") \\ "a").filterClass("attribute")

  override def names = nodes.map({_.text.trim}).toArray

  override def onSelect(position: Int) = null
}
