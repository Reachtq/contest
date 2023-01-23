package com.group.contestback.repositories;

import com.group.contestback.models.Attempts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AttemptsRepo extends JpaRepository<Attempts, Integer> {
    List<Attempts> findAllByTaskIdAndUserId(Integer taskId, Integer userId);
    @Query(
            value = "SELECT t1.id, t1.userid, t1.taskid, t1.courseid, t1.time, t1.succeeded, t1.solution " +
                    "FROM (   select a.id, a.userid, a.taskid, a.courseid, a.time, a.succeeded, a.solution " +
                    "from(select Max(time) as maxTime, taskid from attempts where userid = ?1 group by taskid)" +
                    " r inner join attempts a on r.taskid = a.taskid and a.time = r.maxTime) t1 " +
                    "WHERE NOT EXISTS (SELECT t2.taskid FROM scores t2 WHERE t1.taskid = t2.taskid) and courseid = ?2",
            nativeQuery = true)
    List<Attempts> findByTaskUserMaxTime(Integer userId, Integer courseId);
    List<Attempts> findAllByCourseId(Integer courseId);
    List<Attempts> findAllByCourseIdAndUserId(Integer courseId, Integer userId);
}
