package com.arktekk.littlebro

import _root_.android.app.Activity
import _root_.android.content.Intent
import _root_.android.net.Uri
import _root_.android.os.Bundle
import _root_.android.view.View
import _root_.android.widget.{TextView, Button}
import java.net.URL

/**
 * @author Thor Åge Eldby (thoraageeldby@gmail.com)
 */

class MainActivity extends Activity {
  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.connection_list)
    val loginButton = findViewById(R.login.login_button).asInstanceOf[Button]
    loginButton.setOnClickListener(new View.OnClickListener() {
      override def onClick(v: View) {
        val hostAddressTextView = findViewById(R.login.host_address_edit).asInstanceOf[TextView]
        val intent: Intent = new Intent(MainActivity.this, classOf[BrowserActivity])
        intent.setData(Uri.parse(hostAddressTextView.getText.toString))
        startActivity(intent)
      }
    })
  }
}
