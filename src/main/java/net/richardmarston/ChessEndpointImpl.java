package net.richardmarston;

import javax.jws.WebService;
import javax.jws.WebMethod;

@WebService (endpointInterface = "net.richardmarston.ChessClientInterface")
public class ChessEndpointImpl implements ChessClientInterface {

    ChessEngineComms engine = null;

    @WebMethod(operationName="startGame")
	@Override
    public String startNewGame() {
        if (engine != null) {
            engine.sendCommand("new");
        }
        else {
            engine = new ChessEngineComms();
        }
        return "New game ready.";
    }
}