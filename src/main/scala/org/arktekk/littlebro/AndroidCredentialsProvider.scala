package org.arktekk.littlebro

import android.app.Activity
import android.content.Intent
import org.apache.http.client.CredentialsProvider
import org.apache.http.auth.{UsernamePasswordCredentials, Credentials, AuthScope}
import util.Worker
import concurrent.MailBox

/**
 * @author Thor Åge Eldby (thoraageeldby@gmail.com)
 */

trait AndroidCredentialsProvider extends Activity with Worker with CredentialsProvider {
  val credentialsMailBox = new MailBox

  def setCredentials(authScope: AuthScope, credentials: Credentials): Unit = {}

  def getCredentials(authScope: AuthScope): Credentials = {
    onUiThread {
      val intent = new Intent(getContext, classOf[LoginActivity])
      startActivityForResult(intent, LOGIN_INFORMATION)
    }
    credentialsMailBox.receive {
      case Some((userName: String, password: String)) =>
        new UsernamePasswordCredentials(userName, password)
      case None =>
        // TODO how to fail? println("really got: " + a)
        null
    }
  }

  def clear: Unit = {}

  val LOGIN_INFORMATION = 7843

  override def onActivityResult(requestCode: Int, resultCode: Int, data: Intent): Unit = {
    requestCode match {
      case LOGIN_INFORMATION =>
        resultCode match {
          case Activity.RESULT_OK =>
            val userName = data.getExtras.getString(R.id.userName.toString)
            val password = data.getExtras.getString(R.id.password.toString)
            credentialsMailBox.send(Some(userName, password))
          case _ =>
            credentialsMailBox.send(None)
        }
      case _ =>
        super.onActivityResult(requestCode, resultCode, data)
    }
  }

}