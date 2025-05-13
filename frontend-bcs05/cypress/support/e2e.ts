/// <reference types="cypress" />
// cypress/support/e2e.js

import "@shelex/cypress-allure-plugin";

Cypress.Commands.add("logout", () => { /* … */ });
Cypress.Commands.add("login", (u, p) => { /* … */ });

console.log("🚀 support loaded");

Cypress.Commands.add("logout", () => {
  cy.visit("/");
  cy.get("body").then(($body) => {
    if ($body.find("a:contains('Logout')").length) {
      cy.contains("Logout").click();
    }
  });
});

Cypress.Commands.add("login", (username: string, password: string) => {
  cy.visit("/login");
  cy.get('input[id="nameInput"]').type(username);
  cy.get('input[id="passwordInput"]').type(password);
  cy.intercept("POST", "**/users/login").as("loginRequest");
  cy.get('button[type="submit"]').click();
  cy.wait("@loginRequest");
  cy.url().should("include", "/");
});

afterEach(() => {
  cy.task("resetDatabase");
});

// for module scope
export {};

declare global {
  namespace Cypress {
    interface Chainable {
      logout(): Chainable<void>;
      login(username: string, password: string): Chainable<void>;
    }
  }
}


