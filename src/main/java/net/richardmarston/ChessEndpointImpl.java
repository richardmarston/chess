package net.richardmarston;

import javax.jws.WebService;
import javax.jws.WebMethod;

@WebService (endpointInterface = "net.richardmarston.ChessClientInterface")
public class ChessEndpointImpl implements ChessClientInterface {

    @WebMethod(operationName="startNewGame")
	@Override
    public String startNewGame() {
        return "New game ready.";
    }
}