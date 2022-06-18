/*
 * The MIT License (MIT)
 * Copyright (c) 2020 Leif Lindbäck
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction,including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so,subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package controller;

import java.util.List;

import integration.BankDAO;
import integration.BankDBException;
import model.InstrumentDTO;
import model.AccountException;
import model.RejectedException;

/**
 * This is the application's only controller, all calls to the model pass here.
 * The controller is also responsible for calling the DAO. Typically, the
 * controller first calls the DAO to retrieve data (if needed), then operates on
 * the data, and finally tells the DAO to store the updated data (if any).
 */
public class Controller {
    private final BankDAO bankDb;

    /**
     * Creates a new instance, and retrieves a connection to the database.
     * 
     * @throws BankDBException If unable to connect to the database.
     */
    public Controller() throws BankDBException {
        bankDb = new BankDAO();
    }

    /**
     * Creates a new account for the specified account holder.
     * 
     * @param studentId The account holder's name.
     * @throws AccountException If unable to create account.
     *                          här måste ha @instrument type
     */
    public void createAccount(int studentId, int instrumentId) throws AccountException {
        String failureMsg = "Could not create new rental for: " + studentId;

        if (studentId == 0) {
            throw new AccountException(failureMsg);
        }

        try {




            bankDb.createAccount(studentId, instrumentId);


        } catch (Exception e) {
            throw new AccountException(failureMsg, e);
        }
    }

    /**
     * Lists all accounts in the whole bank.
     * 
     * @return A list containing all accounts. The list is empty if there are no
     *         accounts.
     * @throws AccountException If unable to retrieve accounts.
     */
    public List<? extends InstrumentDTO> getAllInstruments() throws AccountException {
        try {
            return bankDb.findAllInstruments();
        } catch (Exception e) {
            throw new AccountException("Unable to list instruments.", e);
        }
    }

    /**
     * Lists all accounts in the whole bank.
     * 
     * @return A list containing all accounts. The list is empty if there are no
     *         accounts.
     * @throws AccountException If unable to retrieve accounts.
     */
    public List<? extends InstrumentDTO> getAllEnsembles() throws AccountException {
        try {
            return bankDb.findAllEnsembles();
        } catch (Exception e) {
            throw new AccountException("Unable to list ensembles.", e);
        }
    }

    public List<? extends InstrumentDTO> getAllRentals() throws AccountException {
        try {
            return bankDb.findAllRentals();
        } catch (Exception e) {
            throw new AccountException("Unable to list rentals.", e);
        }
    }


    // /**
    // * Deposits the specified amount to the account with the specified account
    // * number.
    // *
    // * @param acctNo The number of the account to which to deposit.
    // * @param amt The amount to deposit.
    // * @throws RejectedException If not allowed to deposit the specified amount.
    // * @throws AccountException If failed to deposit.
    // */
    public void deposit(int studentId ,int instrumentId) throws RejectedException, AccountException {
        String failureMsg = "Could not deposit to account: " + instrumentId;

        if (instrumentId == 0) {
            throw new AccountException(failureMsg);
        }

        try {
            //Instrument rental = bankDb.findAccountByAcctNo5(instrumentId);//
            bankDb.findTerminated(studentId, instrumentId);
            bankDb.terminated(studentId, instrumentId);

        } catch (BankDBException bdbe) {
            throw new AccountException(failureMsg, bdbe);
        } catch (Exception e) {
            commitOngoingTransaction(failureMsg);
            throw e;
        }
    }

    private void commitOngoingTransaction(String failureMsg) throws AccountException {
        try {
            bankDb.commit();
        } catch (BankDBException bdbe) {
            throw new AccountException(failureMsg, bdbe);
        }
    }

}
