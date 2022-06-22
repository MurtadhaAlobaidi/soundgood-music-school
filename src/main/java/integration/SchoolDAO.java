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
import java.util.List;

import model.Instrument;

/**
 * This data access object (DAO) encapsulates all database calls in the School
 * application. No code outside this class shall have any knowledge about the
 * database.
 */
public class SchoolDAO {
    // instrument
    private static final String INSTRUMENT_TABLE_NAME = "instrument"; // instrument
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
    private static final String TERMINATED_TASK_TABLE_NAME = "terminated_task";

    private Connection connection;

    private PreparedStatement createAccountStmt;
    private PreparedStatement changeStatusrentedStmt;

    private PreparedStatement createAccountInfoStmt;

    private PreparedStatement changeRentStatusrentedStmt;
    private PreparedStatement findAllRentalsStmt;
    private PreparedStatement findAllInstrumentsStmt;
    private PreparedStatement findAllEnsemblesStmt;
    private PreparedStatement changeStatus2Stmt;
    private PreparedStatement changeStatus3Stmt;

    // Student
    private PreparedStatement findStudentStmt;
    private PreparedStatement findInstrumentStatusStmt;
    private PreparedStatement findInstrumentTotermminatedStmt;
   

    /**
     * Constructs a new SchoolDAO object connected to the School database.
     */
    public SchoolDAO() throws MusicSchoolException {
        try {
            connectToSchoolDB();
            prepareStatements();
        } catch (ClassNotFoundException | SQLException exception) {
            throw new MusicSchoolException("Could not connect to datasource.", exception);
        }
    }

    /**
     * create New rental with @param studentId from student and @param instrumentId
     * to the instrument.
     * 
     * @param studentId
     * @param instrumentId
     * @throws MusicSchoolException
     */
    public void createNewRental(int studentId, int instrumentId) throws MusicSchoolException {
        String failureMsg = "Could not create the renatl of: " + studentId + "and instrument: " + instrumentId;

        try {

            if (findAllInstruments().size() == 0) {
                System.out.println("All instruments rented");
            }

            checkerStudentId(studentId);
            checkerInstrumentId(instrumentId);

            /**
             * rented_instrument tables
             */
            createAccountStmt.setInt(1, studentId);
            createAccountStmt.setInt(2, instrumentId);
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
            int checkerTotal = 0;
            ResultSet result4 = null;
            findStudentStmt.setInt(1, studentId);
            result4 = findStudentStmt.executeQuery();
            while (result4.next())
                checkerTotal = result4.getInt(4);

            // add to student
            changeStatus3Stmt.setInt(1, checkerTotal + 1);
            changeStatus3Stmt.setInt(2, studentId);
            int updatedRows5 = changeStatus3Stmt.executeUpdate();
            if (updatedRows5 != 1) {
                handleException(failureMsg, null);
            }

            connection.commit();

        } catch (SQLException sqle) {
            handleException(failureMsg, sqle);
        }
    }

    /**
     *
     * @param studentId
     * @throws MusicSchoolException
     */
    public void checkerStudentId(int studentId) throws MusicSchoolException {
        String failureMsg = "You have reached the maximum allowed number of rentals";
        ResultSet result = null;
        int updatedRows = 1;
        int checkerTotal = 0;

        try {
            // Student
            findStudentStmt.setInt(1, studentId);
            result = findStudentStmt.executeQuery();
            while (result.next())
                checkerTotal = result.getInt(4);
            if (checkerTotal == 2 && updatedRows == 1) {
                handleException(failureMsg, null);
            }
            connection.commit();
        } catch (SQLException sqle) {
            handleException(failureMsg, sqle);
        }

    }

    /**
     * Checker the instrument if that available or rented.
     * 
     * @param instrumentId
     * @throws MusicSchoolException
     */
    public void checkerInstrumentId(int instrumentId) throws MusicSchoolException {
        String failureMsg = "This instrument rented";
        try {
            // instrument
            String s = findInstrumentByIdNo(instrumentId).getStatus();
            if (s.equalsIgnoreCase("rented")) {
                handleException(failureMsg, null);
            }
            /**
             * ELSE
             * instrument tables (5)-status == "available"
             * 
             * @move to createNewRental
             */
            connection.commit();
        } catch (SQLException sqle) {
            handleException(failureMsg, sqle);
        }

    }

