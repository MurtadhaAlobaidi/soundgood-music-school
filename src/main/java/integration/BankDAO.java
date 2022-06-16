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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.*;
import java.util.Iterator;
import java.util.List;

import model.Instrument;
import model.InstrumentDTO;
import java.sql.Date;
import model.RejectedException;

/**
 * This data access object (DAO) encapsulates all database calls in the bank
 * application. No code outside this class shall have any knowledge about the
 * database.
 */
public class BankDAO {
    // instrument
    private static final String INSTRUMENT_TABLE_NAME = "instrument"; // instrument
    private static final String INSTRUMENT_ID_COLUMN_NAME = "instrument_id";
    private static final String TYPE_COLUMN_NAME = "type";
    private static final String BRAND_COLUMN_NAME = "brand";
    private static final String RENTING_FEE_COLUMN_NAME = "renting_fee";
    private static final String STATUS_COLUMN_NAME = "status";

    //INSTRUMENT_TABLE_NAME = "instrument"; //Table
    //INSTRUMENT_ID_COLUMN_NAME = "instrument_id"; //Columns
    //STATUS_COLUMN_NAME = "status"; //Columns
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

    // ensemble_statistics
    private static final String ENSEMBLE_STATISTICS_TABLE_NAME = "ensemble_statistics";
    private static final String GENRE_COLUMN_NAME = "genre";
    private static final String TYPE_OF_LESSON_COLUMN_NAME = "type_of_lesson";
    private static final String ENSEMBLE_STATUS_COLUMN_NAME = "status";
    private static final String MAX_NUM_OF_STUDENTS_COLUMN_NAME = "max_num_of_students";
    private static final String MIN_NUM_OF_STUDENTS_COLUMN_NAME = "min_num_of_students";
    private static final String YEAR_COLUMN_NAME = "year";
    private static final String WEEK_NUMBER_COLUMN_NAME = "week_number";
    private static final String DAY_COLUMN_NAME = "day";

    // rented_instruments_info
    private static final String RENTED_INSTRUMENTS_INFO_TABLE_NAME = "rented_instruments_info";
    private static final String STUDENT_NAME_COLUMN_NAME = "student_name";
    private static final String RENTAL_STATUS_COLUMN_NAME = "renting_status";

    // terminated_task
    private static final String TERMINATED_TASK_TABLE_NAME = "terminated_task"; // terminated_task

    private Connection connection;
    // private PreparedStatement createHolderStmt;
    private PreparedStatement findHolderPKStmt;
    private PreparedStatement findHolderPK2Stmt;

    private PreparedStatement createAccountStmt;
    private PreparedStatement updateAccountStmt;
    private PreparedStatement changeStatusrentedStmt;
    private PreparedStatement findAccountStmt;

    private PreparedStatement createAccountInfoStmt;
    // private PreparedStatement findAccountByNameStmt;
    private PreparedStatement findAccountByAcctNoStmt;
    //Hitta status from terminatet task
    private PreparedStatement findAccountByAcctNo1Stmt;
    private PreparedStatement findAllRentalsStmt;
    private PreparedStatement findAllInstrumentsStmt;
    private PreparedStatement findAllEnsemblesStmt;
    private PreparedStatement changeStatus2Stmt;
    private PreparedStatement changeStatusStmt;
    private PreparedStatement changeStatus3Stmt;
    private PreparedStatement changeStmt;
    private PreparedStatement change2Stmt;

    //Student
    private PreparedStatement findStudentStmt;
    private PreparedStatement findStudent1Stmt;
    //Instrument
    private PreparedStatement findInstrumentStatusStmt;
    private PreparedStatement updateNumber;
    private PreparedStatement showQuota;
    private PreparedStatement updateQuota;
    private PreparedStatement makeRentInstrument;
    //public List<Instrument> instruments;

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

