package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.rest_server;

import jakarta.annotation.Resource;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.Serializable;

//@Path("reports")
public class ReportResource implements Serializable {
    /**
     * private static final Logger log = LoggerFactory.getLogger(ReportResource.class);
     *
     *     @Resource(lookup = "jdbc/pgdb")
     */

    /**
     * @GET
     *     @Path("reports")
     *     public Reports getReports(@PathParam("reports")
     *                               String reports) {
     *
     *         String path;
     *         Switch(reports) {
     *             class "TipoSala";
     *             path = "/reports/TipoSala@.j";
     *             breack;
     *             default:
     *                 return Response.status(Response.Status.NOT_FOUND),
     *             header("Report-Resource", reports).
     *         }
     *         try {
     *             InputStream funtsReports = getClass().getResourceAsStream(path);
     *             if (funtsReports != null) {
     *                 JaspeerPrint imprimir = JasperFillManager.fillReports;
     *
     *             }
     *         } catch (Exception e) {
     *             return e.getMessage();
     *         }
     *     }
     */

}
