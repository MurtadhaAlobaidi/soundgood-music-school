package model;

/**
* Specifies a read-only view of an account.
*/
public interface InstrumentDTO {
   /**
    * @return The type.
    */
   public String getType();

   /**
    * @return The instrument-id.
    */
   public int getInstrumentId();

   /**
    * @return The renting fee.
    */
   public String getRentingFee();
   
   /**
    * @return The brand.
    */
   public String getBrand();
   
   /**
    * @return The status.
    */
   public String getStatus();
   
   
   
   public int getMaxNumOfStudents();

   public int getMinNumOfStudents();

   public int getYear();
   
   public int getStudentId();

   public String getStudentName();

   public int getWeekNumber();
   
   public int getDay();

   public String getGenre();

   public String getTypeOfLesson();

   public String getEnsembleStatus();
   
   public String getRentalStatus();
   
   public String getReturnDate();

   
   public String getRentDate();
   public String getRentingStatus();
   public int getTotalRentedInstrumentsCurrently();
   
   
   
   

   
   
   
   
}
