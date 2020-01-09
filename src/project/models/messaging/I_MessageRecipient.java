package project.models.messaging;

import java.util.ArrayList;

/**
 * Defines functions required for objects receiving messages.
 */
public interface I_MessageRecipient {
    /**
     * @return the _messages variable. Represents the stored messages to the user.
     */
    public abstract ArrayList<I_Message> getMessages();

    /**
     * @param messages the new contents to set _prescriptions to.
     */
    public abstract void setMessage(ArrayList<I_Message> messages);


}
