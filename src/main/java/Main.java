import org.eclipse.jetty.annotations.AnnotationConfiguration;
import org.eclipse.jetty.plus.webapp.EnvConfiguration;
import org.eclipse.jetty.plus.webapp.PlusConfiguration;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.*;

public class Main {

    public static void main(String[] args) throws Exception {
        WebAppContext webAppContext = new WebAppContext();
//        webAppContext.addServlet(HelloServlet.class, "/api/*");
        webAppContext.setResourceBase("src/main/webapp");
        webAppContext.setContextPath("/");

        webAppContext.setConfigurations(new Configuration[]{
                new AnnotationConfiguration(),
                new WebInfConfiguration(),
                new WebXmlConfiguration(),
                new MetaInfConfiguration(),
                new FragmentConfiguration(),
                new EnvConfiguration(),
                new PlusConfiguration(),
                new JettyWebXmlConfiguration()
        });

        webAppContext.setAttribute("org.eclipse.jetty.server.webapp.ContainerIncludeJarPattern", ".*/classes/.*");

        Server server = new Server(8080);
        server.setHandler(webAppContext);

        server.start();
        server.join();
    }
}
