# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project

`SampleTest` — a mixed Selenium/Cucumber/JUnit/TestNG practice/demo project. Maven `groupId=Demo`, `artifactId=SampleTest`. Part of a larger multi-project Eclipse workspace (see `../CLAUDE.md` for the workspace-level overview); no dependency on sibling projects.

## Commands

```
mvn clean test     # compiles and runs the Cucumber suite (real headless-Chrome Selenium scenarios) via the JUnit runner
mvn verify          # also runs maven-cucumber-reporting, generating an HTML report in target/
                     # from target/jsonReports/cucumber-report.json
```

Java target is 24 (`maven.compiler.source/target` in `pom.xml`). Requires a local Chrome install — Selenium 4's built-in Selenium Manager resolves the matching chromedriver automatically (no hardcoded driver path).

## Architecture

Flow: `.feature` file → JUnit runner → step definitions → headless Chrome via Selenium.

- `src/test/java/features/login.feature` — real scenarios against the public practice site `https://practicetestautomation.com/practice-test-login/`: one successful login, plus a Scenario Outline covering invalid username / invalid password.
- `src/test/java/cucumber/optionss/TestRunner.java` — `@RunWith(Cucumber.class)` runner; `@CucumberOptions` wires `features` dir, `glue={"stepDefinitions"}`, and `plugin` (Cucumber JSON output + the Extent adapter, see below). Note the package is `cucumber.optionss` (double "s").
- `src/test/java/stepDefinitions/StepDefinitions.java` — `@Before`/`@After` hooks start/quit a headless `ChromeDriver` per scenario; step methods drive the login form and assert success/error text with JUnit `Assert`.
- `src/test/java/Demo/SampleTest/Practice.java`, `Inter.java` — standalone scratch/practice classes, unrelated to the Cucumber flow.
- `src/main/java/Demo/SampleTest/App.java` — default Maven-archetype placeholder class.

**Reporting:** `tech.grasshopper:extentreports-cucumber7-adapter` is wired in as a Cucumber plugin and generates an ExtentReports Spark HTML report at `target/ExtentReport/SparkReport.html` on every `mvn test` run. Output path is configured in `src/test/resources/extent.properties`. The adapter's own JSON reporter is intentionally left disabled there — under Java 24's module system, Gson (used internally by that JSON writer) fails to reflectively access `Throwable` fields; the Spark HTML report and the separate Cucumber `json:` plugin output (`target/jsonReports/cucumber-report.json`, consumed by `maven-cucumber-reporting` on `mvn verify`) are unaffected.

`pom.xml` also pulls in `jackson-databind` and `poi`/`poi-ooxml`, but no source file in this project currently uses them (data-driven/excel/JSON examples live in the sibling `RestAPI` project instead).

This project is lower-stakes/exploratory relative to the workspace's other projects — expect less structure and fewer conventions to preserve.
