package be.ucll.se.courses.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @ManyToOne
    @JoinColumn(name = "building_id")
    @NotNull
    private Building building;

    @OneToMany(mappedBy = "room")
    @JsonBackReference
    private List<Schedule> schedules = new ArrayList<>();

    public Room() {}

    public Room(String name, Building building) {
        this.name = name;
        this.building = building;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Building getBuilding() {
        return building;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }
}

