package controllers

import javax.inject._
import play.api._
import play.api.mvc._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
var counter = 0l;

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index() = Action { implicit request: Request[AnyContent] =>
    counter = counter + 1
    val hostName =  scala.util.Properties.envOrElse("HOSTNAME", "-" )
    Ok(views.html.index(hostName, counter))
  }

   def showIndex() = Action { implicit request: Request[AnyContent] =>
    val hostName =  scala.util.Properties.envOrElse("HOSTNAME", "-" )
    Ok(views.html.index(hostName, counter))
  }
}
