package org.arktekk.littlebro

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import util.ListenerConversions._

/**
 * @author Thor Åge Eldby (thoraageeldby@gmail.com)
 */

class MainActivity extends Activity with TypedActivity {
  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.connections)
    val loginButton = findView(TR.login)
    loginButton.onClick {
      val hostAddressTextView = findView(TR.hostAddress)
      val intent = new Intent(MainActivity.this, classOf[BrowserActivity])
      intent.setData(Uri.parse(hostAddressTextView.getText.toString))
      startActivity(intent)
    }
  }

}
