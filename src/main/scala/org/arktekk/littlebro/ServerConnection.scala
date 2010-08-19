package org.arktekk.littlebro

import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.client.{HttpClient, CredentialsProvider}
import org.apache.http.client.methods.HttpGet
import java.net.URI
import org.apache.http.HttpStatus
import scala.xml.Node
import util.XmlParser

/**
 * @author Thor Åge Eldby (thoraageeldby@gmail.com)
 */

class ServerConnection(credentialsProvider: CredentialsProvider, uri: URI) {

  private lazy val httpClient: HttpClient = {
    val httpClient = new DefaultHttpClient()
    httpClient.setCredentialsProvider(credentialsProvider)
    httpClient
  }

  def get = getXml(uri)

  def get(path: String) = getXml(new URI(uri.getScheme, uri.getUserInfo, uri.getHost, uri.getPort, path, null, null))

  def getXml(uri: URI): Either[Node, HttpError] = {
    val response = httpClient.execute(new HttpGet(uri))
    response.getStatusLine.getStatusCode match {
      case HttpStatus.SC_OK => Left(XmlParser.loadXML(response.getEntity.getContent))
      case HttpStatus.SC_UNAUTHORIZED => getXml(uri)
      case code @ HttpStatus.SC_NOT_FOUND => Right(HttpError(code, "TODO Fetch content"))
      case other => error("Response status " + other)
    }
  }

}

case class HttpError(code: Int, content: String)