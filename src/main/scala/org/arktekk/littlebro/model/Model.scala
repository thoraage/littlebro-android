package org.arktekk.littlebro.model

import scala.xml.Node
import org.arktekk.littlebro.HttpError

/**
 * @author Thor Åge Eldby (thoraageeldby@gmail.com)
 */

trait Model {
  def onSelect(position: Int)(retrieve: String => Either[Node, HttpError]): Option[Either[Model, HttpError]]
}