CREATE VIEW lessones_statistics AS
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


CREATE VIEW avarage_lessons AS
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


CREATE VIEW instructor_statistics AS
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


CREATE VIEW ensemble_statistics AS
    SELECT
         genre
        ,ensemble.max_num_of_students
        ,ensemble.min_num_of_students
        ,ensemble.type_of_lesson
        ,EXTRACT(YEAR FROM NOW()) AS year
        ,(EXTRACT(WEEK FROM NOW()) + 1) AS week_number
        ,EXTRACT(DAY FROM NOW()) AS DAY
        ,CASE 
            WHEN COUNT(student_in_lesson.student_id) = (SELECT ensemble.max_num_of_students FROM ensemble) THEN 'Full booked'
            WHEN COUNT(student_in_lesson.student_id) = (SELECT ensemble.min_num_of_students FROM ensemble)  THEN 'Has more seats left'
            ELSE 'Lesson not confirmed yet'
        END status
    from public.ensemble 
    INNER JOIN lesson ON  ensemble.lesson_id=lesson.lesson_id 
    INNER JOIN student_in_lesson ON student_in_lesson.lesson_id = lesson.lesson_id
    GROUP BY(YEAR,genre,ensemble.max_num_of_students,ensemble.min_num_of_students,ensemble.type_of_lesson,lesson.time)
    ORDER BY(EXTRACT(DAY FROM lesson.time), genre);
