package com.arktekk.littlebro

import XmlHelper._
import xml.Node

/**
 * @author Thor Åge Eldby (thoraageeldby@gmail.com)
 */
class DomainListModel(val node: Node) extends ListModel {
  override def nodes = ((node \\ "span").filterClass("management") \\ "a").filterClass("domain")

  override def names = nodes.map({_.text.trim}).toArray

  override def onSelect(position: Int)(retrieve: String => Node) = Some(new MBeanListModel(retrieve(nodes(position) \ "@href" toString)))
}