    // /**
    // * Creates a new account.
    // *
    // * @param account The account to create.
    // * @throws BankDBException If failed to create the specified account.
    // */
    /*
    public void createAccount(int studentId, int instrumentId) throws BankDBException {
        String failureMsg = "Could not create the renatl of: ";
        int updatedRows = 0;

        try {
            Calendar date = Calendar.getInstance();
            long millisecondsDate = date.getTimeInMillis();
            createAccountStmt.setInt(1, studentId);
            createAccountStmt.setInt(2, instrumentId);
            createAccountStmt.setString(3, "Ongoing");
            createAccountStmt.setDate(4, new Date(millisecondsDate));
            createAccountStmt.setDate(5, new Date(millisecondsDate + 15778800000L));

            // Instrument instrument = new Instrument(studentId,
            // instrumentId,"rented","Ongoing", 1);
            // Instrument instrument = new Instrument(studentId, instrumentId);
            // update();

            updatedRows = createAccountStmt.executeUpdate();

            findHolderPKByName(studentId, instrumentId);

            if (updatedRows != 1) {
                handleException(failureMsg, null);
            }

            connection.commit();
        } catch (SQLException sqle) {
            handleException(failureMsg, sqle);
        }
    }*/

    public void createAccount(int studentId, int instrumentId) throws BankDBException {
        String failureMsg = "Could not create the renatl of: ";
        int updatedRows1 = 1;
        ResultSet result = null;
        int checker = 0;
        ResultSet result11 = null;


        try {

            if (findAllInstruments().size()==0){
                System.out.println("All instruments rented");
            }

            //Student
            findStudentStmt.setInt(1, studentId);
            result = findStudentStmt.executeQuery();
            while(result.next())
                checker = result.getInt(4);
            if(checker == 2 && updatedRows1 == 1){

                System.out.println("You have reached the maximum allowed number of renatls, you can return one of your erliear rentals to complete.");
                handleException(failureMsg, null);
            }


            findInstrumentStatusStmt.setInt(1, instrumentId);
            result = findInstrumentStatusStmt.executeQuery();
            String checkerStatus = null;
            while(result.next())
                checkerStatus = result.getString(5);
            if(checkerStatus == "rented" && updatedRows1 == 1){
                System.out.println("Instrument is rented");
                handleException(failureMsg, null);
            }

            String s =findAccountByAcctNo(instrumentId).getRentingStatus();
            if(s.contains("Ongoing")){


            //rented Instrument
            createAccountStmt.setInt(1,studentId);
            createAccountStmt.setInt(2,instrumentId);
            createAccountStmt.setString(3, "Ongoing");

            int updatedRows4 = createAccountStmt.executeUpdate();
            if (updatedRows4 != 1) {
                handleException(failureMsg, null);
            }

            // instrument tables
            changeStatus2Stmt.setInt(2, instrumentId);
            changeStatus2Stmt.setString(1, "rented");
            int updatedRows6 = changeStatus2Stmt.executeUpdate();
            if (updatedRows6 != 1) {
                handleException(failureMsg, null);
            }

            //add to student
            changeStatus3Stmt.setInt(1,checker + 1 );
            changeStatus3Stmt.setInt(2, studentId);
            int updatedRows3 = changeStatus3Stmt.executeUpdate();
            if (updatedRows3 != 1) {
                handleException(failureMsg, null);
            }
            }

            else {
                try2(studentId, instrumentId);
                }

            connection.commit();

        } catch (SQLException sqle) {
            handleException(failureMsg, sqle);
        }
    }

