package view;

import java.util.List;
import java.util.Scanner;

import controller.Controller;
import model.InstrumentDTO;

/**
* Reads and interprets user commands. This command interpreter is blocking, the user
* interface does not react to user input while a command is being executed.
*/
public class BlockingInterpreter {
   private static final String PROMPT = "> ";
   private final Scanner console = new Scanner(System.in);
   private Controller ctrl;
   private boolean keepReceivingCmds = false;

   /**
    * Creates a new instance that will use the specified controller for all operations.
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
                       
                     //Create new rent  
                   case NEW:
                       if (cmdLine.getParameter(0) == "")
                           System.out.println("EXAMPLE: new student-id instrument-id");                    	
                       ctrl.createAccount(Integer.parseInt(cmdLine.getParameter(0)), Integer.parseInt(cmdLine.getParameter(1)));
                       break;
                       
                       
                       
                       
                   case LIST:
                       List<? extends InstrumentDTO> instruments = null;
                       instruments = ctrl.getAllInstruments();
                       for (InstrumentDTO instrument : instruments) {
                           System.out.println("instrument-id: " + instrument.getInstrumentId() + ", "
                                            + "type: " + instrument.getType() + ", "
                                            + "renting fee: " + instrument.getRentingFee() + ", " 
                                            + "brand: " + instrument.getBrand() + ", "
                                            + "status: " + instrument.getStatus());
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
                                            + "Week: " + ensemble.getWeekNumber()+ ", "
                                            + "Day: " + ensemble.getDay() + ", "
                                            + "Status: " + ensemble.getEnsembleStatus() + ", ");
                       }
                       break;
                       
                       
                   case RENT:
                       List<? extends InstrumentDTO> rentals = null;
                       rentals = ctrl.getAllRentals();
                       for (InstrumentDTO rental : rentals) {
                           System.out.println("Instrument-ID: " + rental.getInstrumentId() + ", "
                                            + "Renting fee: " + rental.getRentingFee() + ", "
                                            + "Type: " + rental.getType() + ", " 
                                            + "Brand: " + rental.getBrand() + ", "
                                            + "Student name: " + rental.getStudentName() + ", "
                                            + "Rent date: " + rental.getRentDate() + ", "
                                            + "Return date: " + rental.getReturnDate()+ ", "
                                            + "Status: " + rental.getRentalStatus());
                       }
                       break;
                       
                       
                   case TERMINATE:
                       if (cmdLine.getParameter(0) == "")
                           System.out.println("EXAMPLE: terminate instrument-id");
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
