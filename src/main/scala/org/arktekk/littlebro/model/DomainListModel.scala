package org.arktekk.littlebro.model

import util.XmlHelper._
import scala.xml.Node
import org.arktekk.littlebro.HttpError

/**
 * @author Thor Åge Eldby (thoraageeldby@gmail.com)
 */
class DomainListModel(val node: Node) extends ListModel {
  override def nodes = ((node \\ "span").filterClass("management") \\ "a").filterClass("domain")

  override def names = nodes.map({_.text.trim}).toArray

  override def onSelect(position: Int)(retrieve: String => Either[Node, HttpError]) =
    retrieve(nodes(position) \ "@href" toString) match {
      case Left(node) => Some(Left(new MBeanListModel(node)))
      case Right(httpError) => Some(Right(httpError))
    }
}