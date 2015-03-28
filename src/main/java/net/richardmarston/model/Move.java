package net.richardmarston.model;

/**
 * @startuml
 * class model.Move {
 *     -String requestToUser
 *     -String commandFromUser
 * }
 * hide model.Move methods
 * @enduml
 * Created by rich on 26/03/15.
 */
public class Move {

    private String requestToUser;

    public String getRequestToUser() {
        return requestToUser;
    }

    public void setRequestToUser(String requestToUser) {
        this.requestToUser = requestToUser;
    }

    private String commandFromUser;

    public String getCommandFromUser() {
        return commandFromUser;
    }

    public void setCommandFromUser(String commandFromUser) {
        this.commandFromUser = commandFromUser;
    }
}
