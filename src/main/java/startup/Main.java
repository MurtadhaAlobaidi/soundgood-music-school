package startup;

import controller.Controller;
import integration.BankDBException;
import view.BlockingInterpreter;

/**
* Starts the bank client.
*/
public class Main {
   /**
    * @param args There are no command line arguments.
    */
   public static void main(String[] args) {
       try {
       new BlockingInterpreter(new Controller()).handleCmds();
       } catch(BankDBException bdbe) {
           System.out.println("Could not connect to Bank db.");
           bdbe.printStackTrace();
       }
   }
}
