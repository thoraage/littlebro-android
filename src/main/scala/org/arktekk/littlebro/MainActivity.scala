package org.arktekk.littlebro

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.{TextView, Button}
import util.ListenerConversions._

/**
 * @author Thor Åge Eldby (thoraageeldby@gmail.com)
 */

class MainActivity extends Activity {
  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.connections)
    val loginButton = findViewById(R.connections.login_button).asInstanceOf[Button]
    loginButton.onClick {
      _ =>
        val hostAddressTextView = findViewById(R.connections.host_address_edit).asInstanceOf[TextView]
        val intent = new Intent(MainActivity.this, classOf[BrowserActivity])
        intent.setData(Uri.parse(hostAddressTextView.getText.toString))
        startActivity(intent)
    }
  }

}
