/// <reference types="cypress" />
import { Given, When, Then } from "@badeball/cypress-cucumber-preprocessor";


When("I navigate to the schedules page", () => {
  cy.visit("/schedule/overview");
});



When("I click on schedule with name {string}", (name: string) => {
    cy.contains(`${name}`).click();
    //cy.get(`tr[role='button'][data-cy=schedule-${id}]`).click();
  });

  Then("I see a list of students who can be enrolled", () => {
    cy.contains("Students").should("be.visible");
  });

  Then("I should see available rooms", () => {
    cy.contains("Available Rooms").should("be.visible");
  });

  When("I click the enroll button for student with number {string}", (number: string) => {
    cy.contains('tr', number) 
      .within(() => {
        cy.contains('button', 'Enroll').click();
      });
  });

  When("I click the book button for room with name {string} in building {string}", (roomName: string, buildingName: string) => {
    cy.get('section') // область с таблицей комнат
      .contains('Available Rooms')
      .parent()
      .within(() => {
        cy.get('tr').contains(roomName).parent('tr').within(() => {
          cy.contains(buildingName);
          cy.contains('button', 'Book').click();
        });
      });
  });


  Then("the enroll button for student with number {string} should not be visible anymore", (number: string) => {
    cy.contains('tr', number)
      .within(() => {
        cy.contains('Enroll').should('not.exist'); 
      });
  });

  Then("no book buttons should be visible", () => {
    cy.get('section')
      .contains('Available Rooms')
      .parent()
      .within(() => {
        cy.contains('button', 'Book').should('not.exist');
      });
  });

  Then("schedule with course name {string} should have room with name {string}", (courseName: string, roomName: string) => {
    cy.contains('tr', courseName)
      .within(() => {
        cy.get('td').last().should('contain', roomName);
      });
  });


  Then("the number of enrolled students for schedule with name {string} should be {int}", (name: string, expectedCount: number) => {
    cy.contains('tr', name)
      .within(() => {
        cy.get('td').eq(-2).should('contain', expectedCount);
      });
  });

  Then("I see a schedule assign room error", () => {
    cy.contains("Failed to assign room").should("be.visible");
  });



