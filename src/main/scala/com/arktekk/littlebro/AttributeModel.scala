package com.arktekk.littlebro

import xml.Node
import XmlHelper._

/**
 * @author Thor Åge Eldby (thoraageeldby@gmail.com)
 */

class AttributeModel(xml: Node) extends Model {

  def value = ((xml \\ "span").filterClass("management") \\ "div").filterClass("value").text.trim

  def onSelect(position: Int)(retrieve: String => Node) = None

}
