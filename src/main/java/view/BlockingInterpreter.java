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
package view;

import java.util.List;
import java.util.Scanner;

import controller.Controller;
import model.InstrumentDTO;

/**
 * Reads and interprets user commands. This command interpreter is blocking, the
 * user
 * interface does not react to user input while a command is being executed.
 */
public class BlockingInterpreter {
    private static final String PROMPT = "> ";
    private final Scanner console = new Scanner(System.in);
    private Controller ctrl;
    private boolean keepReceivingCmds = false;

    /**
     * Creates a new instance that will use the specified controller for all
     * operations.
     * 
     * @param ctrl The controller used by this instance.
     */
    public BlockingInterpreter(Controller ctrl) {
        this.ctrl = ctrl;
    }

    /**
     * Stops the commend interpreter.
     */
    public void stop() {
        keepReceivingCmds = false;
    }

    /**
     * Interprets and performs user commands. This method will not return until the
     * UI has been stopped. The UI is stopped either when the user gives the
     * "quit" command, or when the method <code>stop()</code> is called.
     */
    public void handleCmds() {
        keepReceivingCmds = true;
        while (keepReceivingCmds) {
            try {
                CmdLine cmdLine = new CmdLine(readNextLine());
                switch (cmdLine.getCmd()) {
                    case HELP:
                        for (Command command : Command.values()) {
                            if (command == Command.ILLEGAL_COMMAND) {
                                continue;
                            }
                            System.out.println(command.toString().toLowerCase());
                        }
                        break;
                    case QUIT:
                        keepReceivingCmds = false;
                        break;

                    // Create new rent
                    // case NEW:
                    // if (cmdLine.getParameter(0) == "")
                    // System.out.println("EXAMPLE: new student-id instrument-id");
                    // ctrl.createAccount(Integer.parseInt(cmdLine.getParameter(0)),
                    // Integer.parseInt(cmdLine.getParameter(1)));
                    // break;

                    case LIST:
                        List<? extends InstrumentDTO> instruments = null;
                        instruments = ctrl.getAllInstruments();
                        if ((instruments.size()) != 0) {
                            for (InstrumentDTO instrument : instruments) {
                                System.out.println("instrument-id: " + instrument.getInstrumentId() + ", "
                                        + "type: " + instrument.getType() + ", "
                                        + "renting fee: " + instrument.getRentingFee() + ", "
                                        + "brand: " + instrument.getBrand() + ", "
                                        + "status: " + instrument.getStatus());
                            }

                        } else {
                            System.out.println("There are no instruments available All instruments are rented");

                        }
                        break;

                    case ENSEMBLES:
                        List<? extends InstrumentDTO> ensemble_statistics = null;
                        ensemble_statistics = ctrl.getAllEnsembles();
                        for (InstrumentDTO ensemble : ensemble_statistics) {
                            System.out.println("Genre: " + ensemble.getGenre() + ", "
                                    + "Max number of students: " + ensemble.getMaxNumOfStudents() + ", "
                                    + "Min number of students: " + ensemble.getMinNumOfStudents() + ", "
                                    + "Year: " + ensemble.getYear() + ", "
                                    + "Week: " + ensemble.getWeekNumber() + ", "
                                    + "Day: " + ensemble.getDay() + ", "
                                    + "Status: " + ensemble.getEnsembleStatus() + ", ");
                        }
                        break;

                    case RENT:
                        List<? extends InstrumentDTO> rentals = null;
                        if (cmdLine.getParameter(0).equals("")) {
                            rentals = ctrl.getAllRentals();

                            for (InstrumentDTO rental : rentals) {
                                System.out.println("Instrument-ID: " + rental.getInstrumentId() + ", "
                                        + "Renting fee: " + rental.getRentingFee() + ", "
                                        + "Type: " + rental.getType() + ", "
                                        + "Brand: " + rental.getBrand() + ", "
                                        + "Student name: " + rental.getStudentName() + ", "
                                        + "Rent date: " + rental.getRentDate() + ", "
                                        + "Return date: " + rental.getReturnDate() + ", "
                                        + "Status: " + rental.getRentalStatus());
                            }
                        } else {
                            // <student_id instrument_id> Student can renting a specify instrument.
                            // ctrl.deposit(Integer.parseInt(cmdLine.getParameter(0)));
                            ctrl.createAccount(Integer.parseInt(cmdLine.getParameter(0)),
                                    Integer.parseInt(cmdLine.getParameter(1)));
                        }
                        break;

                    case TERMINATE:
                        if (cmdLine.getParameter(0) == "")
                            System.out.println(
                                    "OBS!!! You wrote in the wrong format. You must type as: EXAMPLE: <terminate instrument-id>");
                        ctrl.deposit(Integer.parseInt(cmdLine.getParameter(0)));
                        break;

                    default:
                        System.out.println("illegal command");
                }
            } catch (Exception e) {
                System.out.println("Operation failed");
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private String readNextLine() {
        System.out.print(PROMPT);
        return console.nextLine();
    }
}
