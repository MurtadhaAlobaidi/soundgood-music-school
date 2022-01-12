--IV1351 KTH
--Project "Soundgood Music School", Task-2 "Logical and Physical Model"
--Created by Murtadha Alobaidi mhaao@kth.se & Abdullah Trabulsiah abdtra@kth.se
--This script we inserts data

 
                                                                                                                        
insert into instrument (type, brand, renting_fee, status) values ('accent',    'Gibson', '€9,94'                          ,'rented'   );                             
insert into instrument (type, brand, renting_fee, status) values ('accordion', 'Harman International Industries', '€61,44','rented'   );   
insert into instrument (type, brand, renting_fee, status) values ('guitar',    'Shure', '€32,96'                          ,'rented'   );                             
insert into instrument (type, brand, renting_fee, status) values ('adagio',    'Yamaha', '€54,12'                         ,'available');                            
insert into instrument (type, brand, renting_fee, status) values ('aeolian harp',  'Fender', '€38,37'                     ,'available');                        


insert into person ( first_name, last_name, person_number, street, zip, city) values ( 'Walther',   'Walther',   '3912628688', 'Debra',    '33456',      'Pskov');      
insert into person ( first_name, last_name, person_number, street, zip, city) values ( 'Gerry',     'Gerry',     '5127224364', 'Scoville', '40321',    'Changfu');      
insert into person ( first_name, last_name, person_number, street, zip, city) values ( 'Dre',       'Dre',       '3114026532', 'Walton',    '02221',     'Säter');      
insert into person ( first_name, last_name, person_number, street, zip, city) values ( 'Kerr',      'Kerr',      '7724282684', 'Warbler',    '83547',  'Longfeng');     
insert into person ( first_name, last_name, person_number, street, zip, city) values ( 'Taddeusz',  'Taddeusz',  '2262059845', 'Daystar',    '32347',     'Parys');     
insert into person ( first_name, last_name, person_number, street, zip, city) values ( 'Ezechiel',  'Ezechiel',  '5562153165', 'Stang',      '00689', 'Grigiškės');     
insert into person ( first_name, last_name, person_number, street, zip, city) values ( 'Fransisco', 'Fransisco', '2374513718', 'Northland',   '32054','Jampang Kulon'); 
insert into person ( first_name, last_name, person_number, street, zip, city) values ( 'Gaultiero', 'Gaultiero', '5072114813', 'Logan',      '30099',       'Mandera'); 
insert into person ( first_name, last_name, person_number, street, zip, city) values ( 'Maxie',     'Maxie',     '0205247261', 'Hayes',      '80314',     'Xitieshan'); 
insert into person ( first_name, last_name, person_number, street, zip, city) values ( 'Preston',   'Preston',   '5152446820', 'High Crossing', '40831',  'Zongzhai');  


insert into phone (phone,person_id) values ('076 599 2858', '50');
insert into phone (phone,person_id) values ('076 952 7997', '51');
insert into phone (phone,person_id) values ('076 258 2423', '52');
insert into phone (phone,person_id) values ('076 622 0193', '53');
insert into phone (phone,person_id) values ('076 219 6050', '54');
insert into phone (phone,person_id) values ('076 897 6364', '55');
insert into phone (phone,person_id) values ('076 723 9819', '56');
insert into phone (phone,person_id) values ('076 882 8560', '57');
insert into phone (phone,person_id) values ('076 602 5463', '58');
insert into phone (phone,person_id) values ('076 688 6727', '59');



insert into email (email,person_id) values ('pkenn0@histats.com'        ,'50' );
insert into email (email,person_id) values ('mrathborne1@squidoo.com'   ,'51' );
insert into email (email,person_id) values ('iscarce2@fastcompany.com'  ,'52' );
insert into email (email,person_id) values ('rstirrup3@shareasale.com'  ,'53' );
insert into email (email,person_id) values ('rnunnerley4@vistaprint.com','54' );
insert into email (email,person_id) values ('dwoolward5@toplist.cz'     ,'55' );
insert into email (email,person_id) values ('nkorous6@columbia.edu'     ,'56' );
insert into email (email,person_id) values ('gmaplethorpe7@cnbc.com'    ,'57' );
insert into email (email,person_id) values ('jtavernor8@amazon.de'      ,'58' );
insert into email (email,person_id) values ('pwillis9@techcrunch.com'   ,'59' );


insert into instructor (total_working_hours, person_id) values (20.00, 58); 
insert into instructor (total_working_hours, person_id) values (10.00, 59); 


