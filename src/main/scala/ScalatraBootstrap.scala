
import javax.servlet.ServletContext
import org.scalatra.{ScalatraServlet, LifeCycle}
import pl.kodujdlapolski.ztm.common.ServletCompanion
import pl.kodujdlapolski.ztm.common.web.SwaggerApiDoc
import pl.kodujdlapolski.ztm.core.Beans
import pl.kodujdlapolski.ztm.records.{MetroValidationsServlet, EngineVersionServlet}
import pl.kodujdlapolski.ztm.web.{VehicleLocationsServlet, PingServlet}
import scala.reflect.runtime._

class ScalatraBootstrap extends LifeCycle with Beans {

  val Prefix = "/api/1.0/"

  private def companionOf(servlet: ScalatraServlet) = {
    // See http://stackoverflow.com/questions/11020746/get-companion-object-instance-with-new-scala-reflection-api
    val rootMirror = universe.runtimeMirror(servlet.getClass.getClassLoader)
    val moduleSymbol = rootMirror.moduleSymbol(servlet.getClass)
    val moduleMirror = rootMirror.reflectModule(moduleSymbol)
    moduleMirror.instance.asInstanceOf[ServletCompanion]
  }

  override def init(context: ServletContext) {
    context.setInitParameter("org.eclipse.jetty.servlet.Default.dirAllowed", "false");

    val servlets = List(
      new PingServlet,
      new EngineVersionServlet(engineVersionProc, swagger),
      new MetroValidationsServlet(metroValidationsProc, swagger),
      new VehicleLocationsServlet(vehicleLocationsProc, swagger),
      new SwaggerApiDoc(swagger)
    )

    for (servlet <- servlets)
      context.mount(servlet, Prefix + companionOf(servlet).MappingPath)

  }

  override def destroy(context: ServletContext) {
    super.destroy(context)
  }
}
