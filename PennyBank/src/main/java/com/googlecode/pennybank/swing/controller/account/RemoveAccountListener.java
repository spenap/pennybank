/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.googlecode.pennybank.swing.controller.account;

import com.googlecode.pennybank.model.account.entity.Account;
import com.googlecode.pennybank.model.accountfacade.delegate.AccountFacadeDelegate;
import com.googlecode.pennybank.model.accountfacade.delegate.AccountFacadeDelegateFactory;
import com.googlecode.pennybank.model.util.exceptions.InstanceNotFoundException;
import com.googlecode.pennybank.model.util.exceptions.InternalErrorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author spenap
 */
public class RemoveAccountListener implements ActionListener {

    private Account theAccount=null;

    public RemoveAccountListener() {
    }

    public RemoveAccountListener(Account theAccount) {
        this.theAccount = theAccount;
    }

    public void actionPerformed(ActionEvent e) {
        if (theAccount == null) {
            //TODO Select an account to delete
        } else {
            try {
                AccountFacadeDelegate accountFacade =
                        AccountFacadeDelegateFactory.getDelegate();
                accountFacade.deleteAccount(theAccount.getAccountId());
            } catch (InstanceNotFoundException ex) {
                Logger.getLogger(RemoveAccountListener.class.getName()).
                        log(Level.SEVERE, null, ex);
            } catch (InternalErrorException ex) {
                Logger.getLogger(RemoveAccountListener.class.getName()).
                        log(Level.SEVERE, null, ex);
            }
        }
    }
}
