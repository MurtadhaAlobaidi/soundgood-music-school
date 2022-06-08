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
package integration;
import java.time.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
import java.util.Calendar;


import model.Instrument;
import model.InstrumentDTO;

/**
* This data access object (DAO) encapsulates all database calls in the bank
* application. No code outside this class shall have any knowledge about the
* database.
*/
public class BankDAO {
   // instrument
   private static final String INSTRUMENT_TABLE_NAME = "instrument";
   private static final String INSTRUMENT_ID_COLUMN_NAME = "instrument_id";
   private static final String TYPE_COLUMN_NAME = "type";
   private static final String BRAND_COLUMN_NAME = "brand";
   private static final String RENTING_FEE_COLUMN_NAME = "renting_fee";
   private static final String STATUS_COLUMN_NAME = "status";
   
   // student
   private static final String STUDENT_TABLE_NAME = "student";
   private static final String STUDENT_ID_COLUMN_NAME = "student_id";
   private static final String MEMBER_ID_COLUMN_NAME = "member_id";
   private static final String PERSON_ID_COLUMN_NAME = "person_id";
   private static final String TOTAL_RENTED_INSTRUMENTS_CURRENTLY_COLUMN_NAME = "total_rented_instruments_currently";
   
   // rented_instruments
   private static final String RENTED_INSTRUMENTS_TABLE_NAME = "rented_instruments";
   private static final String STUDENT_ID_FK_COLUMN_NAME = STUDENT_ID_COLUMN_NAME;
   private static final String PERSON_ID_FK_COLUMN_NAME = PERSON_ID_COLUMN_NAME;
   private static final String RENTING_STATUS_COLUMN_NAME = "renting_status";
   private static final String RENT_DATE_COLUMN_NAME = "rent_date";
   private static final String RETURN_DATE_COLUMN_NAME = "return_date";

   //ensemble_statistics
   private static final String ENSEMBLE_STATISTICS_TABLE_NAME = "ensemble_statistics";
   private static final String GENRE_COLUMN_NAME = "genre";
   private static final String TYPE_OF_LESSON_COLUMN_NAME = "type_of_lesson";
   private static final String ENSEMBLE_STATUS_COLUMN_NAME = "status";
   private static final String MAX_NUM_OF_STUDENTS_COLUMN_NAME = "max_num_of_students";
   private static final String MIN_NUM_OF_STUDENTS_COLUMN_NAME = "min_num_of_students";
   private static final String YEAR_COLUMN_NAME = "year";
   private static final String WEEK_NUMBER_COLUMN_NAME = "week_number";
   private static final String DAY_COLUMN_NAME = "day";
   
   //rented_instruments_info
   private static final String RENTED_INSTRUMENTS_INFO_TABLE_NAME = "rented_instruments_info";
   private static final String STUDENT_NAME_COLUMN_NAME = "student_name";
   private static final String RENTAL_STATUS_COLUMN_NAME = "renting_status";

   //terminated_task
   private static final String TERMINATED_TASK_TABLE_NAME = "terminated_task";

   
   
   
   

     private Connection connection;
//    private PreparedStatement createHolderStmt;
   private PreparedStatement findHolderPKStmt;
   private PreparedStatement findHolderPK2Stmt;

   private PreparedStatement createAccountStmt;
//    private PreparedStatement findAccountByNameStmt;
     private PreparedStatement findAccountByAcctNoStmt;
     private PreparedStatement findAllRentalsStmt;
     private PreparedStatement findAllInstrumentsStmt;
     private PreparedStatement findAllEnsemblesStmt;
     private PreparedStatement changeStatus2Stmt;
     private PreparedStatement changeStatusStmt;
     private PreparedStatement changeStatus3Stmt;
     private PreparedStatement changeStmt;
     private PreparedStatement change2Stmt;



