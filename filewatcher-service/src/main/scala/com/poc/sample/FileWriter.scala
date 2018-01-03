package com.poc.sample

import akka.actor.{ Actor, ActorLogging }
import com.poc.sample.Models.FileInfo
import com.poc.sample.config.Settings
import org.apache.camel.ProducerTemplate

import scala.collection.JavaConversions._
import scala.util.{ Failure, Success, Try }

/**
  * Created by Bala.
  */
class FileWriter(producerTemplate: ProducerTemplate) extends Actor with ActorLogging {

  val endpointUri = new StringBuilder("file:/").append(Settings.targetDirectory).toString()

  def receive = {
    case fileInfo: FileInfo => {
      val fileWriterResponse = Try(producerTemplate.requestBodyAndHeaders(endpointUri, fileInfo.fileBody, createHeaders(fileInfo.fileName)))
      fileWriterResponse match {
        case Success(fileWriterResponse) =>
          fileWriterResponse match {
            case responseString: String => {
              log.info(s"The file with the name ${fileInfo.fileName} has been ingested to the ${Settings.targetDirectory}")
            }
            case other => log.info(s"The file with the name ${fileInfo.fileName} has been ingested to the ${Settings.targetDirectory}. However the result seems to be not correct. Please recheck!!")
          }
        case Failure(failure) =>
          failure match {
            case exception: Exception => {
              log.error(s"Exception occurred when the file with the name ${fileInfo.fileName} has been ingested to the ${Settings.targetDirectory}")
            }
            case error: Error => {
              log.info(s"Unexpected Error occurred when the file with the name ${fileInfo.fileName} has been ingested to the ${Settings.targetDirectory}")
            }
          }
      }
    }
  }

  def createHeaders(fileName: String): Map[String, Object] = {
    Map(
      "CamelFileName" -> fileName
    )
  }

}
