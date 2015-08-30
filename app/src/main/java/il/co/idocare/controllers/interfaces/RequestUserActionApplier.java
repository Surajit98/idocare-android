package il.co.idocare.controllers.interfaces;

import il.co.idocare.pojos.RequestItem;
import il.co.idocare.pojos.UserActionItem;

/**
 * Created by Vasiliy on 7/5/2015.
 */
public interface RequestUserActionApplier {

    /**
     * Alter the contents of the RequestItem object in accordance with the information provided
     * by the UserActionItem object
     * @param request the object to be affected. Must not be null.
     * @param userAction the "affecting" object. Must not be null. It is the responsibility of the
     *                   caller to ensure that this UserActionItem should affect the provided
     *                   RequestItem
     * @return a reference to the altered RequestItem, which is the same object passed as a
     *         parameter to this method. The returned reference is just for convenience of
     *         "chaining" method calls.
     */
    public RequestItem applyUserAction(RequestItem request, UserActionItem userAction);
}