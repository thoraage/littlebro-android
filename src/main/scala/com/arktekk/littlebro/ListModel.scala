package com.arktekk.littlebro

import xml.Node

/**
 * @author Thor Åge Eldby (thoraageeldby@gmail.com)
 */
trait ListModel {
  def names(): Array[String]

  def nodes: Seq[Node]

  def onSelect(position: Int): ListModel
}
