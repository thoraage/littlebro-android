package com.arktekk.littlebro

import java.net.URL

/**
 * @author Thor Åge Eldby (thoraageeldby@gmail.com)
 */

object ServerConnection {

  //val jmxEnv: Map[String, Array[String]] = new HashMap
  //val credentials = Array("admin", "adminadmin")
  private val defaultServerConnection = new ServerConnection(new URL("http://10.80.17.79:8888/domains"))

  def getDefault = defaultServerConnection

}

class ServerConnection(baseUrl: URL) {

  def load = XmlParser.loadXML(baseUrl.openStream)

  def loadUri(url: URL) = XmlParser.loadXML(url.openStream)

}