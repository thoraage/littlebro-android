package org.arktekk.littlebro

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.{Button, TextView}
import util.ListenerConversions._

/**
 * @author Thor Åge Eldby (thoraageeldby@gmail.com)
 */

class LoginActivity extends Activity with TypedActivity {
  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.login)
    val okButton = findView(TR.ok)
    okButton.onClick {
      val userNameTextView = findView(TR.userName)
      val passwordTextView = findView(TR.password)
      val intent = new Intent
      intent.putExtra(R.id.userName.toString, userNameTextView.getText.toString)
      intent.putExtra(R.id.password.toString, passwordTextView.getText.toString)
      setResult(Activity.RESULT_OK, intent)
      finish
    }
  }

}
