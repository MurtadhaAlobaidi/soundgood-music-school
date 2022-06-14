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
package model;

/**
 * An account in the bank.
 */
public class Instrument implements InstrumentDTO {
    private int instrumentId;
    private String rentingFee;
    private String type;
    private String brand;
    private String status;

    private int studentId;
    private int personId;
    private int memberId;
    private String studentName;
    private int totalRentedInstrumentsCurrently;
    private String rentingStatus;
    private String rentDate;
    private String returnDate;
    private String rentalStatus;

    private int maxNumOfStudents;
    private int minNumOfStudents;
    private int year;
    private int weekNumber;
    private int day;
    private String genre;
    private String typeOfLesson;
    private String ensembleStatus;



    // /**
    // * Creates an account for the specified holder with the balance zero. The
    // account
    // * number is unspecified.
    // *
    // * @param holderName The account holder's holderName.
    // * @param bankDB The DAO used to store updates to the database.
    // */
    // public Instrument(String holderName) {
    // this(null, holderName, 0);
    // }
    //
    // /**
    // * Creates an account for the specified holder with the specified balance. The
    // * account number is unspecified.
    // *
    // * @param holderName The account holder's holderName.
    // * @param balance The initial balance.
    // */
    // public Instrument(String holderName, int balance) {
    // this(null, holderName, balance);
    // }
    //
    // /**
    // * Creates an account for the specified holder with the specified balance and
    // account
    // * number.
    // *
    // * @param acctNo The account number.
    // * @param holderName The account holder's holderName.
    // * @param balance The initial balance.
    // */
    // public Instrument(String acctNo, String holderName, int balance) {
    // this.acctNo = acctNo;
    // this.holderName = holderName;
    // this.balance = balance;
    // }
    /********************************OK För Instrument (findAllInstruments())******************************/
    //Instrument Tables
    public Instrument(int instrumentId,String type,String brand, String rentingFee, String status) {
        this.instrumentId = instrumentId;
        this.type = type;
        this.brand = brand;
        this.rentingFee = rentingFee;
        this.status = status;
    }


    //Student
    public Instrument(int studentId,int personId,int memberId, int totalRentedInstrumentsCurrently) {
        this.studentId = studentId;
        this.personId = personId;
        this.memberId = memberId;
        this.totalRentedInstrumentsCurrently = totalRentedInstrumentsCurrently;
    }
    /**************************************************************************************************/
    /************************** ensemble_statistics (findAllEnsembles()) *****************************/
    //ensemble_statistics Views
    public Instrument(String genre, int maxNumOfStudents, int minNumOfStudents, int year, int weekNumber, int day,
                      String ensembleStatus) {
        this.genre = genre;
        this.maxNumOfStudents = maxNumOfStudents;
        this.minNumOfStudents = minNumOfStudents;
        this.year = year;
        this.weekNumber = weekNumber;
        this.day = day;
        this.ensembleStatus = ensembleStatus;
    }
    /**************************************************************************************************/
    /********************** rented_instruments_info (findAllRentals()) ********************************/
    // rented_instruments_info Views
    public Instrument(int instrumentId, String rentingFee, String type, String brand, String studentName,
                      String rentDate, String returnDate, String rentalStatus) {
        this.brand = brand;
        this.instrumentId = instrumentId;
        this.rentingFee = rentingFee;
        this.type = type;
        this.studentName = studentName;
        this.rentDate = rentDate;
        this.returnDate = returnDate;
        this.rentalStatus = rentalStatus;
    }
    /**************************************************************************************************/
    /********************** terminated_task (findAccountByAcctNo()) ********************************/
    //terminated_task Views
    public Instrument(int instrumentId, int studentId, String status, String rentingStatus,
                      int totalRentedInstrumentsCurrently) {
        this.status = status;
        this.studentId = studentId;
        this.instrumentId = instrumentId;
        this.rentingStatus = rentingStatus;
        this.totalRentedInstrumentsCurrently = totalRentedInstrumentsCurrently;
    }
    /**************************************************************************************************/

    /**
     * Creates an account for the specified holder with the specified balance and
     * account
     * number.
     *
     * @param type         The account number.
     * @param rentingFee   The account holder's holderName.
     * @param instrumentId The initial balance.
     */
    /*
    public Instrument(int studentId, int instrumentId, String status, int totalRentedInstrumentsCurrently) {
        this.studentId = studentId;
        this.instrumentId = instrumentId;
        this.status = status;
        this.totalRentedInstrumentsCurrently = totalRentedInstrumentsCurrently;

    }*/


