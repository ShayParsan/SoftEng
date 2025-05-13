package be.ucll.se.courses.backend.controller.dto;

import be.ucll.se.courses.backend.model.Schedule;

import java.time.Instant;

public class ScheduleDto {
    private Long id;
    private Instant start;
    private Instant end;
    private String courseName;

    public ScheduleDto(Schedule schedule) {
        this.id = schedule.getId();
        this.start = schedule.getStart();
        this.end = schedule.getEnd();
        this.courseName = schedule.getCourse().getName();
    }

    public Long getId() { return id; }
    public Instant getStart() { return start; }
    public Instant getEnd() { return end; }
    public String getCourseName() { return courseName; }
}
