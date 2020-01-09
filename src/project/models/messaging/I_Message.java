package project.models.messaging;

import java.time.LocalDateTime;

/**
 * Defines functions required for messages for inter-user messaging.
 */
public interface I_Message {
    /**
     * @return the _message variable. Represents the contents of the message.
     */
    public abstract String getMessage();

    /**
     * @return the _datetime variable. Represents the time and date of when the message was sent.
     */
    public abstract LocalDateTime getDatetime();

    /**
     * @return the _sender variable. Represents the sender of the message.
     */
    public abstract I_MessageSender getSender();
}
