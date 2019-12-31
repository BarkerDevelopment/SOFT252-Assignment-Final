package models.messaging;

import java.time.LocalDateTime;

/**
 * A class that encapsulates messages that get passed between users.
 */
public class Message
        implements I_Message {

    private String _message;
    private final LocalDateTime _datetime;
    private final I_MessageSender _sender;

    /**
     * Creates a message object.
     *
     * @param message the content of the message.
     */
    public Message(I_MessageSender sender, String message){
        _sender = sender;
        _message = message;
        _datetime = LocalDateTime.now();
    }

    /**
     * @return the _message variable. Represents the contents of the message.
     */
    @Override
    public String getMessage() {
        return _message;
    }

    /**
     * @return the _datetime variable. Represents the time and date of when the message was sent.
     */
    @Override
    public LocalDateTime getDatetime() {
        return _datetime;
    }

    /**
     * @return the _sender variable. Represents the sender of the message.
     */
    @Override
    public I_MessageSender getSender() {
        return _sender;
    }

    /**
     * @param message the new contents of the _message variable.
     */
    public void setMessage(String message) {
        _message = message;
    }
}
