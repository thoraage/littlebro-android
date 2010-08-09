package com.arktekk.littlebro

import xml.NodeSeq
import java.net.URL
import XmlHelper._

/**
 * @author Thor Åge Eldby (thoraageeldby@gmail.com)
 */
class AttributeListModel(val serverConnection: ServerConnection, mbeanNode: NodeSeq) extends ListModel {
  private val href = ((mbeanNode) \ "@href").toString
  private val xml = serverConnection.withPath(href).get

  override def nodes = ((xml \\ "span").filterClass("management") \\ "a").filterClass("attribute")

  override def names = nodes.map({_.text.trim}).toArray

  override def onSelect(position: Int) = null
}