    public void try2(int studentId, int instrumentId) throws BankDBException {
        String failureMsg = "Could not search for specified rental.";
        ResultSet result2 = null;
        int updatedRows2 =1;
        int checker2 = 0;

        try {
            findStudentStmt.setInt(1, studentId);
            result2 = findStudentStmt.executeQuery();
            while(result2.next())
                checker2 = result2.getInt(4);
            if(checker2 == 2 && updatedRows2 == 1){

                System.out.println("You have reached the maximum allowed number of renatls, you can return one of your erliear rentals to complete.");
                handleException(failureMsg, null);
            }

            //rented instrument
            updateAccountStmt.setInt(1,studentId);
            updateAccountStmt.setInt(2,instrumentId);
            //updateAccountStmt.setString(3," Ongoing");


            //Instrument
            changeStatus2Stmt.setInt(2, instrumentId);
            changeStatus2Stmt.setString(1, "rented");
            int updatedRows7 = changeStatus2Stmt.executeUpdate();
            if (updatedRows7 != 1) {
                handleException(failureMsg, null);
            }

            else {
                //student
                changeStatus3Stmt.setInt(1,checker2 + 1 );
                changeStatus3Stmt.setInt(2, studentId);
                int updatedRows3 = changeStatus3Stmt.executeUpdate();
                if (updatedRows3 != 1) {
                    handleException(failureMsg, null);
                }

                //rented instrument
                changeStatusrentedStmt.setInt(2, instrumentId);
                changeStatusrentedStmt.setString(1, "Ongoing");
                int updatedRows9 = changeStatusrentedStmt.executeUpdate();
                if (updatedRows9 != 1) {
                    handleException(failureMsg, null);
                }
            }


            connection.commit();
        }catch (SQLException sqle){
            handleException(failureMsg, sqle);
        }
    }




/*
//                + STATUS_COLUMN_NAME
//                + "," + INSTRUMENT_ID_COLUMN_NAME
    public List<Instrument> findInstrumentStatus() throws BankDBException {
        String failureMsg = "Could not list instruments.";
        List<Instrument> instrumentStatus = new ArrayList<>();
        try (ResultSet result = findInstrumentStatusStmt.executeQuery()) {
            while (result.next()) {
                instrumentStatus.add(new Instrument(
                        result.getInt(STATUS_COLUMN_NAME),
                        result.getInt(INSTRUMENT_ID_COLUMN_NAME)));
            }
            connection.commit();
        } catch (SQLException sqle) {
            handleException(failureMsg, sqle);
        }
        return instrumentStatus;
    }*/
/*
    public List<Instrument> findStudent() throws BankDBException {
        String failureMsg = "Could not list instruments.";
        List<Instrument> students = new ArrayList<>();
        try (ResultSet result = findHolderPKStmt.executeQuery()) {
            while (result.next()) {
                students.add(new Instrument(
                        result.getInt(STUDENT_ID_COLUMN_NAME),
                        result.getInt(TOTAL_RENTED_INSTRUMENTS_CURRENTLY_COLUMN_NAME)));
            }
            connection.commit();
        } catch (SQLException sqle) {
            handleException(failureMsg, sqle);
        }
        return students;
    }*/



