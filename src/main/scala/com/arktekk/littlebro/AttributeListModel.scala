package com.arktekk.littlebro

import XmlHelper._
import xml.Node

/**
 * @author Thor Åge Eldby (thoraageeldby@gmail.com)
 */
class AttributeListModel(xml: Node) extends ListModel {
  override def nodes = ((xml \\ "span").filterClass("management") \\ "a").filterClass("attribute")

  override def names = nodes.map({_.text.trim}).toArray

  override def onSelect(position: Int)(retrieve: String => Node) = Some(new AttributeModel(retrieve(nodes(position) \ "@href" toString)))
}
