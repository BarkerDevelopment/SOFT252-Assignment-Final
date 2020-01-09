package project.models.requests;

import project.controllers.repository.RequestRepositoryController;
import project.models.messaging.I_MessageSender;
import project.models.repositories.I_RepositoryItem;

import java.io.Serializable;

/**
 * Template pattern for a request as approval/denial end in the request being deleted from the appropriate repository.
 */
public abstract class Request
    implements I_RepositoryItem, I_MessageSender, Serializable {

    protected RequestType _type;

    /**
     * Default constructor.
     * @param type the type of the request.
     */
    public Request(RequestType type){
        _type = type;
    }

    /**
     * @return the type of the request.
     */
    public RequestType getType(){
        return _type;
    }

    /**
     * The action following request approval.
     */
    public abstract void approveAction() throws Exception;

    /**
     * The action following request denial.
     */
    public abstract void denyAction();

    /**
     * Save the object.
     */
    protected void save(){
        RequestRepositoryController.getInstance().save();
    }
}
