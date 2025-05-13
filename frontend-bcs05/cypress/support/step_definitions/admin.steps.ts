import { Given, When, Then } from "@badeball/cypress-cucumber-preprocessor";
import { DataTable } from "@badeball/cypress-cucumber-preprocessor";
import dayjs from "dayjs";

function parseRelativeDate(input: string): string {
    const parts = input.trim().split(" ");
    const keyword = parts[0];
    const time = parts[1] ?? "00:00";
  
    // формат, который ожидает react-datepicker
    const format = "MMMM D, YYYY";
  
    if (keyword === "today") {
      return `${dayjs().format(format)} ${time}`;
    }
  
    if (keyword === "tomorrow") {
      return `${dayjs().add(1, "day").format(format)} ${time}`;
    }
  
    // fallback
    return input;
  }

    function resolveDate(input: string): string {
      const [keyword, time] = input.trim().split(" ");

      if (keyword === "today") {
        return `${dayjs().format("DD-MM-YYYY")} ${time}`;
      }

      if (keyword === "tomorrow") {
        return `${dayjs().add(1, "day").format("DD-MM-YYYY")} ${time}`;
      }

      return input;
    }


When("I click on lecturer with name {string}", (name: string) => {
    cy.contains(`${name}`).click();
    //cy.get(`tr[role='button'][data-cy=schedule-${id}]`).click();
  });

  Then("I see a list of courses i can create schedule for", () => {
    cy.contains("Courses taught").should("be.visible");
  });

  When("I click the Schedule button for course with name {string}", (number: string) => {
    cy.contains('tr', number) 
      .within(() => {
        cy.contains('button', 'Schedule').click();
      });
  });

  Then("I see create schedule form page", () => {
    cy.contains("Create new schedule").should("be.visible");
  });

  When("I fill in schedule from {string} to {string}", (start: string, end: string) => {
    const startDate = parseRelativeDate(start);
    const endDate = parseRelativeDate(end);

    cy.contains('label', 'Start Date:')
      .parent()
      .find('input')
      .click()
      .type(`${startDate}{enter}`, { force: true });
  
    cy.contains('label', 'End Date:')
      .parent()
      .find('input')
      .click()
      .type(`${endDate}{enter}`, { force: true });
  });

  When("I click the button {string}", (number: string) => {
    cy.contains('button', `${number}`).click();
  });

  Then("I see message schedule created successfully", () => {
    cy.contains("Schedule created successfully").should("be.visible");
  });

  Then("I see error schedule message", () => {
    cy.contains("Start date must be before end date.").should("be.visible");
  });

  Then("I should see the following schedules:", (dataTable: DataTable) => {
    const expectedSchedules = dataTable.hashes();
  
    // обработка динамических дат
    const resolvedSchedules = expectedSchedules.map((schedule) => ({
      ...schedule,
      Start: resolveDate(schedule["Start"]),
      End: resolveDate(schedule["End"]),
    }));
  
    cy.get("table tbody tr").should("have.length", resolvedSchedules.length);
  
    resolvedSchedules.forEach((schedule, index) => {
      cy.get("table tbody tr").eq(index).within(() => {
        cy.contains(schedule["Course"]);
        cy.contains(schedule["Start"]);
        cy.contains(schedule["End"]);
        cy.contains(schedule["Lecturer"]);
        cy.contains(schedule["Enrolled Students"]);
      });
    });
  });

