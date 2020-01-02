package models.requests;

import models.repositories.I_RepositoryItem;

/**
 * Template pattern for a request as approval/denial end in the request being deleted from the appropriate repository.
 */
public abstract class Request
    implements I_RepositoryItem {

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
    protected abstract void approveAction() throws Exception;

    /**
     * The action following request denial.
     */
    protected abstract void denyAction();
}
