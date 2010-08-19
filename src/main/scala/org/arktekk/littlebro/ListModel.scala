package org.arktekk.littlebro

import scala.xml.Node

/**
 * @author Thor Åge Eldby (thoraageeldby@gmail.com)
 */
trait ListModel extends Model {
  def names(): Array[String]

  def nodes: Seq[Node]
}
