package controllers.child

import play.api._
import play.api.mvc._

object ChildApplication extends Controller{

  def index= Action { Ok("Child Hello world")}
}