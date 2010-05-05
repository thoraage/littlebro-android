package com.arktekk.littlebro

import XmlHelper._

/**
 * @author Thor Åge Eldby (thoraageeldby@gmail.com)
 */
class DomainListModel(val serverConnection: ServerConnection) extends ListModel {
  override def nodes = ((serverConnection.load \\ "span").filterClass("management") \\ "a").filterClass("domain")

  override def names = nodes.map({_.text.trim}).toArray

  override def onSelect(position: Int) = new MBeanListModel(nodes(position))
}
