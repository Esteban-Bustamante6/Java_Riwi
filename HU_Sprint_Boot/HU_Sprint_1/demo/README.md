# Eventify - Event and Venue Management System

## Project Description

Eventify is a comprehensive management system developed for the Eventify company, designed to centralize and manage information about events and their venues. The system allows validating all necessary information before starting any event, ensuring that all details are properly configured and verified.

### Main Objectives

- **Centralization**: Unify event and venue management in a single platform
- **Validation**: Ensure all information is correct before event start
- **Organization**: Maintain orderly control of all resources and activities
- **Efficiency**: Optimize event planning and execution processes

## Technology Stack

- **Java 21**: Latest LTS version of Java
- **Spring Boot 3.5.14**: Main framework for backend
- **Spring Web**: For REST API creation
- **Spring Validation**: For input data validation
- **SpringDoc OpenAPI 2.5.0**: For automatic API documentation
- **Lombok**: To reduce boilerplate code
- **Maven**: Dependency management and build system

## Prerequisites

- **Java 21** or higher installed
- **Maven 3.6** or higher
- **Git** to clone the repository

## Installation and Setup

### 1. Clone the Repository

```bash
git clone [YOUR_REPOSITORY_URL]
cd demo
```

### 2. Build the Project

```bash
# Using Maven Wrapper (recommended)
./mvnw clean install

# Or using local Maven
mvn clean install
```

### 3. Run the Application

```bash
# Using Maven Wrapper
./mvnw spring-boot:run

# Or using local Maven
mvn spring-boot:run
```

### 4. Verify Installation

The application will start at `http://localhost:8080`

## API Documentation

Once the application is running, you can access the interactive API documentation at:

**[http://localhost:8080/docs](http://localhost:8080/docs)**

This interface allows you to:
- Explore all available endpoints
- Test the API directly from your browser
- View data models and schemas
- Understand required parameters for each operation

## Project Architecture

### Package Structure

```
src/main/java/com/example/demo/
├── Config/          # Configurations (Swagger, etc.)
├── Controllers/     # REST Controllers (EventController, VenuesController)
├── Models/          # Data Models (Event, Venues)
├── Repositories/    # Data Repositories
├── Services/        # Business Logic
└── DemoApplication.java # Main application class
```

### Data Models

#### Event
- **id**: Unique identifier of the event
- **name**: Event name (required)
- **description**: Detailed description (maximum 500 characters)

#### Venues
- **id**: Unique identifier of the venue
- **name**: Venue name (required)
- **address**: Detailed address (maximum 500 characters)

## Main Endpoints

The API exposes the following main endpoints:

### Events
- `GET /api/events` - Get all events
- `POST /api/events` - Create a new event
- `GET /api/events/{id}` - Get a specific event
- `PUT /api/events/{id}` - Update an event
- `DELETE /api/events/{id}` - Delete an event

### Venues
- `GET /api/venues` - Get all venues
- `POST /api/venues` - Create a new venue
- `GET /api/venues/{id}` - Get a specific venue
- `PUT /api/venues/{id}` - Update a venue
- `DELETE /api/venues/{id}` - Delete a venue

## Validations

The system includes automatic validations:

- **Events**: Name cannot be empty, description cannot exceed 500 characters
- **Venues**: Name cannot be empty, address cannot exceed 500 characters

## Testing

To run unit tests:

```bash
# Using Maven Wrapper
./mvnw test

# Or using local Maven
mvn test
```



## How to Use the API

1. **Start the application** as described in the installation section
2. **Open your browser** and navigate to `http://localhost:8080/docs`
3. **Explore the endpoints** available in the Swagger UI interface
4. **Test operations** directly from the interface:
   - Use the "Try it out" buttons to test the endpoints
   - Enter example data according to the provided schemas
   - Execute operations and observe the responses

## Usage Examples

### Create an Event

```json
{
  "name": "Java Conference 2024",
  "description": "Annual event on the latest trends in Java and Spring Boot development"
}
```

### Create a Venue

```json
{
  "name": "Convention Center",
  "address": "Main Street #123, City, Country"
}
```

## Important Notes

- Make sure you have Java 21 properly installed
- The application uses volatile memory, data will be lost on restart
- For production, consider configuring a persistent database
- API documentation is always available at `/docs` when the application is running

## Support

If you encounter any problems or have questions, please:
1. Check the documentation at `http://localhost:8080/docs`
2. Verify application logs for error messages
3. Ensure you meet all prerequisites

---

**Developed for Eventify © 2024**
