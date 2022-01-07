CREATE VIEW all_lessones AS
    SELECT 
        EXTRACT(YEAR FROM lesson.time) AS year,
        EXTRACT(MONTH FROM lesson.time) AS month,
        COUNT(lesson.type_of_lesson) AS numbre_of_lesson
    FROM lesson
    GROUP BY (year, month)
    ORDER BY MONTH;


CREATE VIEW lessones_statistics AS
    SELECT 
        EXTRACT(MONTH FROM lesson.time) AS month,
        COUNT(ensemble.type_of_lesson)  AS ensemble,
        COUNT(group_lesson.type_of_lesson)  AS group,
        COUNT(individual_lesson.type_of_lesson)  AS individual
    FROM lesson
    FULL OUTER JOIN ensemble ON  lesson.lesson_id=ensemble.lesson_id
    FULL OUTER JOIN group_lesson ON  lesson.lesson_id=group_lesson.lesson_id
    FULL OUTER JOIN individual_lesson ON  lesson.lesson_id=individual_lesson.lesson_id
    GROUP BY month
    ORDER BY  MONTH;
--------------------------------------------------------------------------------------------------------
CREATE VIEW avarage_lessons AS
    SELECT 
        EXTRACT(YEAR FROM lesson.time) AS year,
        COUNT(lesson.type_of_lesson) AS numbre_of_lesson,
        CAST(COUNT(*) AS NUMERIC)/12 AS avarage_lessons
    FROM lesson
    GROUP BY (year);
--------------------------------------------------------------------------------------------------------

CREATE VIEW instructor_statistics AS
    SELECT 
        first_name || ' ' || last_name AS Instructor_Name,
        EXTRACT(MONTH FROM lesson.time) AS month,
        lesson.type_of_lesson
    FROM lesson
    INNER JOIN instructor ON lesson.employment_id=instructor.employment_id
    INNER JOIN person ON instructor.person_id=person.person_id
    ORDER BY  MONTH;


CREATE VIEW instructors_work_too_much AS
    SELECT 
        EXTRACT(MONTH FROM le.time) AS month,
        COUNT(le.employment_id)  AS number_of_instructors_works_much
    FROM person AS pe 
    INNER JOIN instructor AS inj 
     ON pe.person_id=inj.person_id
    INNER JOIN lesson   AS le
    ON inj.employment_id=le.employment_id
    GROUP BY month
    HAVING (COUNT(le.employment_id)>1)
    ORDER BY  MONTH;  


CREATE VIEW instructors_total_lesson AS
    SELECT  
        first_name || ' ' || last_name AS Instructor_Name ,
        COUNT(lesson.lesson_id) AS total_lessons
    FROM public.lesson
    INNER JOIN instructor ON instructor.employment_id=lesson.employment_id
    INNER JOIN person ON instructor.person_id=person.person_id
    GROUP BY Instructor_Name;