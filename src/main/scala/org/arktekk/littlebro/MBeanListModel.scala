package org.arktekk.littlebro

import XmlHelper._
import scala.xml.Node

/**
 * @author Thor Åge Eldby (thoraageeldby@gmail.com)
 */
class MBeanListModel(xml: Node) extends ListModel {
  override def nodes = ((xml \\ "span").filterClass("management") \\ "a").filterClass("mbean")

  override def names = nodes.map({_.text.trim}).toArray

  override def onSelect(position: Int)(retrieve: String => Either[Node, HttpError]) =
    retrieve(nodes(position) \ "@href" toString) match {
      case Left(node) => Some(Left(new AttributeListModel(node)))
      case Right(httpError) => Some(Right(httpError))
    }
}
