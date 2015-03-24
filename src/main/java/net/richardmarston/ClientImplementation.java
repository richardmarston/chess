package net.richardmarston;

import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import net.richardmarston.Interface;

/**
 * Created by rich on 23/03/15.
 */
public class ClientImplementation {

	public static void main(String[] args) throws Exception {

	URL url = new URL("http://localhost:8080/chess/hello?wsdl");

        //1st argument service URI, refer to wsdl document above
	    //2nd argument is service name, refer to wsdl document above
        QName qname = new QName("http://richardmarston.net/", "EndpointImplementationService");

        Service service = Service.create(url, qname);

        Interface hello = service.getPort(Interface.class);

        System.out.println(hello.getHelloWorld("iyy"));

    }
}
