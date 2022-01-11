package controller;

import java.util.ArrayList;
import java.util.List;

import integration.BankDAO;
import integration.BankDBException;
import model.Instrument;
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
    */
   public void createAccount(int studentId, int instrumentId) throws AccountException {
       String failureMsg = "Could not create new rental for: " + studentId;

       if (studentId == 0) {
           throw new AccountException(failureMsg);
       }

       try {
           
           Instrument instrument = new Instrument(studentId, instrumentId, failureMsg, instrumentId);
           bankDb.createAccount(studentId, instrumentId);
           bankDb.updateRental222(instrument);
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

//    /**
//     * Lists all accounts owned by the specified account holder.
//     * 
//     * @param holderName The holder who's accounts shall be listed.
//     * @return A list with all accounts owned by the specified holder. The list is
//     *         empty if the holder does not have any accounts, or if there is no
//     *         such holder.
//     * @throws AccountException If unable to retrieve the holder's accounts.
//     */
//    public List<? extends InstrumentDTO> getAccountsForHolder(String holderName) throws AccountException {
//        if (holderName == null) {
//            return new ArrayList<>();
//        }
//
//        try {
//            return bankDb.findAccountsByHolder(holderName);
//        } catch (Exception e) {
//            throw new AccountException("Could not search for account.", e);
//        }
//    }
//
//    /**
//     * Retrieves the account with the specified number.
//     * 
//     * @param acctNo The number of the searched account.
//     * @return The account with the specified account number, or <code>null</code>
//     *         if there is no such account.
//     * @throws AccountException If unable to retrieve the account.
//     */
//    public InstrumentDTO getAccount(String acctNo) throws AccountException {
//        if (acctNo == null) {
//            return null;
//        }
//
//        try {
//            return bankDb.findAccountByAcctNo(acctNo, false);
//        } catch (Exception e) {
//            throw new AccountException("Could not search for account.", e);
//        }
//    }
//
//    /**
//     * Deposits the specified amount to the account with the specified account
//     * number.
//     * 
//     * @param acctNo The number of the account to which to deposit.
//     * @param amt    The amount to deposit.
//     * @throws RejectedException If not allowed to deposit the specified amount.
//     * @throws AccountException  If failed to deposit.
//     */
   public void deposit(int instrumentId) throws RejectedException, AccountException {
       String failureMsg = "Could not deposit to account: " + instrumentId;

       if (instrumentId == 0) {
           throw new AccountException(failureMsg);
       }

       try {
           Instrument rental = bankDb.findAccountByAcctNo(instrumentId);
           rental.deposit();
           
           bankDb.updateRental(rental);
       } catch (BankDBException bdbe) {
           throw new AccountException(failureMsg, bdbe);
       } catch (Exception e) {
           commitOngoingTransaction(failureMsg);
           throw e;
       }
   }
//
//    /**
//     * Withdraws the specified amount from the account with the specified account
//     * number.
//     * 
//     * @param acctNo The number of the account from which to withdraw.
//     * @param amt    The amount to withdraw.
//     * @throws RejectedException If not allowed to withdraw the specified amount.
//     * @throws AccountException  If failed to withdraw.
//     */
//    public void withdraw(String acctNo, int amt) throws RejectedException, AccountException {
//        String failureMsg = "Could not withdraw from account: " + acctNo;
//
//        if (acctNo == null) {
//            throw new AccountException(failureMsg);
//        }
//
//        try {
//            Instrument acct = bankDb.findAccountByAcctNo(acctNo, true);
//            acct.withdraw(amt);
//            bankDb.updateAccount(acct);
//        } catch (BankDBException bdbe) {
//            throw new AccountException(failureMsg, bdbe);
//        } catch (Exception e) {
//            commitOngoingTransaction(failureMsg);
//            throw e;
//        }
//    }

   private void commitOngoingTransaction(String failureMsg) throws AccountException {
       try {
           bankDb.commit();
       } catch (BankDBException bdbe) {
           throw new AccountException(failureMsg, bdbe);
       }
   }

//    /**
//     * Deletes the account with the specified account number.
//     * 
//     * @param acctNo The number of the account that shall be deleted.
//     * @throws AccountException If failed to delete the specified account.
//     */
//    public void deleteAccount(String acctNo) throws AccountException {
//        String failureMsg = "Could not delete account: " + acctNo;
//
//        if (acctNo == null) {
//            throw new AccountException(failureMsg);
//        }
//
//        try {
//            bankDb.deleteAccount(acctNo);
//        } catch (Exception e) {
//            throw new AccountException(failureMsg, e);
//        }
//    }
}