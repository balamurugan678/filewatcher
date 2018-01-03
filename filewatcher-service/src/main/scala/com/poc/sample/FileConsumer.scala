package com.poc.sample

import java.time.LocalDateTime

import akka.actor.{ ActorLogging, Props }
import akka.camel.{ CamelMessage, Consumer }
import akka.pattern.ask
import akka.util.Timeout
import com.poc.sample.Models.FileInfo
import com.poc.sample.config.Settings
import org.apache.camel.ProducerTemplate

import scala.concurrent.duration._

/**
  * Created by Bala.
  */
class FileConsumer(producerTemplate: ProducerTemplate) extends Consumer with ActorLogging {

  def endpointUri = new StringBuilder("file:/").append(Settings.sourceDirectory).append("?noop=true&recursive=true").toString()

  val producer = context.actorOf(Props(classOf[FileWriter], producerTemplate), "fileWriter")
  implicit val timeout = Timeout(10 seconds)

  def receive = {
    case msg: CamelMessage => {
      val messageHeaders = msg.headers
      val messageBody = msg.bodyAs[String]
      val future = producer.ask(FileInfo(messageHeaders.getOrElse("CamelFileName", "UnKnownFileName" + LocalDateTime.now()).toString, messageBody)).mapTo[CamelMessage]
    }
  }
}