    public Instrument(int studentId, int instrumentId) {
        this.studentId = studentId;
        this.instrumentId = instrumentId;
        //this.status = status;
    }







    /**
     * @return The account number.
     */
    public String getType() {
        return type;
    }

    /**
     * @return The account number.
     */
    public String getBrand() {
        return brand;
    }

    /**
     * @return The account number.
     */
    public String getStatus() {
        return status;
    }

    /**
     * @return The balance.
     */
    public int getInstrumentId() {
        return instrumentId;
    }

    /**
     * @return The holder's name.
     */
    public String getRentingFee() {
        return rentingFee;
    }

    /**
     * @return The holder's name.
     */
    public int getStudentId() {
        return studentId;
    }

    /**
     * @return The holder's name.
     */
    public String getRentingStatus() {
        return rentingStatus;
    }

    /**
     * @return The holder's name.
     */
    public int getpersonId() {
        return personId;
    }

    /**
     * @return The holder's name.
     */
    public int getMemberId() {
        return memberId;
    }

    /**
     * @return The holder's name.
     */
    public int getTotalRentedInstrumentsCurrently() {
        return totalRentedInstrumentsCurrently;
    }

    /**
     * @return The holder's name.
     */
    public String getRentDate() {
        return rentDate;
    }

    /**
     * @return The holder's name.
     */
    public String getReturnDate() {
        return returnDate;
    }

    /**
     * @return The balance.
     */
    public int getMaxNumOfStudents() {
        return maxNumOfStudents;
    }

    /**
     * @return The balance.
     */
    public int getMinNumOfStudents() {
        return minNumOfStudents;
    }

    /**
     * @return The balance.
     */
    public int getYear() {
        return year;
    }

    /**
     * @return The balance.
     */
    public int getWeekNumber() {
        return weekNumber;
    }

    /**
     * @return The balance.
     */
    public int getDay() {
        return day;
    }

    /**
     * @return The holder's name.
     */
    public String getGenre() {
        return genre;
    }

    /**
     * @return The holder's name.
     */
    public String getTypeOfLesson() {
        return typeOfLesson;
    }

    /**
     * @return The holder's name.
     */
    public String getEnsembleStatus() {
        return ensembleStatus;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getRentalStatus() {
        return rentalStatus;
    }

    // *************************************************************************************************************************/

    // /**
    // * Deposits the specified amount.
    // *
    // * @param amount The amount to deposit.
    // * @throws AccountException If the specified amount is negative, or if unable
    // to
    // * perform the update.
    // */
    public void deposit() throws RejectedException {
        if (getRentingStatus().equalsIgnoreCase("Terminated")) {
            throw new RejectedException("Tried to terminate an already terminated rental, illegal request: ");
        }

        rentingStatus = "terminated";
        status = "available";
        totalRentedInstrumentsCurrently--;
        if (totalRentedInstrumentsCurrently >= 0) {
            return;
        }
        totalRentedInstrumentsCurrently = 0;
    }

    public void update() throws RejectedException {
        if (totalRentedInstrumentsCurrently == 2) {
            throw new RejectedException(
                    "You have reached the maximum allowed number of renatls, you can return one of your erliear rentals to complete. ");
        }  else if (getStatus() == "rented") {
            throw new RejectedException("You can not rent this instrument, it's not available at the moment. ");
        }
        status = "rented";
        rentingStatus = "Ongoing";
        totalRentedInstrumentsCurrently++;
    }

    /**
     * @return A string representation of all fields in this object.
     */
    @Override
    public String toString() {
        StringBuilder stringRepresentation = new StringBuilder();
        stringRepresentation.append("Instrument: [");
        stringRepresentation.append("status: ");
        stringRepresentation.append(status);
        stringRepresentation.append(",  rentingStatus: ");
        stringRepresentation.append(rentingStatus);
        stringRepresentation.append(",  studentId: ");
        stringRepresentation.append(studentId);
        stringRepresentation.append(",  totalRentedInstrumentsCurrently: ");
        stringRepresentation.append(totalRentedInstrumentsCurrently);
        stringRepresentation.append(",  type: ");
        stringRepresentation.append(type);
        stringRepresentation.append(", renting fee: ");
        stringRepresentation.append(rentingFee);
        stringRepresentation.append(",  brand: ");
        stringRepresentation.append(brand);
        stringRepresentation.append(", instrument-id: ");
        stringRepresentation.append(instrumentId);

        stringRepresentation.append("]");
        return stringRepresentation.toString();
    }
}

