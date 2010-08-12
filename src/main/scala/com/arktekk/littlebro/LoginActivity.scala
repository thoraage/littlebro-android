package com.arktekk.littlebro

import _root_.android.app.Activity
import _root_.android.os.Bundle

/**
 * @author Thor Åge Eldby (thoraageeldby@gmail.com)
 */

class LoginActivity extends Activity {

  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.login)
  }

}