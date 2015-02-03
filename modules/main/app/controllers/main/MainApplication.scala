package controllers.main

import play.api._
import play.api.mvc._

object MainApplication extends Controller{

  def index= Action { Ok("Main Hello world")}
} 