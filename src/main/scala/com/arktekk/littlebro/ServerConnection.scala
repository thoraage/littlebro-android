package com.arktekk.littlebro

import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.client.{HttpClient, CredentialsProvider}
import org.apache.http.client.methods.{HttpGet}
import java.net.URI

/**
 * @author Thor Åge Eldby (thoraageeldby@gmail.com)
 */

object ServerConnection {

  def apply(credentialsProvider: CredentialsProvider, uri: URI) = new ServerConnection(credentialsProvider, uri, None)

}

class ServerConnection(credentialsProvider: CredentialsProvider, uri: URI, httpClientDefined: Option[HttpClient]) {
  
  private lazy val httpClient: HttpClient = httpClientDefined match {
    case None =>
      val httpClient = new DefaultHttpClient()
      httpClient.setCredentialsProvider(credentialsProvider)
      httpClient
    case Some(httpClient) => httpClient
  }

  def get = {
    XmlParser.loadXML(httpClient.execute(new HttpGet(uri)).getEntity.getContent)
  }

  def withPath(path: String) = {
    new ServerConnection(this.credentialsProvider, new URI(uri.getScheme, uri.getUserInfo, uri.getHost, uri.getPort, path, null, null), Some(httpClient))
  }

}