select id, name, image, price, synopsis, description, classification, teacher_id as teacherId,
       status,start_time as startTime, update_time as updateTime 
from course 
where update_time >:sql_last_value 
order by update_time asc