package com.arktekk.littlebro

import XmlHelper._
import xml.Node

/**
 * @author Thor Åge Eldby (thoraageeldby@gmail.com)
 */
class DomainListModel(val node: Node) extends ListModel {
  override def nodes = ((node \\ "span").filterClass("management") \\ "a").filterClass("domain")

  override def names = nodes.map({_.text.trim}).toArray

  // TODO remove server connection from models
  override def onSelect(position: Int) = new MBeanListModel(null, nodes(position))
}
