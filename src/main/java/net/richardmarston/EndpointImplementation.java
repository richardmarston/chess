package net.richardmarston;

import javax.jws.WebService;
import javax.jws.WebMethod;

@WebService (endpointInterface = "net.richardmarston.Interface")
public class EndpointImplementation implements Interface {

    String last = "";
    @WebMethod(operationName="getHelloWorld")
	@Override
    public String getHelloWorld(String name) {
        String current = last;
        last = name;
        return "The last one you sent was: " + current;
    }
}