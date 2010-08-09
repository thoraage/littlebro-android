package com.arktekk.littlebro

import org.apache.http.client.CredentialsProvider
import org.apache.http.auth.{UsernamePasswordCredentials, Credentials, AuthScope}

/**
 * @author Thor Åge Eldby (thoraageeldby@gmail.com)
 */

trait AndroidCredentialsProvider extends CredentialsProvider {

  def setCredentials(authScope: AuthScope, credentials: Credentials): Unit = {}

  def getCredentials(authScope: AuthScope): Credentials = { new UsernamePasswordCredentials("admin", "adminadmin") }

  def clear: Unit = {}
  
}