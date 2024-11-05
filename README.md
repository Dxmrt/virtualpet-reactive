# 🐾 Virtual Pet Reactive

Welcome to the Virtual Pet Reactive Project! This fully reactive application, built with Spring WebFlux, R2DBC for MySQL, and MongoDB, allows users to create and manage virtual pets. Users can perform fun actions like feeding, dancing, and bathing their pets while tracking game sessions.

## 🌟 Key Features

    🔐 User Authentication: Secure login and registration with JWT-based authentication.
    🛡️ Role-based Access Control: Define access based on roles (e.g., ROLE_USER, ROLE_ADMIN).
    🐱 Pet Management: Create, update, and delete virtual pets with a variety of interaction options.
    🎩 Accessory Management: Personalize your pet with fun accessories.
    📅 Game Sessions: Track each pet’s sessions and health stats over time.
    📖 API Documentation: Swagger-enabled API docs for easy testing, accessible at http://localhost:8080/webjars/swagger-ui/index.html

---    
    
## 📁 Project Structure

**config:** Contains configurations for application security, password encoding, and Swagger API documentation to ensure smooth integration and security for all endpoints.

**controller:**

    VirtualPetController: Handles all endpoints related to pet interactions and game session management, allowing users to create, interact with, and manage virtual pets.
    AuthController: Manages authentication processes, including user registration and login, using JWT for secure session handling.

**model:** Defines the core entities, such as User, Pet, Accessory, and GameSession, which represent the main components of the virtual pet system, including pet types and accessory options.

**service:**

    VirtualPetService: Contains the business logic for managing virtual pets, game sessions, and accessories. It connects the controllers to the data layer, handling operations like creating game sessions, executing pet actions (feeding, dancing, bathing), and managing accessory assignments.

**excepcions:** The GlobalExceptionHandler ensures consistent error handling and meaningful responses, improving user experience by managing and logging application errors.

**repository:** Interfaces for both R2DBC and MongoDB databases, enabling non-blocking, reactive data persistence and retrieval for users, pets, accessories, and game sessions.


---
    

## 🚀 Getting Started

1️⃣ **Clone the Repository**

bash

git clone <repo-url>
cd virtualpet-reactive-master

2️⃣ **Set Up Databases**

    Configure MySQL and MongoDB connection details in application.properties.

3️⃣ **Run the Application**

bash

./mvnw spring-boot:run

4️⃣ **Explore the API**

    Open Swagger UI in your browser to explore and test the endpoints.


---    


## 📬 Using the API with Postman

To simplify testing, a pre-configured Postman collection is provided, containing essential endpoints for authentication, pet management, and admin functionalities.
How to Use the Postman Collection

    Download the Collection: Save the provided Virtual Pet API.postman_collection.json file locally.
    
    Import into Postman:
    
        Open Postman and click Import.
        Select the downloaded file. The collection will appear with organized endpoints.
        
    Set Up Environment Variables:
    
        Create a new environment in Postman with:
        
            - base_url: The base URL of your API, e.g., http://localhost:8080.
            - jwt_token: Store the JWT token from login to authenticate requests.

After logging in, copy the JWT token from the login response and paste it into jwt_token to access protected endpoints.

---


## 📄 Database Setup

To set up the MySQL database for the Virtual Pet API, use the following SQL script:

```sql

-- Create the database
CREATE DATABASE IF NOT EXISTS virtualm;

-- Use the database
USE virtualm;

-- Create the users table
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role ENUM('ROLE_USER', 'ROLE_ADMIN') NOT NULL DEFAULT 'ROLE_USER',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

---


## 🌱 Future Enhancements

    🎨 Frontend Development: Planned integration with a React or Vue interface for a more engaging UI.
    ⚠️ Enhanced Exception Handling: Additional custom exceptions for clearer feedback.

---

## 🌐 URL for Frontend Access

Once integrated, the frontend will be accessible at http://localhost:8080/index.html.
