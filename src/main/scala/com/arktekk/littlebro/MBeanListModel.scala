package com.arktekk.littlebro

import java.net.URL
import xml.NodeSeq
import XmlHelper._

/**
 * @author Thor Åge Eldby (thoraageeldby@gmail.com)
 */
class MBeanListModel(serverConnection: ServerConnection, domainNode: NodeSeq) extends ListModel {
  private val href = (domainNode \ "@href").toString
  private val xml = serverConnection.withPath(href).get

  override def nodes = ((xml \\ "span").filterClass("management") \\ "a").filterClass("mbean")

  override def names = nodes.map({_.text.trim}).toArray

  override def onSelect(position: Int) = new AttributeListModel(serverConnection, nodes(position))
}
