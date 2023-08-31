

---

# Microservice

This document outlines the flow of the microservices architecture, detailing how user interactions are processed and fraud detection is performed. The architecture consists of various components that work together to manage user interactions and detect fraudulent behavior.

## Flow Steps:

1. **User Interaction:**
   - Users engage in interactions such as liking a post or visiting a page within the application.

2. **User Service:**
   - The User Service records these interactions in its MySQL database.
   - Interaction data includes the type of interaction (like, visit) and the timestamp.

3. **Event Publication:**
   - The User Service publishes an event related to the interaction to the event broker.
   - The event includes user information, interaction type, and timestamp.

4. **Event Broker:**
   - The event broker receives and stores events from the User Service.

5. **Fraud Detection Service Subscription:**
   - The Fraud Detection Service subscribes to events from the event broker, particularly those related to user interactions.

6. **Fraud Detection Analysis:**
   - The Fraud Detection Service extracts user information from the received interaction event.
   - It accesses the User Service's MySQL database to retrieve the user's interaction history.

7. **Interaction Count Calculation:**
   - The Fraud Detection Service calculates the total number of interactions (likes and visits) for the user using the interaction history.

8. **Fraud Detection Decision:**
   - If the total interaction count reaches the threshold (e.g., 100 interactions), the Fraud Detection Service marks the user as fraudulent.
   - The Fraud Detection Service updates its own NoSQL database to reflect the fraudulent status of the user.

9. **User API and Fraud API:**
   - External clients interact with the system through the User API and Fraud API.
   - The User API manages user profiles, interactions, and updates.
   - The Fraud API handles queries and management of flagged users for fraud.

10. **API Gateway:**
    - The API Gateway serves as the entry point for external clients.
    - It routes incoming requests to relevant microservices (User Service API, Fraud API).
    - The API Gateway handles authentication, rate limiting, and request/response transformations.

11. **Service Discovery:**
    - The Eureka Discovery service assists in locating and communicating with different microservices.
    
12. **Load Balancing:**
    - Load balancers distribute incoming requests evenly across instances of microservices for optimized resource utilization.

13. **Docker Containerization:**
    - Each microservice is containerized using Docker for consistency and portability.

## Components:

**Load Balancer**

**API Gateway**

**Eureka Discovery**

**User Service**

**Fraud Detection Service**

**User API (RESTful)**

**Fraud API (RESTful)**

**User Service MySQL Database**

**Fraud Service NoSQL Database**

**Event Broker**

**Docker for each service**

---

## Fault Tolerance:


**Asynchronous Communication:**
Using asynchronous communication patterns, like message queues, can decouple services and prevent failures in one service from directly affecting others.

**Redundancy:** 
Redundancy is key to fault tolerance. Multiple instances of each microservice can be deployed, either on the same server or across different servers or even data centers. If one instance fails, traffic can be rerouted to others, ensuring service availability.

**Data Redundancy:**
Replicating data from the master to multiple replicas ensures redundancy. If the master server fails, one of the replicas can be promoted to take its place, minimizing downtime and data loss.

**Retry and Timeout Strategies:**
Just like in any distributed system, implementing retries and timeouts is crucial. Microservices can fail due to network issues, temporary resource unavailability, or other transient problems. Implementing intelligent retry mechanisms with appropriate timeouts can enhance fault tolerance.

**Service Discovery and Load Balancing:**
Services need to be discovered and accessed dynamically in a microservices architecture. Service discovery mechanisms, combined with load balancers, help distribute traffic evenly across instances, aiding fault tolerance by avoiding overloading a single instance.

---