    /**
     * To find the right information to the instrumentId with studentId number.
     * All this information that rented_instrument table have.
     * 
     * @param studentId
     * @param instrumentId
     * @return
     * @throws MusicSchoolException
     */
    public Instrument findTerminated(int studentId, int instrumentId)
            throws MusicSchoolException {
        PreparedStatement stmtToExecute;
        stmtToExecute = findInstrumentTotermminatedStmt;

        String failureMsg = "Could not search for specified rental.";
        ResultSet result = null;
        try {
            stmtToExecute.setInt(1, studentId);
            stmtToExecute.setInt(2, instrumentId);
            result = stmtToExecute.executeQuery();
            if (result.next()) {
                return new Instrument(
                        result.getInt(STUDENT_ID_COLUMN_NAME),
                        result.getString(INSTRUMENT_ID_COLUMN_NAME),
                        result.getString(RENTING_STATUS_COLUMN_NAME),
                        result.getString(RENT_DATE_COLUMN_NAME),
                        result.getString(RETURN_DATE_COLUMN_NAME));
            }
            connection.commit();
        } catch (SQLException sqle) {
            handleException(failureMsg, sqle);
        }
        return null;
    }

    /**
     * To terminated the specified instrument.
     * 
     * @param studentId
     * @param instrumentId
     * @throws MusicSchoolException
     */
    public void terminated(int studentId, int instrumentId) throws MusicSchoolException {
        String failureMsg = "Could not terminated for specified rental.";
        try {
            // update rent instrument
            changeRentStatusrentedStmt.setString(1, "terminated");
            changeRentStatusrentedStmt.setInt(2, studentId);
            changeRentStatusrentedStmt.setInt(3, instrumentId);
            int updatedRows8 = changeRentStatusrentedStmt.executeUpdate();
            if (updatedRows8 != 1) {
                handleException(failureMsg, null);
            }

            // student current
            findStudentStmt.setInt(1, studentId);
            ResultSet result2 = null;
            result2 = findStudentStmt.executeQuery();
            int checker2 = 0;
            while (result2.next())
                checker2 = result2.getInt(4);

            // student current
            changeStatus3Stmt.setInt(1, checker2 - 1);
            changeStatus3Stmt.setInt(2, studentId);
            int updatedRows3 = changeStatus3Stmt.executeUpdate();
            if (updatedRows3 != 1) {
                handleException(failureMsg, null);
            }

            // Instrument
            changeStatus2Stmt.setInt(2, instrumentId);
            changeStatus2Stmt.setString(1, "available");
            int updatedRows7 = changeStatus2Stmt.executeUpdate();
            if (updatedRows7 != 1) {
                handleException(failureMsg, null);
            }

            connection.commit();
        } catch (SQLException sqle) {
            handleException(failureMsg, sqle);
        }

    }

    /**
     *
     * @param instrumentId
     * @return
     * @throws MusicSchoolException
     */
    public Instrument findInstrumentByIdNo(int instrumentId)
            throws MusicSchoolException {
        PreparedStatement stmtToExecute;
        stmtToExecute = findInstrumentStatusStmt;

        String failureMsg = "Could not search for specified rental.";
        ResultSet result = null;
        try {
            stmtToExecute.setInt(1, instrumentId);
            result = stmtToExecute.executeQuery();
            if (result.next()) {
                return new Instrument(
                        result.getInt(INSTRUMENT_ID_COLUMN_NAME),
                        result.getString(TYPE_COLUMN_NAME),
                        result.getString(BRAND_COLUMN_NAME),
                        result.getString(RENTING_FEE_COLUMN_NAME),
                        result.getString(STATUS_COLUMN_NAME));
            }
            connection.commit();
        } catch (SQLException sqle) {
            handleException(failureMsg, sqle);
        } finally {
            closeResultSet(failureMsg, result);
        }
        return null;
    }