    public Instrument findStudentsid(int studentId)
            throws BankDBException {
        PreparedStatement stmtToExecute;
        stmtToExecute = findStudentStmt;

        String failureMsg = "Could not search for specified rental.";
        ResultSet result = null;
        try {
            stmtToExecute.setInt(1, studentId);
            result = stmtToExecute.executeQuery();
            if (result.next()) {
                return new Instrument(
                        result.getInt(STUDENT_ID_COLUMN_NAME),
                        result.getInt(MEMBER_ID_COLUMN_NAME),
                        result.getInt(PERSON_ID_COLUMN_NAME),
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

    // /**
    // * Searches for the account with the specified account number.
    // *
    // * @param acctNo The account number.
    // * @param lockExclusive If true, it will not be possible to perform UPDATE
    // * or DELETE statements on the selected row in the
    // * current transaction. Also, the transaction will not
    // * be committed when this method returns. If false, no
    // * exclusive locks will be created, and the transaction will
    // * be committed when this method returns.
    // * @return The account with the specified account number, or <code>null</code>
    // if
    // * there is no such account.
    // * @throws BankDBException If failed to search for the account.
    // */
    // terminate
    /*
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
                        /*
                        TYPE_COLUMN_NAME = "type";
                         BRAND_COLUMN_NAME = "brand";
                             RENTING_FEE_COLUMN_NAME = "renting_fee";
                                 STATUS_COLUMN_NAME = "status";
                         */
                        /*
                        result.getInt(INSTRUMENT_ID_COLUMN_NAME),
                        result.getInt(TYPE_COLUMN_NAME),
                        result.getInt(RENTING_FEE_COLUMN_NAME),
                        result.getInt(STATUS_COLUMN_NAME)*/


              /*          //result.getInt(INSTRUMENT_ID_COLUMN_NAME)
            }
            connection.commit();
        } catch (SQLException sqle) {
            handleException(failureMsg, sqle);
        } finally {
            closeResultSet(failureMsg, result);
        }
        return null;
    }
    */


    public void findAccountByAcctNo1(int studentId, int instrumentId) throws BankDBException {
        String failureMsg = "Could not search for specified rental.";

        int updatedRows1 = 0;
        int upp123 = 0;
        int result11 = 0;

        try {
/*
            updateRentEnd.setInt(1, instrumentId);
            updateRentEnd.setInt(2, studentId);
            updatedRows1 = updateRentEnd.executeUpdate();

            updateRentEnd = connection.prepareStatement("UPDATE renting_instruments\r\n"
                    + "SET end_instrument_period = CURRENT_DATE \r\n"
                    + "WHERE instrument_id = ? AND student_id= ? ");


            updateQuota1.setInt(1, studentId);
            updateQuota1.setInt(2, studentId);
            result11 = updateQuota1.executeUpdate();

            updateQuota1 = connection.prepareStatement("UPDATE student \r\n"
                    + "SET quota = (SELECT quota \r\n"
                    + "			FROM student \r\n"
                    + "			WHERE student_id = ? \r\n"
                    + "			) - 1 \r\n"
                    + "WHERE student_id = ?		\r\n"
                    + "			");

            updateNumber1.setInt(1, instrumentId);
            updateNumber1.setInt(2, instrumentId);
            upp123 = updateNumber1.executeUpdate();

            updateNumber1 = connection.prepareStatement("UPDATE instrument_in_stock \r\n"
                    + "SET number_of_pcs_in_stock = (SELECT number_of_pcs_in_stock\r\n"
                    + "							 FROM instrument_in_stock\r\n"
                    + "							  WHERE instrument_id= ? \r\n"
                    + "							 ) + 1 \r\n"
                    + " WHERE instrument_id= ? ");

*/

            connection.commit();
        }catch (SQLException sqle){
            handleException(failureMsg, sqle);
        }
    }


    public Instrument findAccountByAcctNo(int instrumentId)
            throws BankDBException {
        PreparedStatement stmtToExecute;
        stmtToExecute = findAccountByAcctNoStmt;

        String failureMsg = "Could not search for specified rental.";
        ResultSet result = null;
        //ResultSet result1 = null;


        try {


           /* findAccountByAcctNo1Stmt.setInt(1, instrumentId);
            result1 = findAccountByAcctNo1Stmt.executeQuery();
            String checkerRentingStatus = null;
            while(result1.next())
                checkerRentingStatus = result1.getString(4); */

         //   if(checkerRentingStatus == "Ongoing"){

                stmtToExecute.setInt(1, instrumentId);
                result = stmtToExecute.executeQuery();
                if (result.next()) {
                    return new Instrument(
                            result.getInt(INSTRUMENT_ID_COLUMN_NAME),
                            result.getInt(STUDENT_ID_COLUMN_NAME),
                            result.getString(STATUS_COLUMN_NAME),
                            result.getString(RENTING_STATUS_COLUMN_NAME),
                            result.getInt(TOTAL_RENTED_INSTRUMENTS_CURRENTLY_COLUMN_NAME));
          //  }
          //  else {
           //     System.out.println("All instrument are Terminaitet");
            //    }


                        /*
                        TYPE_COLUMN_NAME = "type";
                         BRAND_COLUMN_NAME = "brand";
                             RENTING_FEE_COLUMN_NAME = "renting_fee";
                                 STATUS_COLUMN_NAME = "status";
                         */
                        /*
                        result.getInt(INSTRUMENT_ID_COLUMN_NAME),
                        result.getInt(TYPE_COLUMN_NAME),
                        result.getInt(RENTING_FEE_COLUMN_NAME),
                        result.getInt(STATUS_COLUMN_NAME)*/


                //result.getInt(INSTRUMENT_ID_COLUMN_NAME)
            }
            connection.commit();
        } catch (SQLException sqle) {
            handleException(failureMsg, sqle);
        } finally {
            closeResultSet(failureMsg, result);
        }
        return null;
    }

    // /**
    // * Searches for all accounts whose holder has the specified name.
    // *
    // * @param holderName The account holder's name
    // * @return A list with all accounts whose holder has the specified name,
    // * the list is empty if there are no such account.
    // * @throws BankDBException If failed to search for accounts.
    // */
    // public List<Instrument> findAccountsByHolder(String holderName) throws
    // BankDBException {
    // String failureMsg = "Could not search for specified accounts.";
    // ResultSet result = null;
    // List<Instrument> accounts = new ArrayList<>();
    // try {
    // findAccountByNameStmt.setString(1, holderName);
    // result = findAccountByNameStmt.executeQuery();
    // while (result.next()) {
    // accounts.add(new Instrument(result.getString(ACCT_NO_COLUMN_NAME),
    // result.getString(HOLDER_COLUMN_NAME),
    // result.getInt(BALANCE_COLUMN_NAME)));
    // }
    // connection.commit();
    // } catch (SQLException sqle) {
    // handleException(failureMsg, sqle);
    // } finally {
    // closeResultSet(failureMsg, result);
    // }
    // return accounts;
    // }

    /**
     * Retrieves all existing accounts.
     *
     * @return A list with all existing accounts. The list is empty if there are no
     *         accounts.
     * @throws BankDBException If failed to search for accounts.
     *                         ALLA Instrument
     */

    public List<Instrument> findAllInstruments() throws BankDBException {
        String failureMsg = "Could not list instruments.";
        List<Instrument> instruments = new ArrayList<>();
        try (ResultSet result = findAllInstrumentsStmt.executeQuery()) {
            while (result.next()) {
                instruments.add(new Instrument(
                        result.getInt(INSTRUMENT_ID_COLUMN_NAME),
                        result.getString(TYPE_COLUMN_NAME),
                        result.getString(BRAND_COLUMN_NAME),
                        result.getString(RENTING_FEE_COLUMN_NAME),
                        result.getString(STATUS_COLUMN_NAME)));
            }
            connection.commit();
        } catch (SQLException sqle) {
            handleException(failureMsg, sqle);
        }
        return instruments;
    }

    // ensemble_statistics
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
     * <code>InstrumentDTO</code> object. The balance is set to the value in the
     * specified
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
        String failureMsg = "Could not update the rental: " + rental;
        try {
            changeStatusStmt.setInt(2, rental.getInstrumentId());
            changeStatusStmt.setString(1, rental.getRentingStatus());
            int updatedRows = changeStatusStmt.executeUpdate();
            if (updatedRows != 1) {
                handleException(failureMsg, null);
            }
            /*
            changeStatus2Stmt.setInt(2, rental.getInstrumentId());
            changeStatus2Stmt.setString(1, rental.getStatus());
            int updatedRows2 = changeStatus2Stmt.executeUpdate();
            if (updatedRows2 != 1) {
                handleException(failureMsg, null);
            }*/
          //  changeStatus3Stmt.setInt(2, rental.getStudentId());
           // changeStatus3Stmt.setInt(1, rental.getTotalRentedInstrumentsCurrently() );

            //int updatedRows3 = changeStatus3Stmt.executeUpdate();
           // if (updatedRows3 != 1) {
            //    handleException(failureMsg, null);
           // }
            connection.commit();
        } catch (SQLException sqle) {
            handleException(failureMsg, sqle);
        }
    }
    //
    // /**
    // * Deletes the account with the specified account number.
    // *
    // * @param acctNo The account to delete.
    // * @throws BankDBException If unable to delete the specified account.
    // */
    // public void deleteAccount(String acctNo) throws BankDBException {
    // String failureMsg = "Could not delete account: " + acctNo;
    // try {
    // changeStatus2Stmt.setString(1, acctNo);
    // int updatedRows = changeStatus2Stmt.executeUpdate();
    // if (updatedRows != 1) {
    // handleException(failureMsg, null);
    // }
    // connection.commit();
    // } catch (SQLException sqle) {
    // handleException(failureMsg, sqle);
    // }
    // }

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
                "postgres", "mortza2050");

        connection.setAutoCommit(false);
    }

    private void prepareStatements() throws SQLException {

        /*
         * createAccountStmt = connection.prepareStatement("INSERT INTO " +
         * RENTED_INSTRUMENTS_TABLE_NAME
         * + "(" + STUDENT_ID_COLUMN_NAME
         * + ", " + INSTRUMENT_ID_COLUMN_NAME + ", "
         * + RENTING_STATUS_COLUMN_NAME + ", "
         * + RENT_DATE_COLUMN_NAME + ", " +
         * RETURN_DATE_COLUMN_NAME
         * + ")
         * VALUES (?, ?, ?, ?, ?) ");
         * 
         * ("INSERT INTO renting_instruments VALUES \r\n"
         * + "(" + " ? " + "," + " ? " +
         * ", CURRENT_DATE , CURRENT_DATE + INTERVAL '1 year'  )");
         * 
         */
/*
        createAccountStmt = connection.prepareStatement("INSERT INTO "
                + RENTED_INSTRUMENTS_TABLE_NAME
                 + "(" + STUDENT_ID_COLUMN_NAME + " = ?"
                 + ", " + INSTRUMENT_ID_COLUMN_NAME + " = ?"
                + ", " + RENTING_STATUS_COLUMN_NAME + " = 'Ongoing'"
                + ", " + " ? "
                + ", " + " ? "
                + ")" +  " VALUES (?, ?, ?, ?, ?) "); */

       /* createAccountStmt = connection.prepareStatement("INSERT INTO rented_instruments VALUES \r\n"
                + "(" + " ? "
                + "," + " ? "
                +  "," + " ? "
                +  "," + " ? "
                +  "," + " ? "
                + ")" ); */

        createAccountStmt = connection.prepareStatement("INSERT INTO rented_instruments VALUES \r\n"
                + "(" + " ? "
                + "," + " ? "
                + "," + " ? "
                + ", CURRENT_DATE , CURRENT_DATE + INTERVAL '1 year'  )");


        //Rented Instrument
    /*    updateAccountStmt = connection.prepareStatement("UPDATE " + RENTED_INSTRUMENTS_TABLE_NAME
                + " SET " + RENTING_STATUS_COLUMN_NAME +  " = ?"
                +  " WHERE " + STUDENT_ID_COLUMN_NAME + " = ?"
                + " AND " + INSTRUMENT_ID_COLUMN_NAME + "= ?"); */

        updateAccountStmt = connection.prepareStatement("UPDATE rented_instruments\r\n"
                + "SET return_date = CURRENT_DATE \r\n"
                + "WHERE instrument_id = ? AND student_id= ? "); //instrument_id = 'terminated'

        changeStatusrentedStmt = connection.prepareStatement("UPDATE " + RENTED_INSTRUMENTS_TABLE_NAME
                + " SET " + RENTING_STATUS_COLUMN_NAME + " = ? WHERE " + INSTRUMENT_ID_COLUMN_NAME + " = ? ");

     /*   "UPDATE " + STUDENT_TABLE_NAME
                + " SET " + TOTAL_RENTED_INSTRUMENTS_CURRENTLY_COLUMN_NAME + " = ? WHERE " + STUDENT_ID_COLUMN_NAME
                + " = ? " /*
/*
        changeStatusStmt = connection.prepareStatement("UPDATE " + RENTED_INSTRUMENTS_TABLE_NAME
                + " SET " + RENTING_STATUS_COLUMN_NAME + " = ? WHERE " + INSTRUMENT_ID_COLUMN_NAME + " = ? ");*/

        //Rented Instrument
        findAccountStmt = connection.prepareStatement("SELECT * FROM " + RENTED_INSTRUMENTS_TABLE_NAME +
                " WHERE " + STUDENT_ID_COLUMN_NAME + " = ?"
                + " AND " + INSTRUMENT_ID_COLUMN_NAME + "= ?");


/*
    private static final String RENTED_INSTRUMENTS_TABLE_NAME = "rented_instruments";
    private static final String STUDENT_ID_FK_COLUMN_NAME = STUDENT_ID_COLUMN_NAME;
    private static final String PERSON_ID_FK_COLUMN_NAME = PERSON_ID_COLUMN_NAME;
    private static final String RENTING_STATUS_COLUMN_NAME = "renting_status";
    private static final String RENT_DATE_COLUMN_NAME = "rent_date";
    private static final String RETURN_DATE_COLUMN_NAME = "return_date";
 */


        /*
        createAccountInfoStmt = connection.prepareStatement("INSERT INTO rented_instruments VALUES \r\n"
                + "(" + " ? "
                + "," + " ? "
                +  "," + " ? "
                +  "," + " ? "
                +  "," + " ? "
                +  "," + " ? "
                +  "," + " ? "
                +  "," + " ? "
                + ")" ); /*
         */
       /* showQuota = connection.prepareStatement("SELECT total_rented_instruments_currently \r\n"
                + "FROM student\r\n"
                + "WHERE student_id=? \r\n"
                + "");*/

       /* updateQuota = connection.prepareStatement("UPDATE student\r\n"
                + "SET total_rented_instruments_currently=?  \r\n"
                + "WHERE student_id=? \r\n"
                + "");*/

      /*  makeRentInstrument = connection.prepareStatement("INSERT INTO rented_instruments VALUES \r\n"
                + "(" + " ? " + "," + " ? " + ", CURRENT_DATE , CURRENT_DATE + INTERVAL '1 year'  )"); */

   /*     updateNumber = connection.prepareStatement("UPDATE instrument_in_stock \r\n"
                + "SET number_of_pcs_in_stock = (SELECT number_of_pcs_in_stock\r\n"
                + "							 FROM instrument_in_stock\r\n"
                + "							  WHERE instrument_id= ? \r\n"
                + "							 ) - 1 \r\n"
                + " WHERE instrument_id= ?");*/

        findHolderPKStmt = connection.prepareStatement(
                "SELECT student." + TOTAL_RENTED_INSTRUMENTS_CURRENTLY_COLUMN_NAME + " FROM " + STUDENT_TABLE_NAME +
                        " WHERE " + STUDENT_ID_COLUMN_NAME + " = ?");

        //Instrument
        findHolderPK2Stmt = connection.prepareStatement("SELECT instrument."
                + TOTAL_RENTED_INSTRUMENTS_CURRENTLY_COLUMN_NAME + " FROM " + INSTRUMENT_TABLE_NAME +
                " WHERE " + INSTRUMENT_ID_COLUMN_NAME + " = ?");


        //Instrument
        /*
        String INSTRUMENT_TABLE_NAME = "instrument"; // instrument
        String INSTRUMENT_ID_COLUMN_NAME = "instrument_id";
        String TYPE_COLUMN_NAME = "type";
        String BRAND_COLUMN_NAME = "brand";
        String RENTING_FEE_COLUMN_NAME = "renting_fee";
        String STATUS_COLUMN_NAME = "status"; */
        findInstrumentStatusStmt = connection.prepareStatement("SELECT * FROM " + INSTRUMENT_TABLE_NAME +
                " WHERE " + INSTRUMENT_ID_COLUMN_NAME + " = ?");
        //Student
         //String STUDENT_TABLE_NAME = "student";
         //String STUDENT_ID_COLUMN_NAME = "student_id";
         //String MEMBER_ID_COLUMN_NAME = "member_id";
         //String PERSON_ID_COLUMN_NAME = "person_id";
         //String TOTAL_RENTED_INSTRUMENTS_CURRENTLY_COLUMN_NAME = "total_rented_instruments_currently";
        findStudentStmt = connection.prepareStatement("SELECT * FROM " + STUDENT_TABLE_NAME +
                " WHERE " + STUDENT_ID_COLUMN_NAME + " = ?");



        findStudent1Stmt = connection.prepareStatement("UPDATE  " + STUDENT_TABLE_NAME
                + " SET " + STUDENT_ID_COLUMN_NAME +
                " WHERE " +  TOTAL_RENTED_INSTRUMENTS_CURRENTLY_COLUMN_NAME + " = ?");


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

        findAllInstrumentsStmt = connection.prepareStatement(
                "SELECT * FROM " + INSTRUMENT_TABLE_NAME + " WHERE " + STATUS_COLUMN_NAME + " = 'available'");

        findAllEnsemblesStmt = connection.prepareStatement("SELECT * FROM " + ENSEMBLE_STATISTICS_TABLE_NAME);

        // terminate (måste instrument)
        findAccountByAcctNoStmt = connection.prepareStatement("SELECT * FROM " + TERMINATED_TASK_TABLE_NAME +
              " WHERE " + INSTRUMENT_ID_COLUMN_NAME + " = ?");



//Terminate with rentingstatus "Ongoing"
        findAccountByAcctNo1Stmt = connection.prepareStatement("UPDATE " + TERMINATED_TASK_TABLE_NAME
                + " WHERE " + INSTRUMENT_ID_COLUMN_NAME + " = ?"
                + " AND " + RENTING_STATUS_COLUMN_NAME + " = 'Ongoing'"
                );
        //+ " AND " + RENTING_STATUS_COLUMN_NAME + " = Ongoing"

                 /*("SELECT * FROM " + TERMINATED_TASK_TABLE_NAME +
                " WHERE "
                 + INSTRUMENT_ID_COLUMN_NAME + " = ?" + " AND " + RENTING_STATUS_COLUMN_NAME + " = Ongoing" );*/
        /*
        ("UPDATE renting_instruments\r\n"
                + "SET end_instrument_period = CURRENT_DATE \r\n"
                + "WHERE instrument_id = ? AND student_id= ? "); */


 //Instrument terminate
      //  findAccountByAcctNoStmt = connection.prepareStatement("SELECT * FROM " + INSTRUMENT_TABLE_NAME +
           //     " WHERE " + INSTRUMENT_ID_COLUMN_NAME + " = ?");

        changeStatusStmt = connection.prepareStatement("UPDATE " + RENTED_INSTRUMENTS_TABLE_NAME
                + " SET " + RENTING_STATUS_COLUMN_NAME + " = ? WHERE " + INSTRUMENT_ID_COLUMN_NAME + " = ? ");

        changeStatus2Stmt = connection.prepareStatement("UPDATE " + INSTRUMENT_TABLE_NAME
                + " SET " + STATUS_COLUMN_NAME + " = ? WHERE " + INSTRUMENT_ID_COLUMN_NAME + " = ? ");

        changeStatus3Stmt = connection.prepareStatement("UPDATE " + STUDENT_TABLE_NAME
                + " SET " + TOTAL_RENTED_INSTRUMENTS_CURRENTLY_COLUMN_NAME + " = ? WHERE " + STUDENT_ID_COLUMN_NAME
                + " = ? ");

        changeStmt = connection.prepareStatement("UPDATE " + STUDENT_TABLE_NAME
                + " SET " + TOTAL_RENTED_INSTRUMENTS_CURRENTLY_COLUMN_NAME + " = ? WHERE " + STUDENT_ID_COLUMN_NAME
                + " = ? ");

        //INSTRUMENT_TABLE_NAME = "instrument"; //Table
        //INSTRUMENT_ID_COLUMN_NAME = "instrument_id"; //Columns
        //STATUS_COLUMN_NAME = "status"; //Columns

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

    // private int createAccountNo() {
    // return (int)Math.floor(Math.random() * Integer.MAX_VALUE);
    // }
    //
    private void findHolderPKByName(int studentId, int instrumentId) throws SQLException {
        findHolderPKStmt.setInt(1, studentId);
        findHolderPK2Stmt.setInt(1, instrumentId);
        findHolderPKStmt.executeQuery();
        findHolderPK2Stmt.executeQuery();
        findStudent1Stmt.executeUpdate();

    }

}
