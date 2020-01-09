package project.controllers.auxiliary;

import project.controllers.repository.UserRepositoryController;
import project.models.messaging.I_Message;
import project.models.messaging.I_MessageRecipient;

import java.util.ArrayList;

public class MessageController {
    public static ArrayList< I_Message> get(I_MessageRecipient recipient){
        return recipient.getMessages();
    }

    public static void send(I_MessageRecipient recipient, I_Message message){
        recipient.getMessages().add(message);
        UserRepositoryController.getInstance().save();
    }
}
