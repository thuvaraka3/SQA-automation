# Sauce Demo Automation Testing Project
BAT/IT/2324/F/032
> **This is my automation testing assignment.**
> I built this Selenium WebDriver framework to test the **Sauce Demo** e-commerce website
> (https://www.saucedemo.com). It uses **Java + TestNG** with the **Page Object Model (POM)** design pattern.

---

## Table of Contents
1. [What I Used](#what-i-used)
2. [Project Structure](#project-structure)
3. [What I Tested](#what-i-tested)
4. [How to Run the Tests](#how-to-run-the-tests)
5. [Reports and Logs](#reports-and-logs)
6. [Configuration](#configuration)
7. [What I Learned](#what-i-learned)
8. [Things I Struggled With](#things-i-struggled-with)
9. [Things to Note](#things-to-note)

---

## What I Used

| Technology | Purpose | Why I Chose It |
|------------|---------|----------------|
| **Java** | Programming language | We learned this in class |
| **Selenium WebDriver** | Browser automation | Industry standard for web testing |
| **TestNG** | Test framework | Better than JUnit - has groups, priorities, listeners |
| **Maven** | Build/dependency tool | Automatically downloads libraries (no manual JAR files!) |
| **ExtentReports** | HTML test reports | Makes beautiful dark-themed reports |
| **Log4j2** | Logging | Helps debug test failures |
| **IntelliJ IDEA** | IDE | Free community edition works great |

---

## Project Structure

I organized my code following the **Page Object Model (POM)** pattern.
Each web page has its own class with locators and methods.

```
SauceDemoAutomation/
|
+-- src/test/java
|   +-- base/                          # Foundation classes
|   |   +-- BaseTest.java              # Setup & teardown for ALL tests
|   |   +-- BasePage.java              # Common methods for ALL page objects
|   |   +-- DriverFactory.java         # Creates & manages browser instances
|   |
|   +-- pages/                         # Page Object classes (one per page)
|   |   +-- LoginPage.java             # Login page actions
|   |   +-- ProductsPage.java          # Products/Inventory page actions
|   |   +-- CartPage.java              # Shopping cart page actions
|   |   +-- CheckoutPage.java          # Checkout form page
|   |   +-- OverviewPage.java          # Order summary/review page
|   |   +-- CompletePage.java          # Order confirmation page
|   |
|   +-- tests/                         # Actual test cases
|   |   +-- LoginTests.java            # 5 test cases for login
|   |   +-- ProductTests.java          # 5 test cases for products
|   |   +-- CartTests.java             # 2 test cases for cart
|   |   +-- CheckoutTests.java         # 3 test cases for checkout
|   |
|   +-- utilities/                     # Helper classes
|   |   +-- ConfigReader.java          # Reads config.properties
|   |   +-- ExtentManager.java         # Manages ExtentReports singleton
|   |
|   +-- listeners/
|       +-- TestListener.java          # TestNG listener for reporting + logging
|
+-- src/test/resources                 # Configuration files
|   +-- config.properties              # Environment settings (URL, browser, etc.)
|   +-- log4j2.xml                     # Logging settings
|
+-- reports/                           # HTML reports (generated after test run)
+-- logs/                              # Log files (generated during test run)
+-- testng.xml                         # TestNG suite configuration
+-- pom.xml                            # Maven build file (dependencies)
+-- TESTCASES.md                       # Detailed test case documentation
+-- README.md                          # This file
```

---

## What I Tested

I wrote **15 test cases** covering the main features of the website:

### Login Tests (5 tests)
| # | Test | What It Checks |
|---|------|----------------|
| TC-01 | Valid login | Can we log in with correct username & password? |
| TC-02 | Invalid password | Does error show when password is wrong? |
| TC-03 | Invalid username | Does error show when username is wrong? |
| TC-04 | Empty username | Does error say "Username is required"? |
| TC-05 | Empty password | Does error say "Password is required"? |

### Product Tests (5 tests)
| # | Test | What It Checks |
|---|------|----------------|
| TC-06 | Page loads | Is the products page displayed with items? |
| TC-07 | Sort A-Z | Does sorting by name alphabetically work? |
| TC-08 | Sort price low-high | Does sorting by price ascending work? |
| TC-09 | Add one item | Does cart badge show "1" after adding one? |
| TC-10 | Add multiple items | Does cart badge show "3" after adding three? |

### Cart Tests (2 tests)
| # | Test | What It Checks |
|---|------|----------------|
| TC-11 | Remove from cart | Does removing decrease the cart count? |
| TC-12 | Badge count sequence | Does badge update: 0 -> 1 -> 2 -> 1 correctly? |

### Checkout Tests (3 tests)
| # | Test | What It Checks |
|---|------|----------------|
| TC-13 | Complete checkout | Can we go through the whole flow successfully? |
| TC-14 | Missing fields | Does error say "First Name is required"? |
| TC-15 | Order confirmation | Does thank-you message appear with dispatch info? |

---

## How to Run the Tests

### Prerequisites
- **Java 21** installed (`java -version` to check)
- **Maven** installed (`mvn -version` to check)
- **Chrome browser** installed

### Step 1: Fix ChromeDriver Path
Open `src/test/java/base/DriverFactory.java` and update the path to your chromedriver.exe:
```java
System.setProperty("webdriver.chrome.driver", "C:\\path\\to\\your\\chromedriver.exe");
```
> **Note:** You can download ChromeDriver from https://chromedriver.chromium.org/

### Step 2: Run All Tests
```bash
mvn test
```

### Step 3: Run Specific Groups
```bash
mvn test -Dgroups=smoke          # Only smoke tests (TC-01, TC-06, TC-13, TC-15)
mvn test -Dgroups=login          # Only login tests
mvn test -Dgroups=products       # Only product tests
mvn test -Dgroups=cart           # Only cart tests
mvn test -Dgroups=checkout       # Only checkout tests
```

### Or Run from IntelliJ
- **File → Settings → Build, Execution, Deployment → Build Tools → Maven → Runner**
- Check **"Delegate IDE build/run actions to Maven"** → Apply → OK
- Then right-click on `testng.xml` → Run 'All Tests'

> **Note:** If using JDK 26, IntelliJ's internal compiler has a bug with `com.sun.tools.javac.code.TypeTag`.
> Delegating to Maven or switching to JDK 21 fixes this.

---

## Reports and Logs

After running the tests, check these files:

| File | Location | Description |
|------|----------|-------------|
| **Extent Report** | `reports/extent-report.html` | Fancy HTML report (open in any browser) |
| **TestNG Report** | `target/surefire-reports/index.html` | Built-in TestNG report |
| **Log File** | `logs/automation.log` | Detailed execution logs |

---

## Configuration

Edit `src/test/resources/config.properties` to change settings:

```properties
base.url=https://www.saucedemo.com   # Website URL
browser=chrome                        # Browser: chrome or firefox
username=standard_user                # Demo account username
password=secret_sauce                 # Demo account password
timeout=10                            # Wait timeout in seconds
```

---



## Things I Struggled With

1. **ChromeDriver setup** - Had to download the right version that matches my Chrome browser
2. **Flaky tests** - Elements not loading fast enough, fixed with `WebDriverWait`
3. **XPath for cart removal** - Had to learn ancestor axes to find the right button
4. **Maven dependencies** - Figuring out which dependency versions work together
5. **ExtentReports listener** - Understanding how TestNG callbacks work
6. **Static vs instance** - When to use `static` (ConfigReader, DriverFactory) vs not
7. **Config file path** - The path is relative to the project root, not the class location

---

## Things to Note

- Tests need **internet connection** (Sauce Demo is a live website)
- Update your **ChromeDriver path** in DriverFactory.java before running
- Tests run **one at a time** (no parallel execution set up yet)
- The **standard_user** account is a test account provided by Sauce Demo
- All **15 tests passed** in my last execution (9 July 2026)
- Built with **Java 21** (JDK 21) and **Maven**

---

*Made with by a student learning automation testing*
*Course: Software Testing & Automation | June 2026*
