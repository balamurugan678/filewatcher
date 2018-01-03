package com.poc.sample.config

import com.typesafe.config.ConfigFactory

/**
  * Created by Bala.
  */
object Settings {

  lazy val config = ConfigFactory.load()

  // =========== DIRECTORIES Configuration =================//
  lazy val sourceDirectory = config.getString("file-watcher.source-directory")
  lazy val targetDirectory = config.getString("file-watcher.target-directory")


}
