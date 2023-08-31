# Meet5
User Interaction

Users perform interactions such as liking a post or visiting a page within the application.
User Service:

The User Service records these interactions in its MySQL database, updating the user's profile with interaction details.
Interaction data includes the type of interaction (like, visit) and the timestamp.
Event Publication:

Upon recording an interaction, the User Service publishes an event related to the interaction to the event broker.
This event contains information about the user, the type of interaction, and the timestamp.
Event Broker:

The event broker receives and stores events from the User Service.
Fraud Detection Service Subscription:

The Fraud Detection Service subscribes to events from the event broker, particularly those related to user interactions.
Fraud Detection Analysis:

When the Fraud Detection Service receives an interaction event, it extracts user information from the event.
The Fraud Detection Service accesses the User Service's MySQL database to retrieve the user's interaction history.
Interaction Count Calculation:

Using the interaction history, the Fraud Detection Service calculates the total number of interactions (likes and visits) for the user.
Fraud Detection Decision:

If the total interaction count reaches the threshold (e.g., 100 interactions), the Fraud Detection Service marks the user as fraudulent.
The Fraud Detection Service updates its own NoSQL database to reflect the fraudulent status of the user.
User API and Fraud API:

External clients, such as web or mobile applications, interact with the system through the User API and Fraud API.
The User API provides functionalities related to user profiles, interactions, and updates.
The Fraud API offers functionalities for querying and managing flagged users for fraud.
API Gateway:

The API Gateway acts as the entry point for external clients.
It routes incoming requests to appropriate microservices (User Service API, Fraud API).
The API Gateway handles authentication, rate limiting, and request/response transformations.
Service Discovery:

The Eureka Discovery service assists in locating and communicating with different microservices.
Load Balancing:

Load balancers distribute incoming requests evenly across instances of microservices for optimized resource utilization.
Docker Containerization:

Each microservice is containerized using Docker for consistency and portability.
