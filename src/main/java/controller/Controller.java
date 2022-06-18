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

import integration.SchoolDAO;
import integration.MusicSchoolException;
import model.InstrumentDTO;
import model.RentException;
import model.RejectedException;

/**
 * This is the application's only controller, all calls to the model pass here.
 * The controller is also responsible for calling the DAO. Typically, the
 * controller first calls the DAO to retrieve data (if needed), then operates on
 * the data, and finally tells the DAO to store the updated data (if any).
 */
public class Controller {
    private final SchoolDAO schoolDb;

    /**
     * Creates a new instance, and retrieves a connection to the database.
     * 
     * @throws MusicSchoolException If unable to connect to the database.
     */
    public Controller() throws MusicSchoolException {
        schoolDb = new SchoolDAO();
    }

    /**
     * Creates a new rent for the specified studentID and instrumentID.
     * 
     * @param studentId The student name.
     * @throws RentException If unable to create account.
     *
     */
    public void createNewRental(int studentId, int instrumentId) throws RentException {
        String failureMsg = "Could not create new rental for: " + studentId;

        if (studentId == 0) {
            throw new RentException(failureMsg);
        }

        try {

            schoolDb.createNewRental(studentId, instrumentId);

        } catch (Exception e) {
            throw new RentException(failureMsg, e);
        }
    }

    /**
     * Lists all instruments in the whole music school.
     * 
     * @return A list containing all instruments. The list is empty if there are no
     *         instrument rented.
     * @throws RentException If unable to retrieve instruments.
     */
    public List<? extends InstrumentDTO> getAllInstruments() throws RentException {
        try {
            return schoolDb.findAllInstruments();
        } catch (Exception e) {
            throw new RentException("Unable to list instruments.", e);
        }
    }

    /**
     * Lists all ensemble's lesson in the music school.
     * 
     * @return A list containing all ensemble's lesson. The list is empty if there
     *         are no
     *         ensemble's lesson.
     * @throws RentException If unable to retrieve instruments.
     */
    public List<? extends InstrumentDTO> getAllEnsembles() throws RentException {
        try {
            return schoolDb.findAllEnsembles();
        } catch (Exception e) {
            throw new RentException("Unable to list ensembles.", e);
        }
    }

    public List<? extends InstrumentDTO> getAllRentals() throws RentException {
        try {
            return schoolDb.findAllRentals();
        } catch (Exception e) {
            throw new RentException("Unable to list rentals.", e);
        }
    }

    // /**
    // * Terminated the specified instruments to the student with the specified
    // ID-number.
    // *
    // * @param studentId The number of the student.
    // * @param instrumentId The number of the instrument.
    // * @throws RejectedException If not allowed to terminate the specified
    // instrument.
    // * @throws AccountException If failed to terminate.
    // */
    public void terminate(int studentId, int instrumentId) throws RejectedException, RentException {
        String failureMsg = "Could not terminate to instrument: " + instrumentId;

        if (instrumentId == 0) {
            throw new RentException(failureMsg);
        }

        try {
            schoolDb.findTerminated(studentId, instrumentId);
            schoolDb.terminated(studentId, instrumentId);

        } catch (MusicSchoolException bdbe) {
            throw new RentException(failureMsg, bdbe);
        } catch (Exception e) {
            commitOngoingRental(failureMsg);
            throw e;
        }
    }

    private void commitOngoingRental(String failureMsg) throws RentException {
        try {
            schoolDb.commit();
        } catch (MusicSchoolException bdbe) {
            throw new RentException(failureMsg, bdbe);
        }
    }

}
