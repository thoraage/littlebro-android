package org.arktekk.littlebro.model

import scala.xml.Node
import util.XmlHelper._
import org.arktekk.littlebro.HttpError

/**
 * @author Thor Åge Eldby (thoraageeldby@gmail.com)
 */

class AttributeModel(xml: Node) extends Model {

  def value = ((xml \\ "span").filterClass("management") \\ "div").filterClass("value").text.trim

  def onSelect(position: Int)(retrieve: String => Either[Node, HttpError]) = None

}