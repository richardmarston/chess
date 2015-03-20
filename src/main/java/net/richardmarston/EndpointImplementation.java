package net.richardmarston;

import javax.jws.WebService;
import javax.jws.WebMethod;

@WebService
public class EndpointImplementation  {

    @WebMethod(operationName="getHelloWorld")
	public String sayHello(String name) {
		return "Hello, Welcom to jax-ws " + name;
	}

}