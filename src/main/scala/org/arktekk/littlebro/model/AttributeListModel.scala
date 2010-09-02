package org.arktekk.littlebro.model

import org.arktekk.littlebro.util.XmlHelper._
import scala.xml.Node
import org.arktekk.littlebro.HttpError

/**
 * @author Thor Åge Eldby (thoraageeldby@gmail.com)
 */
class AttributeListModel(xml: Node) extends ListModel {
  override def nodes = ((xml \\ "span").filterClass("management") \\ "a").filterClass("attribute")

  override def names = nodes.map({_.text.trim}).toArray

  override def onSelect(position: Int)(retrieve: String => Either[Node, HttpError]) =
    retrieve(nodes(position) \ "@href" toString) match {
      case Left(node) => Some(Left(new AttributeModel(node)))
      case Right(httpError) => Some(Right(httpError))
    }
}