--IV1351 KTH
--Project "Soundgood Music School", Task-2 "Logical and Physical Model"
--Created by Murtadha Alobaidi mhaao@kth.se & Abdullah Trabulsiah abdtra@kth.se
--This script we inserts data
 
-- instrument
insert into instrument (type, number_in_stock, id_number, brand, monthly_renting_fee) values ('accent', 1, '57-3185587', 'Gibson', '€9,94');
insert into instrument (type, number_in_stock, id_number, brand, monthly_renting_fee) values ('accordion', 1, '17-8813587', 'Harman International Industries', '€61,44');
insert into instrument (type, number_in_stock, id_number, brand, monthly_renting_fee) values ('acoustic guitar', 1, '92-1674363', 'Shure', '€32,96');
insert into instrument (type, number_in_stock, id_number, brand, monthly_renting_fee) values ('adagio', 1, '00-6222341', 'Yamaha', '€54,12');
insert into instrument (type, number_in_stock, id_number, brand, monthly_renting_fee) values ('aeolian harp', 1, '43-5505748', 'Fender', '€38,37');

--person
insert into person ( first_name, last_name, person_number, street, zip, city) values ( 'Walther',   'Walther',   '3912628688', 'Debra',    '33456',      'Pskov');
insert into person ( first_name, last_name, person_number, street, zip, city) values ( 'Gerry',     'Walther',   '5127224364', 'Scoville', '40321',    'Changfu');
insert into person ( first_name, last_name, person_number, street, zip, city) values ( 'Dre',       'Dre',       '3114026532', 'Walton',    '02221',     'Säter');
insert into person ( first_name, last_name, person_number, street, zip, city) values ( 'Kerr',      'Kerr',      '7724282684', 'Warbler',    '83547',  'Longfeng');
insert into person ( first_name, last_name, person_number, street, zip, city) values ( 'Taddeusz',  'Taddeusz',  '2262059845', 'Daystar',    '32347',     'Parys');
insert into person ( first_name, last_name, person_number, street, zip, city) values ( 'Ezechiel',  'Ezechiel',  '5562153165', 'Stang',      '00689', 'Grigiškės');
insert into person ( first_name, last_name, person_number, street, zip, city) values ( 'Fransisco', 'Fransisco', '2374513718', 'Northland',   '32054','Jampang Kulon');
insert into person ( first_name, last_name, person_number, street, zip, city) values ( 'Gaultiero', 'Gaultiero', '5072114813', 'Logan',      '30099',       'Mandera');
insert into person ( first_name, last_name, person_number, street, zip, city) values ( 'Maxie',     'Maxie',     '0205247261', 'Hayes',      '80314',     'Xitieshan');
insert into person ( first_name, last_name, person_number, street, zip, city) values ( 'Preston',   'Preston',   '5152446820', 'High Crossing', '40831',  'Zongzhai');

--phone
insert into phone (phone,person_id) values ('076 599 2858', '30');
insert into phone (phone,person_id) values ('076 952 7997', '31');
insert into phone (phone,person_id) values ('076 258 2423', '32');
insert into phone (phone,person_id) values ('076 622 0193', '33');
insert into phone (phone,person_id) values ('076 219 6050', '34');
insert into phone (phone,person_id) values ('076 897 6364', '35');
insert into phone (phone,person_id) values ('076 723 9819', '36');
insert into phone (phone,person_id) values ('076 882 8560', '37');
insert into phone (phone,person_id) values ('076 602 5463', '38');
insert into phone (phone,person_id) values ('076 688 6727', '39');

--email
insert into email (email,person_id) values ('pkenn0@histats.com'        ,'30' );
insert into email (email,person_id) values ('mrathborne1@squidoo.com'   ,'31' );
insert into email (email,person_id) values ('iscarce2@fastcompany.com'  ,'32' );
insert into email (email,person_id) values ('rstirrup3@shareasale.com'  ,'33' );
insert into email (email,person_id) values ('rnunnerley4@vistaprint.com','34' );
insert into email (email,person_id) values ('dwoolward5@toplist.cz'     ,'35' );
insert into email (email,person_id) values ('nkorous6@columbia.edu'     ,'36' );
insert into email (email,person_id) values ('gmaplethorpe7@cnbc.com'    ,'37' );
insert into email (email,person_id) values ('jtavernor8@amazon.de'      ,'38' );
insert into email (email,person_id) values ('pwillis9@techcrunch.com'   ,'39' );

--instructor
insert into instructor (total_working_hours, person_id) values (1.08 , 38);
insert into instructor (total_working_hours, person_id) values (10.00, 39);

--lesson
insert into lesson (place, price, skill_level, employment_id, time) values ('Home',  '€42,56','advanced',     150, '2021-06-05 08:00');
insert into lesson (place, price, skill_level, employment_id, time) values ('School','€57,30','beginner',     151, '2021-05-17 10:00');
insert into lesson (place, price, skill_level, employment_id, time) values ('School','€51,89','intermediate', 151, '2021-03-01 15:00');

--parent
insert into parent (person_id) values (35);
insert into parent (person_id) values (36);
insert into parent (person_id) values (37);

--student
insert into student (member_id, person_id) values (103, 30);
insert into student (member_id, person_id) values (103, 31);
insert into student (member_id, person_id) values (104, 32);
insert into student (member_id, person_id) values (104, 33);
insert into student (member_id, person_id) values (105, 34);

--student_in_lesson
insert into student_in_lesson (student_id, lesson_id) values (120, 50);
insert into student_in_lesson (student_id, lesson_id) values (121, 50);
insert into student_in_lesson (student_id, lesson_id) values (122, 51);
insert into student_in_lesson (student_id, lesson_id) values (123, 51);
insert into student_in_lesson (student_id, lesson_id) values (124, 52);

--ensemble
insert into ensemble (lesson_id, genre) values (50, 'Drama');
insert into ensemble (lesson_id, genre) values (51, 'Fantasy');
insert into ensemble (lesson_id, genre) values (52, 'Comedy');

--payment
insert into payment (student_id, total, lesson_fee, instrument_renting_fee) values (120, 9, '€74,24', '€33,03');
insert into payment (student_id, total, lesson_fee, instrument_renting_fee) values (121, 9, '€94,80', '€33,10');
insert into payment (student_id, total, lesson_fee, instrument_renting_fee) values (122, 6, '€27,21', '€79,57');
insert into payment (student_id, total, lesson_fee, instrument_renting_fee) values (123, 8, '€90,85', '€12,02');
insert into payment (student_id, total, lesson_fee, instrument_renting_fee) values (124, 14, '€80,88', '€72,92');

--rented_instruments
insert into rented_instruments (student_id, instrument_id) values (120, 1);
insert into rented_instruments (student_id, instrument_id) values (121, 2);
insert into rented_instruments (student_id, instrument_id) values (122, 3);