   /**
    * Constructs a new DAO object connected to the bank database.
    */
   public BankDAO() throws BankDBException {
       try {
           connectToBankDB();
           prepareStatements();
       } catch (ClassNotFoundException | SQLException exception) {
           throw new BankDBException("Could not connect to datasource.", exception);
       }
   }

//    /**
//     * Creates a new account.
//     *
//     * @param account The account to create.
//     * @throws BankDBException If failed to create the specified account.
//     */
   public void createAccount(int studentId, int instrumentId) throws BankDBException {
       String failureMsg = "Could not create the renatl of: " ;
       int updatedRows = 0;
       try {
           Calendar date = Calendar.getInstance();
           long millisecondsDate = date.getTimeInMillis();
           createAccountStmt.setInt(1, studentId);
           createAccountStmt.setInt(2, instrumentId);
           createAccountStmt.setString(3, "Ongoing");
           createAccountStmt.setDate(4, new Date(millisecondsDate));
           createAccountStmt.setDate(5, new Date(millisecondsDate+15778800000L));
           updatedRows = createAccountStmt.executeUpdate();
        //    Instrument instrument = new Instrument(studentId, instrumentId,);
           findHolderPKByName(studentId, instrumentId);
           if (updatedRows != 1) {
               handleException(failureMsg, null);
           }
       
           connection.commit();
       } catch (SQLException sqle) {
           handleException(failureMsg, sqle);
       }
   }

//    /**
//     * Searches for the account with the specified account number.
//     *
//     * @param acctNo The account number.
//     * @param lockExclusive If true, it will not be possible to perform UPDATE 
//     *                      or DELETE statements on the selected row in the
//     *                      current transaction. Also, the transaction will not
//     *                      be committed when this method returns. If false, no
//     *                      exclusive locks will be created, and the transaction will
//     *                      be committed when this method returns.
//     * @return The account with the specified account number, or <code>null</code> if 
//     *         there is no such account.
//     * @throws BankDBException If failed to search for the account.
//     */
   //terminate
   public Instrument findAccountByAcctNo(int instrumentId)
                                      throws BankDBException {
   PreparedStatement stmtToExecute;
           stmtToExecute = findAccountByAcctNoStmt;

       String failureMsg = "Could not search for specified rental.";
       ResultSet result = null;
       try {
           stmtToExecute.setInt(1, instrumentId);
           result = stmtToExecute.executeQuery();
           if (result.next()) {
               return new Instrument(result.getInt(INSTRUMENT_ID_COLUMN_NAME),
                       result.getInt(STUDENT_ID_COLUMN_NAME),
                           result.getString(STATUS_COLUMN_NAME),
                           result.getString(RENTING_STATUS_COLUMN_NAME),
                           result.getInt(TOTAL_RENTED_INSTRUMENTS_CURRENTLY_COLUMN_NAME));
           }
       connection.commit();
       } catch (SQLException sqle) {
           handleException(failureMsg, sqle);
       } finally {
           closeResultSet(failureMsg, result);
       }
       return null;
   }

//    /**
//     * Searches for all accounts whose holder has the specified name.
//     *
//     * @param holderName The account holder's name
//     * @return A list with all accounts whose holder has the specified name, 
//     *         the list is empty if there are no such account.
//     * @throws BankDBException If failed to search for accounts.
//     */
//    public List<Instrument> findAccountsByHolder(String holderName) throws BankDBException {
//        String failureMsg = "Could not search for specified accounts.";
//        ResultSet result = null;
//        List<Instrument> accounts = new ArrayList<>();
//        try {
//            findAccountByNameStmt.setString(1, holderName);
//            result = findAccountByNameStmt.executeQuery();
//            while (result.next()) {
//                accounts.add(new Instrument(result.getString(ACCT_NO_COLUMN_NAME),
//                                         result.getString(HOLDER_COLUMN_NAME),
//                                         result.getInt(BALANCE_COLUMN_NAME)));
//            }
//            connection.commit();
//        } catch (SQLException sqle) {
//            handleException(failureMsg, sqle);
//        } finally {
//            closeResultSet(failureMsg, result);
//        }
//        return accounts;
//    }

