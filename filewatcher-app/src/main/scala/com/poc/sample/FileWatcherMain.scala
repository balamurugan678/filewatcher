package com.poc.sample

import java.time.Instant

import akka.actor.{ ActorSystem, Props }
import akka.camel.CamelExtension
import akka.util.Timeout
import com.poc.sample.config.Settings
import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.duration._

/**
  * Created by Bala.
  */
object FileWatcherMain extends App with LazyLogging {

  val system = ActorSystem("file-watcher-system")
  implicit val timeout = Timeout(10 seconds)
  val camel = CamelExtension(system)
  val camelContext = camel.context
  val producerTemplate = camel.template
  camelContext.setStreamCaching(false)
  logger.info("Starting the file watcher at... - " + Instant.now+ " .The directory being watched(recursively) is "+ Settings.sourceDirectory)
  system.actorOf(Props(classOf[FileConsumer], producerTemplate))

}
