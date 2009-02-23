package com.googlecode.pennybank.model.userfacade.hibernate;

import java.util.List;

import com.googlecode.pennybank.model.user.entity.User;
import com.googlecode.pennybank.model.userfacade.delegate.UserFacadeDelegate;
import com.googlecode.pennybank.model.userfacade.hibernate.actions.CreateUserAction;
import com.googlecode.pennybank.model.userfacade.hibernate.actions.DeleteAction;
import com.googlecode.pennybank.model.userfacade.hibernate.actions.FindAction;
import com.googlecode.pennybank.model.userfacade.hibernate.actions.FindAllAction;
import com.googlecode.pennybank.model.util.exceptions.InstanceNotFoundException;
import com.googlecode.pennybank.model.util.exceptions.InternalErrorException;
import com.googlecode.pennybank.model.util.exceptions.ModelException;
import com.googlecode.pennybank.model.util.facade.HibernateFacade;
import com.googlecode.pennybank.model.util.transactions.PlainActionProcessor;

/**
 * An UserFacade implementation, using Hibernate
 * 
 * @author spenap
 */
public class UserFacade extends HibernateFacade implements UserFacadeDelegate {

    public User createUser(User user)
            throws InternalErrorException {
        CreateUserAction action = new CreateUserAction(user);
        User result;
        try {
            result = (User) PlainActionProcessor.process(entityManager, action);
            return result;
        } catch (ModelException e) {
            throw new InternalErrorException(e);
        }

    }

    public void deleteUser(Long userId)
            throws InstanceNotFoundException,
            InternalErrorException {
        DeleteAction action = new DeleteAction(userId);
        try {
            PlainActionProcessor.process(entityManager, action);
        } catch (InstanceNotFoundException e) {
            throw e;
        } catch (ModelException e) {
            throw new InternalErrorException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public List<User> findUsers()
            throws InternalErrorException {
        FindAllAction action = new FindAllAction();
        try {
            List<User> userList = (List<User>) PlainActionProcessor.process(
                    entityManager, action);
            return userList;
        } catch (ModelException e) {
            throw new InternalErrorException(e);
        }
    }

    public User find(Long userId)
            throws InstanceNotFoundException,
            InternalErrorException {
        FindAction action = new FindAction(userId);
        User result;
        try {
            result = (User) PlainActionProcessor.process(entityManager, action);
            return result;
        } catch (InstanceNotFoundException e) {
            throw e;
        } catch (ModelException e) {
            throw new InternalErrorException(e);
        }
    }
}