   /**
    * Retrieves all existing accounts.
    *
    * @return A list with all existing accounts. The list is empty if there are no
    *         accounts.
    * @throws BankDBException If failed to search for accounts.
    */
   public List<Instrument> findAllInstruments() throws BankDBException {
       String failureMsg = "Could not list instruments.";
       List<Instrument> instruments = new ArrayList<>();
       try (ResultSet result = findAllInstrumentsStmt.executeQuery()) {
           while (result.next()) {
               instruments.add(new Instrument(result.getString(TYPE_COLUMN_NAME),
                                              result.getString(RENTING_FEE_COLUMN_NAME),
                                              result.getInt(INSTRUMENT_ID_COLUMN_NAME),
                                              result.getString(BRAND_COLUMN_NAME),
                                              result.getString(STATUS_COLUMN_NAME)));
           }
           connection.commit();
       } catch (SQLException sqle) {
           handleException(failureMsg, sqle);
       }
       return instruments;
   }
   
   
   //ensemble_statistics
   /**
    * Retrieves all existing accounts.
    *
    * @return A list with all existing accounts. The list is empty if there are no
    *         accounts.
    * @throws BankDBException If failed to search for accounts.
    */
   public List<Instrument> findAllEnsembles() throws BankDBException {
       String failureMsg = "Could not list ensembles.";
       List<Instrument> ensembles = new ArrayList<>();
       try (ResultSet result = findAllEnsemblesStmt.executeQuery()) {
           while (result.next()) {
               ensembles.add(new Instrument(result.getString(GENRE_COLUMN_NAME),
                                            result.getInt(MAX_NUM_OF_STUDENTS_COLUMN_NAME),
                                            result.getInt(MIN_NUM_OF_STUDENTS_COLUMN_NAME),
                                            result.getInt(YEAR_COLUMN_NAME),
                                            result.getInt(WEEK_NUMBER_COLUMN_NAME),
                                            result.getInt(DAY_COLUMN_NAME),
                                            result.getString(ENSEMBLE_STATUS_COLUMN_NAME)));
           } 
           connection.commit();
       } catch (SQLException sqle) {
           handleException(failureMsg, sqle);
       }
       return ensembles;
   }
   
   
   public List<Instrument> findAllRentals() throws BankDBException {
       String failureMsg = "Could not list rentals.";
       List<Instrument> rentals = new ArrayList<>();
       try (ResultSet result = findAllRentalsStmt.executeQuery()) {
           while (result.next()) {
               rentals.add(new Instrument(result.getInt(INSTRUMENT_ID_COLUMN_NAME),
                                            result.getString(RENTING_FEE_COLUMN_NAME),
                                            result.getString(TYPE_COLUMN_NAME),
                                            result.getString(BRAND_COLUMN_NAME),
                                            result.getString(STUDENT_NAME_COLUMN_NAME),
                                            result.getString(RENT_DATE_COLUMN_NAME),
                                            result.getString(RETURN_DATE_COLUMN_NAME),
                                            result.getString(RENTAL_STATUS_COLUMN_NAME)));
           } 
           connection.commit();
       } catch (SQLException sqle) {
           handleException(failureMsg, sqle);
       }
       return rentals;
   }
   
   
   

