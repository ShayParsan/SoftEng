/// <reference types="cypress" />
import { Given, When, Then } from "@badeball/cypress-cucumber-preprocessor";
import { DataTable } from "@badeball/cypress-cucumber-preprocessor";

Given("I am an unauthenticated user", () => {
  cy.logout();
});

Given("I am logged in as {string} with password {string}", (username: string, password: string) => {
  cy.login(username, password);
});

When("I navigate to the lecturers page", () => {
  cy.visit("/lecturers");
});

Then("I should see an unauthorized error message", () => {
  cy.contains("not authorized").should("be.visible");
});

Then("I should see a table with all lecturers", () => {
  cy.get("table").should("exist");
});

Then("I should see the following lecturers:", (dataTable: DataTable) => {
  const expectedLecturers = dataTable.hashes(); // Array of row-objects

  cy.get("table tbody tr").should("have.length", expectedLecturers.length);

  expectedLecturers.forEach((lecturer, index) => {
    cy.get("table tbody tr").eq(index).within(() => {
      cy.contains(lecturer["First Name"]);
      cy.contains(lecturer["Last Name"]);
      cy.contains(lecturer["Email"]);
      cy.contains(lecturer["Expertise"]);
    });
  });
});
