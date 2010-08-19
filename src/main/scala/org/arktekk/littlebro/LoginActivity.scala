package org.arktekk.littlebro

import _root_.android.app.Activity
import _root_.android.content.Intent
import _root_.android.os.Bundle
import _root_.android.view.View
import _root_.android.widget.{Button, TextView}

/**
 * @author Thor Åge Eldby (thoraageeldby@gmail.com)
 */

class LoginActivity extends Activity {

  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.login)
    val okButton = findViewById(R.login.ok_button).asInstanceOf[Button]
    okButton.setOnClickListener(new View.OnClickListener {
      override def onClick(v: View) {
        val userNameTextView = findViewById(R.login.user_name_edit).asInstanceOf[TextView]
        val passwordTextView = findViewById(R.login.password_edit).asInstanceOf[TextView]
        val intent = new Intent
        intent.putExtra(R.login.user_name_edit.toString, userNameTextView.getText.toString)
        intent.putExtra(R.login.password_edit.toString, passwordTextView.getText.toString)
        setResult(Activity.RESULT_OK, intent)
        finish
      }
    })
  }

}