   /**
    * Changes the balance of the account with the number of the specified
    * <code>InstrumentDTO</code> object. The balance is set to the value in the specified
    * <code>InstrumentDTO</code>.
    *
    * @param account The account to update.
    * @throws BankDBException If unable to update the specified account.
    */
   public void updateRental(InstrumentDTO rental) throws BankDBException {
       String failureMsg = "Could not update the rental: " + rental;
       try {
           changeStatusStmt.setInt(2, rental.getInstrumentId());
           changeStatusStmt.setString(1, rental.getRentingStatus());
           int updatedRows = changeStatusStmt.executeUpdate();
           if (updatedRows != 1) {
               handleException(failureMsg, null);
           }
           changeStatus2Stmt.setInt(2, rental.getInstrumentId());
           changeStatus2Stmt.setString(1, rental.getStatus());
           int updatedRows2 = changeStatus2Stmt.executeUpdate();
           if (updatedRows2 != 1) {
               handleException(failureMsg, null);
           }
           changeStatus3Stmt.setInt(2, rental.getStudentId());
           changeStatus3Stmt.setInt(1, rental.getTotalRentedInstrumentsCurrently());
           int updatedRows3 = changeStatus3Stmt.executeUpdate();
           if (updatedRows3 != 1) {
               handleException(failureMsg, null);
           }
           connection.commit();
       } catch (SQLException sqle) {
           handleException(failureMsg, sqle);
       }
   }
   
   
   public void updateRental222(InstrumentDTO rental) throws BankDBException {
       String failureMsg = "Could not update the database: " + rental;
       try {
           changeStmt.setInt(2, rental.getStudentId());
           changeStmt.setInt(1, rental.getTotalRentedInstrumentsCurrently());
           int updatedRows3 = changeStatusStmt.executeUpdate();
           if (updatedRows3 != 1) {
               handleException(failureMsg, null);
           }
           change2Stmt.setInt(2, rental.getInstrumentId());
           change2Stmt.setString(1, rental.getStatus());
           int updatedRows4 = changeStatus2Stmt.executeUpdate();
           if (updatedRows4 != 1) {
               handleException(failureMsg, null);
           }
           connection.commit();
       } catch (SQLException sqle) {
           handleException(failureMsg, sqle);
       }
   }
//
//    /**
//     * Deletes the account with the specified account number.
//     *
//     * @param acctNo The account to delete.
//     * @throws BankDBException If unable to delete the specified account.
//     */
//    public void deleteAccount(String acctNo) throws BankDBException {
//        String failureMsg = "Could not delete account: " + acctNo;
//        try {
//            changeStatus2Stmt.setString(1, acctNo);
//            int updatedRows = changeStatus2Stmt.executeUpdate();
//            if (updatedRows != 1) {
//                handleException(failureMsg, null);
//            }
//            connection.commit();
//        } catch (SQLException sqle) {
//            handleException(failureMsg, sqle);
//        }
//    }

   /**
    * Commits the current transaction.
    * 
    * @throws BankDBException If unable to commit the current transaction.
    */
   public void commit() throws BankDBException {
       try {
           connection.commit();
       } catch (SQLException e) {
           handleException("Failed to commit", e);
       }
   }

   private void connectToBankDB() throws ClassNotFoundException, SQLException {
       connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/soundgood_music_school",
                                                "postgres", "pinkod");

