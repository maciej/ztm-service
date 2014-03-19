
import javax.servlet.ServletContext
import org.scalatra.{ScalatraServlet, LifeCycle}
import pl.kodujdlapolski.ztm.api.v1_0.{MetroValidationsServlet, EngineVersionServlet, VehicleLocationsServlet}
import pl.kodujdlapolski.ztm.common.ServletCompanion
import pl.kodujdlapolski.ztm.common.web.SwaggerApiDoc
import pl.kodujdlapolski.ztm.core.Beans
import pl.kodujdlapolski.ztm.web.PingServlet
import scala.reflect.runtime._

class ScalatraBootstrap extends LifeCycle {

  val Prefix = "/api/1.0/"

  private def companionOf(servlet: ScalatraServlet) = {
    // See http://stackoverflow.com/questions/11020746/get-companion-object-instance-with-new-scala-reflection-api
    val rootMirror = universe.runtimeMirror(servlet.getClass.getClassLoader)
    val moduleSymbol = rootMirror.moduleSymbol(servlet.getClass)
    val moduleMirror = rootMirror.reflectModule(moduleSymbol)
    moduleMirror.instance.asInstanceOf[ServletCompanion]
  }

  override def init(context: ServletContext) {
    val beans = Option(context.getAttribute("beans").asInstanceOf[Beans]).orElse(Some(Beans)).get

    context.setInitParameter("org.eclipse.jetty.servlet.Default.dirAllowed", "false");

    val servlets = List(
      new PingServlet,
      new EngineVersionServlet(beans.engineVersionProc, beans.swagger),
      new MetroValidationsServlet(beans.metroValidationsProc, beans.swagger),
      new VehicleLocationsServlet(beans.vehicleLocationsProc, beans.swagger),
      new SwaggerApiDoc(beans.swagger)
    )

    for (servlet <- servlets)
      context.mount(servlet, Prefix + companionOf(servlet).MappingPath)

  }

  override def destroy(context: ServletContext) {
    super.destroy(context)
  }
}