    /**
     * Retrieves all existing instruments.
     *
     * @return A list with all existing instruments. The list is empty if there are
     *         no
     *         instruments rented.
     * @throws MusicSchoolException If failed to search for instruments.
     *
     */
    public List<Instrument> findAllInstruments() throws MusicSchoolException {
        String failureMsg = "Could not find the instruments list.";
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
     * Retrieves all existing lesson.
     *
     * @return A list with all existing accounts. The list is empty if there are no
     *         accounts.
     * @throws MusicSchoolException If failed to search for ensembles lesson.
     */
    public List<Instrument> findAllEnsembles() throws MusicSchoolException {
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

    /**
     * To Find all rented instruments
     * 
     * @return
     * @throws MusicSchoolException
     */
    public List<Instrument> findAllRentals() throws MusicSchoolException {
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
     * Commits the current rentals.
     * 
     * @throws MusicSchoolException If unable to commit the current rentals.
     */
    public void commit() throws MusicSchoolException {
        try {
            connection.commit();
        } catch (SQLException e) {
            handleException("Failed to commit", e);
        }
    }

    /**
     * To connect to the Music school database.
     * 
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    private void connectToSchoolDB() throws ClassNotFoundException, SQLException {
        connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/soundgood_music_school",
                "postgres", "mortza2050");

        connection.setAutoCommit(false);
    }

    /**
     * Hear all the Queries that we used in the application.
     * 
     * @throws SQLException
     */
    private void prepareStatements() throws SQLException {

        createAccountStmt = connection.prepareStatement("INSERT INTO rented_instruments VALUES \r\n"
                + "(" + " ? "
                + "," + " ? "
                + "," + " ? "
                + ", CURRENT_DATE , CURRENT_DATE + INTERVAL '1 year'  )");


        changeStatusrentedStmt = connection.prepareStatement("UPDATE " + RENTED_INSTRUMENTS_TABLE_NAME
                + " SET " + RENTING_STATUS_COLUMN_NAME + " = ? WHERE " + INSTRUMENT_ID_COLUMN_NAME + " = ? ");



        // Instrument
        findInstrumentStatusStmt = connection.prepareStatement("SELECT * FROM " + INSTRUMENT_TABLE_NAME +
                " WHERE " + INSTRUMENT_ID_COLUMN_NAME + " = ?");
        // Student
        findStudentStmt = connection.prepareStatement("SELECT * FROM " + STUDENT_TABLE_NAME +
                " WHERE " + STUDENT_ID_COLUMN_NAME + " = ?");

        //rented_instrument_info VIEWs
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


        // rented instrument
        findInstrumentTotermminatedStmt = connection.prepareStatement("SELECT * FROM " + RENTED_INSTRUMENTS_TABLE_NAME +
                " WHERE " + STUDENT_ID_COLUMN_NAME + " = ?"
                + " AND " + INSTRUMENT_ID_COLUMN_NAME + " = ?");



        changeRentStatusrentedStmt = connection.prepareStatement("UPDATE " + RENTED_INSTRUMENTS_TABLE_NAME
                + " SET " + RENTING_STATUS_COLUMN_NAME + " = ? "
                + " WHERE " + STUDENT_ID_COLUMN_NAME + " = ?"
                + " AND " + INSTRUMENT_ID_COLUMN_NAME + " = ?");



        changeStatus2Stmt = connection.prepareStatement("UPDATE " + INSTRUMENT_TABLE_NAME
                + " SET " + STATUS_COLUMN_NAME + " = ? WHERE " + INSTRUMENT_ID_COLUMN_NAME + " = ? ");

        changeStatus3Stmt = connection.prepareStatement("UPDATE " + STUDENT_TABLE_NAME
                + " SET " + TOTAL_RENTED_INSTRUMENTS_CURRENTLY_COLUMN_NAME + " = ? WHERE " + STUDENT_ID_COLUMN_NAME
                + " = ? ");

    }

    /**
     *
     * @param failureMsg
     * @param cause
     * @throws MusicSchoolException
     */
    private void handleException(String failureMsg, Exception cause) throws MusicSchoolException {
        String completeFailureMsg = failureMsg;
        try {
            connection.rollback();
        } catch (SQLException rollbackExc) {
            completeFailureMsg = completeFailureMsg +
                    ". Also failed to rollback rentals because of: " + rollbackExc.getMessage();
        }

        if (cause != null) {
            throw new MusicSchoolException(failureMsg, cause);
        } else {
            throw new MusicSchoolException(failureMsg);
        }
    }

    private void closeResultSet(String failureMsg, ResultSet result) throws MusicSchoolException {
        try {
            result.close();
        } catch (Exception e) {
            throw new MusicSchoolException(failureMsg + " Could not close result set.", e);
        }

    }

}