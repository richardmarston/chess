package net.richardmarston.model;

/**
 * @startuml
 * class model.Move {
 *     String requestToUser
 *     String commandFromUser
 * }
 * @enduml
 * Created by rich on 26/03/15.
 */
public class Move {

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String command;

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }
}
