package com.arktekk.littlebro

import XmlHelper._
import xml.Node

/**
 * @author Thor Åge Eldby (thoraageeldby@gmail.com)
 */
class MBeanListModel(xml: Node) extends ListModel {
  override def nodes = ((xml \\ "span").filterClass("management") \\ "a").filterClass("mbean")

  override def names = nodes.map({_.text.trim}).toArray

  override def onSelect(position: Int)(retrieve: String => Node) = Some(new AttributeListModel(retrieve(nodes(position) \ "@href" toString)))
}