       connection.setAutoCommit(false);
   }

   private void prepareStatements() throws SQLException {

       
       
       createAccountStmt = connection.prepareStatement("INSERT INTO " + RENTED_INSTRUMENTS_TABLE_NAME
           + "(" + STUDENT_ID_COLUMN_NAME + ", " + INSTRUMENT_ID_COLUMN_NAME + ", "
           + RENTING_STATUS_COLUMN_NAME  + ", " + RENT_DATE_COLUMN_NAME + ", " + RETURN_DATE_COLUMN_NAME + ") VALUES (?, ?, ?, ?, ?) ");
       
       

      findHolderPKStmt = connection.prepareStatement("SELECT student." + TOTAL_RENTED_INSTRUMENTS_CURRENTLY_COLUMN_NAME + " FROM " + STUDENT_TABLE_NAME + 
               " WHERE " + STUDENT_ID_COLUMN_NAME + " = ?");
      
      findHolderPK2Stmt = connection.prepareStatement("SELECT instrument." + TOTAL_RENTED_INSTRUMENTS_CURRENTLY_COLUMN_NAME + " FROM " + INSTRUMENT_TABLE_NAME + 
              " WHERE " + INSTRUMENT_ID_COLUMN_NAME + " = ?");

       
      
      
       findAllRentalsStmt = connection.prepareStatement("SELECT " 
       + RENT_DATE_COLUMN_NAME
     + ", " + RENTING_FEE_COLUMN_NAME
     + ", " + TYPE_COLUMN_NAME 
     + ", " + BRAND_COLUMN_NAME
     + ", " + INSTRUMENT_ID_COLUMN_NAME
     + ", " + STUDENT_NAME_COLUMN_NAME
     + ", " + RETURN_DATE_COLUMN_NAME
     + ", " + RENTAL_STATUS_COLUMN_NAME
     + " FROM "
     + RENTED_INSTRUMENTS_INFO_TABLE_NAME);
       
       


         findAllInstrumentsStmt = connection.prepareStatement("SELECT * FROM " + INSTRUMENT_TABLE_NAME + " WHERE " + STATUS_COLUMN_NAME + " = 'available'");

         findAllEnsemblesStmt = connection.prepareStatement("SELECT * FROM " + ENSEMBLE_STATISTICS_TABLE_NAME);
         
         //terminate
         findAccountByAcctNoStmt = connection.prepareStatement("SELECT * FROM " + TERMINATED_TASK_TABLE_NAME + 
         " WHERE " + INSTRUMENT_ID_COLUMN_NAME + " = ?");
         
         
       changeStatusStmt = connection.prepareStatement("UPDATE " + RENTED_INSTRUMENTS_TABLE_NAME
           + " SET " + RENTING_STATUS_COLUMN_NAME + " = ? WHERE " + INSTRUMENT_ID_COLUMN_NAME + " = ? ");
       
       changeStatus2Stmt = connection.prepareStatement("UPDATE " + INSTRUMENT_TABLE_NAME
               + " SET " + STATUS_COLUMN_NAME + " = ? WHERE " + INSTRUMENT_ID_COLUMN_NAME + " = ? ");
       
       changeStatus3Stmt = connection.prepareStatement("UPDATE " + STUDENT_TABLE_NAME
               + " SET " + TOTAL_RENTED_INSTRUMENTS_CURRENTLY_COLUMN_NAME + " = ? WHERE " + STUDENT_ID_COLUMN_NAME + " = ? ");
       
       changeStmt = connection.prepareStatement("UPDATE " + STUDENT_TABLE_NAME
               + " SET " + TOTAL_RENTED_INSTRUMENTS_CURRENTLY_COLUMN_NAME + " = ? WHERE " + STUDENT_ID_COLUMN_NAME + " = ? ");
       
       change2Stmt = connection.prepareStatement("UPDATE " + INSTRUMENT_TABLE_NAME
               + " SET " + STATUS_COLUMN_NAME + " = ? WHERE " + INSTRUMENT_ID_COLUMN_NAME + " = ? ");
   }
   
   
   
   private void handleException(String failureMsg, Exception cause) throws BankDBException {
       String completeFailureMsg = failureMsg;
       try {
           connection.rollback();
       } catch (SQLException rollbackExc) {
           completeFailureMsg = completeFailureMsg + 
           ". Also failed to rollback transaction because of: " + rollbackExc.getMessage();
       }

       if (cause != null) {
           throw new BankDBException(failureMsg, cause);
       } else {
           throw new BankDBException(failureMsg);
       }
   }

   private void closeResultSet(String failureMsg, ResultSet result) throws BankDBException {
       try {
           result.close();
       } catch (Exception e) {
           throw new BankDBException(failureMsg + " Could not close result set.", e);
       }
   }

//    private int createAccountNo() {
//        return (int)Math.floor(Math.random() * Integer.MAX_VALUE);
//    }
//
   private void findHolderPKByName(int studentId, int instrumentId) throws SQLException {
       findHolderPKStmt.setInt(1, studentId);
       findHolderPK2Stmt.setInt(1, instrumentId);
       findHolderPKStmt.executeQuery();
       findHolderPK2Stmt.executeQuery();

   }
}
