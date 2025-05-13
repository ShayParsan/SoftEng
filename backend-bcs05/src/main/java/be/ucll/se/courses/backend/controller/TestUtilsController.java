package be.ucll.se.courses.backend.controller;

import be.ucll.se.courses.backend.repository.DbInitializer;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test-utils")
public class TestUtilsController {

    private DbInitializer dbInitializer;

    public TestUtilsController(DbInitializer dbInitializer) {
        this.dbInitializer = dbInitializer;
    }

    @PostMapping("/reset-database")
    public ResponseEntity<Void> resetDatabase() {
        System.out.println("Resetting and reinitializing database...");
        dbInitializer.init();
        return ResponseEntity.ok().build();
    }
}
