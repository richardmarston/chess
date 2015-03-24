package net.richardmarston;

/**
 * Created by rich on 23/03/15.
 */

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

//Service Endpoint Interface
@WebService
@SOAPBinding(style = Style.RPC)
public interface ChessClientInterface {

	@WebMethod String startNewGame();

}

