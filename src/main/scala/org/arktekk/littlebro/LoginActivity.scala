package org.arktekk.littlebro

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.{Button, TextView}

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
