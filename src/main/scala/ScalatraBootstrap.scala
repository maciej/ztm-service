import javax.servlet.ServletContext
import org.scalatra.{ScalatraServlet, LifeCycle}
import pl.kodujdlapolski.ztm.common.ServletCompanion
import pl.kodujdlapolski.ztm.web.PingServlet
import scala.reflect.runtime._

class ScalatraBootstrap extends LifeCycle {

  private def companionOf(servlet: ScalatraServlet) = {
    // See http://stackoverflow.com/questions/11020746/get-companion-object-instance-with-new-scala-reflection-api
    val rootMirror = universe.runtimeMirror(servlet.getClass.getClassLoader)
    val moduleSymbol = rootMirror.moduleSymbol(servlet.getClass)
    val moduleMirror = rootMirror.reflectModule(moduleSymbol)
    moduleMirror.instance.asInstanceOf[ServletCompanion]
  }

  override def init(context: ServletContext) {
    context.setInitParameter("org.eclipse.jetty.servlet.Default.dirAllowed", "false");

    context.mount(new PingServlet, "/*")
  }

  override def destroy(context: ServletContext) {
    super.destroy(context)
  }
}
