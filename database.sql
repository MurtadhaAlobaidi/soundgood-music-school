--IV1351 KTH
--Project 'Soundgood Music School', Task-2 'Logical and Physical Model'
--Created by Murtadha Alobaidi mhaao@kth.se & Abdullah Trabulsiah abdtra@kth.se
--This script we creates the database

CREATE TABLE "instrument" (
 "instrument_id" int GENERATED ALWAYS AS IDENTITY
 (start with 1 increment by 1),
 "type" VARCHAR(500) NOT NULL,
 "brand" VARCHAR(500) NOT NULL,
 "renting_fee" VARCHAR(500) NOT NULL
);

ALTER TABLE "instrument" ADD CONSTRAINT "PK_instrument" PRIMARY KEY ("instrument_id");

CREATE TABLE "person" (
 "person_id" int GENERATED ALWAYS AS IDENTITY
 (start with 50 increment by 1),
 "first_name" VARCHAR(500) NOT NULL,
 "last_name" VARCHAR(500) NOT NULL,
 "person_number" VARCHAR(12),
 "street" VARCHAR(500) NOT NULL,
 "zip" VARCHAR(500) NOT NULL,
 "city" VARCHAR(500) NOT NULL
);

ALTER TABLE "person" ADD CONSTRAINT "PK_person" PRIMARY KEY ("person_id");


CREATE TABLE "phone" (
 "phone" VARCHAR(16) NOT NULL,
 "person_id" INT NOT NULL
);

ALTER TABLE "phone" ADD CONSTRAINT "PK_phone" PRIMARY KEY ("phone","person_id");


CREATE TABLE "email" (
 "email" VARCHAR(500) NOT NULL,
 "person_id" INT NOT NULL
);

ALTER TABLE "email" ADD CONSTRAINT "PK_email" PRIMARY KEY ("email","person_id");

CREATE TABLE "instructor" (
 "employment_id" int GENERATED ALWAYS AS IDENTITY
 (start with 250 increment by 1),
 "total_working_hours" VARCHAR(500) NOT NULL,
 "person_id" INT NOT NULL
);

ALTER TABLE "instructor" ADD CONSTRAINT "PK_instructor" PRIMARY KEY ("employment_id");

CREATE TABLE "lesson" (
 "lesson_id" int GENERATED ALWAYS AS IDENTITY
 (start with 100 increment by 1),
 "place" VARCHAR(500) NOT NULL,
 "price" VARCHAR(500) NOT NULL,
 "employment_id" INT NOT NULL,
 "time" TIMESTAMP(6) NOT NULL,
 "type_of_lesson" VARCHAR(500) NOT NULL
);

ALTER TABLE "lesson" ADD CONSTRAINT "PK_lesson" PRIMARY KEY ("lesson_id");

CREATE TABLE "parent" (
 "member_id" int GENERATED ALWAYS AS IDENTITY
 (start with 150 increment by 1),
 "person_id" INT NOT NULL
);

ALTER TABLE "parent" ADD CONSTRAINT "PK_parent" PRIMARY KEY ("member_id","person_id");

CREATE TABLE "student" (
 "student_id"  int GENERATED ALWAYS AS IDENTITY
 (start with 200 increment by 1),
 "person_id" INT NOT NULL,
 "member_id" INT NOT NULL
);

ALTER TABLE "student" ADD CONSTRAINT "PK_student" PRIMARY KEY ("student_id" );


CREATE TABLE "student_in_lesson" (
 "student_id"  INT NOT NULL,
 "lesson_id" INT NOT NULL
);

ALTER TABLE "student_in_lesson" ADD CONSTRAINT "PK_student_in_lesson" PRIMARY KEY ("student_id" ,"lesson_id");


CREATE TABLE "ensemble" (
 "lesson_id" INT NOT NULL,
 "genre" VARCHAR(500) NOT NULL,
 "max_num_of_students" INT NOT NULL,
 "min_num_of_students" INT NOT NULL,
 "type_of_lesson" VARCHAR(500) NOT NULL
);

ALTER TABLE "ensemble" ADD CONSTRAINT "PK_ensemble" PRIMARY KEY ("lesson_id");


CREATE TABLE "group_lesson" (
 "lesson_id" INT NOT NULL,
 "max_num_of_students" INT NOT NULL,
 "min_num_of_students" INT NOT NULL,
 "skill_level" VARCHAR(500) NOT NULL,
 "type_of_lesson" VARCHAR(500) NOT NULL
);

ALTER TABLE "group_lesson" ADD CONSTRAINT "PK_group_lesson" PRIMARY KEY ("lesson_id");


CREATE TABLE "individual_lesson" (
 "lesson_id" INT NOT NULL,
 "skill_level" VARCHAR(500) NOT NULL,
 "type_of_lesson" VARCHAR(500) NOT NULL
);

ALTER TABLE "individual_lesson" ADD CONSTRAINT "PK_individual_lesson" PRIMARY KEY ("lesson_id");


CREATE TABLE "payment" (
 "student_id"  INT NOT NULL,
 "total" VARCHAR(500) NOT NULL,
 "lesson_fee" VARCHAR(500) NOT NULL,
 "instrument_renting_fee" VARCHAR(500) NOT NULL
);

ALTER TABLE "payment" ADD CONSTRAINT "PK_payment" PRIMARY KEY ("student_id" );


CREATE TABLE "rented_instruments" (
 "student_id"  INT NOT NULL,
 "instrument_id" INT NOT NULL
);

ALTER TABLE "rented_instruments" ADD CONSTRAINT "PK_rented_instruments" PRIMARY KEY ("student_id" ,"instrument_id");


ALTER TABLE "phone" ADD CONSTRAINT "FK_phone_0" FOREIGN KEY ("person_id") REFERENCES "person" ("person_id");


ALTER TABLE "email" ADD CONSTRAINT "FK_email_0" FOREIGN KEY ("person_id") REFERENCES "person" ("person_id");


ALTER TABLE "instructor" ADD CONSTRAINT "FK_instructor_0" FOREIGN KEY ("person_id") REFERENCES "person" ("person_id");

ALTER TABLE "parent" ADD CONSTRAINT "FK_parent_0" FOREIGN KEY ("person_id") REFERENCES "person" ("person_id");


ALTER TABLE "student" ADD CONSTRAINT "FK_student_0" FOREIGN KEY ("person_id") REFERENCES "person" ("person_id");

ALTER TABLE "student_in_lesson" ADD CONSTRAINT "FK_student_in_lesson_0" FOREIGN KEY ("student_id" ) REFERENCES "student" ("student_id" );


ALTER TABLE "payment" ADD CONSTRAINT "FK_payment_0" FOREIGN KEY ("student_id" ) REFERENCES "student" ("student_id" );


ALTER TABLE "rented_instruments" ADD CONSTRAINT "FK_rented_instruments_0" FOREIGN KEY ("student_id" ) REFERENCES "student" ("student_id" );
ALTER TABLE "rented_instruments" ADD CONSTRAINT "FK_rented_instruments_1" FOREIGN KEY ("instrument_id") REFERENCES "instrument" ("instrument_id");