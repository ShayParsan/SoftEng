/// <reference types="cypress" />
import { Given, When, Then } from "@badeball/cypress-cucumber-preprocessor";
import { DataTable } from "@badeball/cypress-cucumber-preprocessor";

When("I navigate to the buildings page", () => {
  cy.visit("/buildings");
});

Then("I should see the buildings overview with rooms and schedules", () => {
  const today = new Date();

  const yyyy = today.getFullYear();
  const mm = String(today.getMonth() + 1).padStart(2, '0');
  const dd = String(today.getDate()).padStart(2, '0');

  const formattedDate = `${dd}-${mm}-${yyyy}`;

  cy.contains("h1", "Buildings Overview").should("be.visible");

  cy.contains("Main Building").should("be.visible");
  cy.contains("Room 101").should("be.visible");
  cy.contains(`${formattedDate} 08:30 – ${formattedDate} 10:30`).should("exist");
  cy.contains(`${formattedDate} 13:30 – ${formattedDate} 15:30`).should("exist");

  cy.contains("Room 303").should("exist");
  cy.contains("Technology Center").should("be.visible");
  cy.contains("Room 202").should("exist");
});