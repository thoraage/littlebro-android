package com.arktekk.littlebro

import xml.Node

/**
 * @author Thor Åge Eldby (thoraageeldby@gmail.com)
 */

trait Model {
  def onSelect(position: Int)(retrieve: String => Either[Node, HttpError]): Option[Either[Model, HttpError]]
}