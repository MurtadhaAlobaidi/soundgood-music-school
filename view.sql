-- CREATE VIEW lessones_statistics AS
    SELECT 
        EXTRACT(MONTH FROM lesson.time) AS month,
        EXTRACT(YEAR FROM lesson.time) AS YEAR,
        COUNT(ensemble.type_of_lesson)  AS ensemble,
        COUNT(group_lesson.type_of_lesson)  AS group,
        COUNT(individual_lesson.type_of_lesson)  AS individual
    FROM lesson
    FULL OUTER JOIN ensemble ON  lesson.lesson_id=ensemble.lesson_id
    FULL OUTER JOIN group_lesson ON  lesson.lesson_id=group_lesson.lesson_id
    FULL OUTER JOIN individual_lesson ON  lesson.lesson_id=individual_lesson.lesson_id
    GROUP BY (month,year)
    ORDER BY  MONTH;


-- CREATE VIEW avarage_lessons AS
    SELECT 
        EXTRACT(YEAR FROM lesson.time) AS year,
        COUNT(lesson.type_of_lesson) AS total_num_of_lessons,
        CAST(COUNT(individual_lesson.type_of_lesson) AS NUMERIC)/12 AS average_num_of_individual_per_month,
        CAST(COUNT(group_lesson.type_of_lesson) AS NUMERIC)/12 AS average_num_of_group_per_month,
        CAST(COUNT(ensemble.type_of_lesson) AS NUMERIC)/12 AS average_num_of_ensemble_per_month
    FROM lesson
    FULL OUTER JOIN ensemble ON  lesson.lesson_id=ensemble.lesson_id
    FULL OUTER JOIN group_lesson ON  lesson.lesson_id=group_lesson.lesson_id
    FULL OUTER JOIN individual_lesson ON  lesson.lesson_id=individual_lesson.lesson_id
    GROUP BY (year);


-- CREATE VIEW instructor_statistics AS
    SELECT  
        first_name || ' ' || last_name AS Instructor_Name,
        COUNT(lesson.lesson_id) AS total_lessons,
        EXTRACT(MONTH FROM lesson.time) AS month,
        CASE
            WHEN COUNT(lesson.lesson_id) = 1 THEN 'False'
            WHEN COUNT(lesson.lesson_id) > 1 THEN 'True'
        END AS Overtime
    FROM public.lesson
    INNER JOIN instructor ON instructor.employment_id=lesson.employment_id
    INNER JOIN person ON instructor.person_id=person.person_id
    GROUP BY (Instructor_Name,MONTH) 
    ORDER BY MONTH;