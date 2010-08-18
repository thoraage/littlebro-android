package com.arktekk.littlebro

import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.client.{HttpClient, CredentialsProvider}
import org.apache.http.client.methods.HttpGet
import java.net.URI

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

  def getXml(uri: URI) = XmlParser.loadXML(httpClient.execute(new HttpGet(uri)).getEntity.getContent)

}