insert into lesson (place, price, employment_id, time,type_of_lesson) values ('Home',  '€42,56', 250, '2021-08-11 17:06:46', 'Individual'); 
insert into lesson (place, price, employment_id, time,type_of_lesson) values ('School','€57,30', 251, '2021-07-18 17:28:30','Group'); 
insert into lesson (place, price, employment_id, time,type_of_lesson) values ('School','€51,89', 251, '2021-09-24 11:25:50','Ensemble'); 
insert into lesson (place, price, employment_id, time,type_of_lesson) values ('Home',  '€42,56', 250, '2021-08-16 14:44:13', 'Individual'); 
insert into lesson (place, price, employment_id, time,type_of_lesson) values ('School','€57,30', 251, '2021-07-24 17:46:27', 'Group' ); 
insert into lesson (place, price, employment_id, time,type_of_lesson) values ('School','€51,89', 251, '2021-05-20 23:12:00', 'Individual'); 
insert into lesson (place, price, employment_id, time,type_of_lesson) values ('Home',  '€42,56', 250, '2021-01-18 11:17:56', 'Individual'); 
insert into lesson (place, price, employment_id, time,type_of_lesson) values ('School','€57,30', 251, '2021-01-10 09:39:15', 'Individual'); 
insert into lesson (place, price, employment_id, time,type_of_lesson) values ('School','€51,89', 251, '2021-06-14 22:37:45', 'Individual'); 


insert into parent (person_id) values (55); 
insert into parent (person_id) values (56); 
insert into parent (person_id) values (57); 


insert into student (member_id ,  person_id, total_rented_instruments_currently ) values (150 , 50, 1);    
insert into student ( member_id , person_id, total_rented_instruments_currently ) values (150 , 51, 1);    
insert into student ( member_id , person_id, total_rented_instruments_currently ) values (151 , 52, 1);    
insert into student ( member_id , person_id, total_rented_instruments_currently ) values (151 , 53, 0);    
insert into student ( member_id , person_id, total_rented_instruments_currently ) values (152 , 54, 0);    
, 


insert into student_in_lesson (student_id, lesson_id) values (200, 100);
insert into student_in_lesson (student_id, lesson_id) values (201, 108);
insert into student_in_lesson (student_id, lesson_id) values (202, 107);
insert into student_in_lesson (student_id, lesson_id) values (204, 103);
insert into student_in_lesson (student_id, lesson_id) values (202, 105);
insert into student_in_lesson (student_id, lesson_id) values (203, 106);
insert into student_in_lesson (student_id, lesson_id) values (200, 104);
insert into student_in_lesson (student_id, lesson_id) values (204, 104);
insert into student_in_lesson (student_id, lesson_id) values (202, 101);
insert into student_in_lesson (student_id, lesson_id) values (200, 101);
insert into student_in_lesson (student_id, lesson_id) values (203, 101);
insert into student_in_lesson (student_id, lesson_id) values (200, 102);
insert into student_in_lesson (student_id, lesson_id) values (201, 102);
insert into student_in_lesson (student_id, lesson_id) values (202, 102);
insert into student_in_lesson (student_id, lesson_id) values (203, 102);
insert into student_in_lesson (student_id, lesson_id) values (204, 102);


insert into ensemble (lesson_id, genre, max_num_of_students, min_num_of_students,type_of_lesson ) values (102, 'Rock', 5, 3,'Ensemble');


insert into group_lesson (lesson_id, max_num_of_students, min_num_of_students, skill_level,type_of_lesson ) values (104, 3, 2, 'advanced','Group');
insert into group_lesson (lesson_id, max_num_of_students, min_num_of_students, skill_level,type_of_lesson ) values (101, 3, 2, 'beginner','Group');


insert into individual_lesson (lesson_id, skill_level,type_of_lesson ) values (100, 'advanced','Individual');
insert into individual_lesson (lesson_id, skill_level,type_of_lesson ) values (103, 'beginner','Individual');
insert into individual_lesson (lesson_id, skill_level,type_of_lesson ) values (105, 'intermediate','Individual');
insert into individual_lesson (lesson_id, skill_level,type_of_lesson ) values (106, 'beginner','Individual');
insert into individual_lesson (lesson_id, skill_level,type_of_lesson ) values (107, 'advanced','Individual');
insert into individual_lesson (lesson_id, skill_level,type_of_lesson ) values (108, 'intermediate','Individual');


insert into payment (student_id, total, lesson_fee, instrument_renting_fee) values (200, 9, '€74,24', '€33,03');
insert into payment (student_id, total, lesson_fee, instrument_renting_fee) values (201, 9, '€94,80', '€33,10');
insert into payment (student_id, total, lesson_fee, instrument_renting_fee) values (202, 6, '€27,21', '€79,57');
insert into payment (student_id, total, lesson_fee, instrument_renting_fee) values (203, 8, '€90,85', '€12,02');
insert into payment (student_id, total, lesson_fee, instrument_renting_fee) values (204, 14, '€80,88', '€72,92');


insert into rented_instruments (student_id, instrument_id, renting_status, rent_date, return_date  ) values (200, 1, 'Ongoing', '2021-07-01', '2022-03-01');
insert into rented_instruments (student_id, instrument_id, renting_status, rent_date, return_date  ) values (201, 2, 'Ongoing', '2021-08-11', '2022-02-11');
insert into rented_instruments (student_id, instrument_id, renting_status, rent_date, return_date  ) values (202, 3,'Ongoing',  '2021-08-11', '2022-02-01' );