# Yoga App

This project consists of a back-end application built with [Spring Boot](https://spring.io/projects/spring-boot) and a front-end application developed with [Angular](https://angular.io/).

## Getting Started

### 1. Database Installation

#### MySQL 5

1. **Install MySQL**: Follow the instructions on the [MySQL website](https://dev.mysql.com/downloads/installer/) to install MySQL.

2. **Create the Database Schema**:
   - Locate the SQL script for schema creation at `resources/sql/script.sql`.
   - Open your terminal or command prompt and execute the script:
     ```bash
     mysql -u your_username -p your_password < resources/sql/script.sql
     ```
   - **Default Admin Account for the App**:
     - **Login:** yoga@studio.com
     - **Password:** test!1234

### 2. Application Installation

#### Back-end Installation

1. **Navigate to the Back-end Directory**:
   ```bash
   cd back
   ```
2. **Install Dependencies**:

   - Make sure you have Maven installed.

   ```bash
   mvn clean install
   ```

3. **Configure Environment Variables**:

   - Make sure you have Maven installed.

   ```bash
    DATABASE_URL=jdbc:mysql://localhost:3306/your_database
    DATABASE_USERNAME=your_username
    DATABASE_PASSWORD=your_password
   ```

4. **Launch Back-end**:
   - Make sure you have Maven installed.
   ```bash
    mvn spring-boot:run
   ```

#### Front-end Installation

1. **Navigate to the Front-end Directory**:
   ```bash
   cd front
   ```
2. **Install Dependencies**:

   - Ensure you have Node.js and npm installed.

   ```bash
   npm install

   ```

3. **Launch Front-end**:

   - Make sure you have Maven installed.

   ```bash
   npm run start

   ```

### 3. Running the Application

#### Back-end

Ensure that the Spring Boot application is running by executing:

```bash
    mvn spring-boot:run
```

in the `back` directory.

#### Front-end

Start the Angular application by running by
executing:

```bash
    npm run start
```

in the `front` directory.

Visit `http://localhost:4200` in your browser to access the front-end application.

### 4. Running Tests

#### Back-end Tests

1. **Navigate to the Back-end Directory**:

   ```bash
   cd back

   ```

2. **Run Tests**:

   ```bash
   mvn clean test
   ```

#### Front-end Tests

1. **Navigate to the Front-end Directory**:

   ```bash
   cd front

   ```

2. **Run Unit Tests**:

   ```bash
   npm run test
   ```

3. **Run End-to-End (E2E) Tests**:
   ```bash
   npm run e2e
   ```

### 5. Generating Coverage Reports

#### Back-end Coverage Report

1. **Navigate to the Back-end Directory**:

   ```bash
   cd back

   ```

2. **Generate Coverage Report**:

   ```bash
   mvn clean test

   ```

3. **View Report**:
   Open the coverage report at:

   ```bash
   back/target/site/jacoco/index.html

   ```

#### Front-end Coverage Reports

1. **Navigate to the Front-end Directory**:

   ```bash
   cd front

   ```

2. **Generate Unit Test Coverage Report**:

   ```bash
   npm run test

   ```

3. **Generate End-to-End (E2E) Test Coverage Report**:

   ```bash
   npm run e2e:coverage

   ```

4. **View Reports**:

   - Unit Test Coverage Report

   ```bash
   front/coverage/jest/lcov-report/index.html

   ```

   - E2E Test Coverage Report

   ```bash
   front/coverage/lcov-report/index.html

   ```
