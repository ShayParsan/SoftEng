import { defineConfig } from "cypress";
import createBundler from "@bahmutov/cypress-esbuild-preprocessor";
import { addCucumberPreprocessorPlugin } from "@badeball/cypress-cucumber-preprocessor";
import { createEsbuildPlugin } from "@badeball/cypress-cucumber-preprocessor/esbuild";
import { exec } from "child_process";
import dotenv from "dotenv";

// load the Allure plugin writer
import allureWriter from "@shelex/cypress-allure-plugin/writer";

dotenv.config({ path: ".env.e2e" });
console.log("Loaded NEXT_PUBLIC_API_URL:", process.env.NEXT_PUBLIC_API_URL);
console.log("Loaded CYPRESS_BASE_URL:", process.env.CYPRESS_BASE_URL);

async function setupNodeEvents(
    on: Cypress.PluginEvents,
    config: Cypress.PluginConfigOptions,
): Promise<Cypress.PluginConfigOptions> {
  // cucumber + esbuild
  await addCucumberPreprocessorPlugin(on, config);
  on("file:preprocessor", createBundler({ plugins: [createEsbuildPlugin(config)] }));

  // allure plugin
  allureWriter(on, config);

  // your custom tasks
  on("task", {
    resetDatabase() {
      const apiUrl = process.env.NEXT_PUBLIC_API_URL;
      return new Promise((resolve, reject) => {
        exec(`curl -X POST ${apiUrl}/test-utils/reset-database`, (err, stdout, stderr) => {
          if (err) {
            console.error("Error with DB:", stderr);
            return reject(err);
          }
          console.log("DB is fine", stdout);
          resolve(null);
        });
      });
    },
  });

  return config;
}

export default defineConfig({
  e2e: {
    baseUrl: process.env.CYPRESS_BASE_URL,
    specPattern: "**/*.feature",
    setupNodeEvents,
    supportFile: "cypress/support/e2e.ts",
  },
  env: {
    // turn on the plugin & set results folder
    allure: true,
    allureResultsPath: "allure-results"
  }
});