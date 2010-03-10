package com.arktekk.littlebro

import _root_.android.os.Bundle
import java.util.{Map, HashMap}
import java.net.URL
import _root_.android.widget.{ArrayAdapter, ListView}
import _root_.android.app.{ListActivity, Activity}

class MainActivity extends ListActivity {
  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    val jmxEnv: Map[String, Array[String]] = new HashMap
    val credentials = Array("admin", "adminadmin")
    val jmxUrl = new URL("http://192.168.1.142:8888/jmxRestAccess/domains")
    val xml = XmlParser.loadXML(jmxUrl.openStream)
    val nameNodes = xml \\ "@name"
    val names = nameNodes.map({ _.toString })
    setListAdapter(new ArrayAdapter(this, R.layout.list_item, names.toArray))
  }
}
