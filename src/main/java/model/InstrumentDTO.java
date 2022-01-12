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
