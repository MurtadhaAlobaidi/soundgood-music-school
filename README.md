#  [Soundgood Music School](https://github.com/mhaao/Soundgood-Music-School)

Description
This project "Soundgood Music School" on KTH-Data Storage Paradigms(IV1351). 
The purpose is to facilitate information handling and business transactions for the Soundgood music school company, 
by developing a database which handles all the school's data and also an application that can handle some of the transactions. 

 
# Built With languages
* [SQL](https://pages.github.com/)
* [JAVA](https://pages.github.com/)

# TOOLS
This tools was helpeing to built this project.You can download them from here:
* [Visual Studio](https://code.visualstudio.com)
* [SQL Shell(psql) 'terminal'](https://www.postgresql.org/download/)
* [PostgresSQL](https://www.postgresql.org/download/)
* [PgAdmin 4](https://www.pgadmin.org/download/)
* [Lucidchart](https://www.lucidchart.com/pages/examples/flowchart_software)
* [Astah](https://astah.net/downloads/)
 
 # To connect java application with the database using JDBC.
 We used `Eclipse` to build a `Maven-project` and connected a database with JAVA using JDBC.
 >You need to add the `JDBC` file to your project on `Eclipse`.You can download them from here:
 * [JDBC](https://jdbc.postgresql.org/download.html)
 * [Eclipse](https://www.eclipse.org/downloads/)
 * [Maven](https://maven.apache.org/download.cgi)
 
 PostgreSQL JDBC Driver Postgresql 
 [JDBC](https://search.maven.org/artifact/org.postgresql/postgresql/42.3.1/jar)

 
# How to Execute
1. Clone this git repository.  `git clone https://github.com/mhaao/Soundgood-Music-School.git`
1. Change to the newly created directory cd soundgood-music-school.  `cd "soundgood-music-school"`
    1. Change the url to match your database.
1. Create a database that can be reached with postgres.That is a database called soundgood-music-school.
1. Build the project with the command `mvn install`
1. Run the program with the command `mvn exec:java`

## Commands for the bank program
* `help` displays all commands.
* `new <rent new instrument>` creates a new rent new instrument.
* `list` lists all instruments.
* `ensembles <ensembles lessones>` shows the all ensembles lessones.
* `rent <all rented instruments>` shows the all rented instruments
* `terminate <you can meak terminate instrument>` you can meak terminate instrument.
* `quit` quits the application.


 
 
 
# Created by 
- Murtadha Alobaidi mhaao@kth.se 
- Abdullah Trabulsiah abdtra@kth.se
