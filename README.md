# Guesthouse Booking Service

Part of the entire guesthouse booking system
- **booking-service** handles rooms, bookings and frontend.
- **customer-service** all customer data

## How the services talk
Through REST, neither service accesses the others database.
- When a booking is created, booking-service asks customer-service if the customer exists
- When a customer is deleted, customer-service asks booking-service if that customer has active bookings
- If the other service is down the request fails with a clear message instead of crashing

A booking only stores customer id, not the customer.

## Starting the system
Both repos need to be exist next to each other, then from the **booking-service**-repo 
``` docker compose up --build ``` 

This starts four containers, both services and a database each.
- booking-service: localhost:8081
- customer-service: localhost:8080

## REST API
- GET /api/bookings/customer/{id}/has-active = does this customer have bookings
- POST /api/bookings = create a booking

Body for POST:

``` 
{
"checkIn": "2026-09-13,
"checkOut": "2027-09-16",
"customerId": 1,
"roomId": 1 
} 
```

409 = room already booked those dates
404 = customer or room does not exist

# Frontend
Thymeleaf from monolith project
- http://localhost:8081/bookings
- http://localhost:8081/rooms

# Tests
./mvnw test 

## Merge conflict we resolved
PR #5 feature/logging-booking and pr #6 feature/logging-room-service were both branched from the same main and both changed logging lines in app.propeties.
Almost the same lines were written but PR #5 also contained spring.jpa.open-in-view=false

PR#5 was merged first so PR #6 got a conflict. It was resolved by keeping PR#5. Rest of PR#6 with RoomServiceImpl logging was kept.
CI ran again and was green. 

## Deployed service
App: https://guesthouse-booking-service-production.up.railway.app/
Healthcheck: https://guesthouse-booking-service-production.up.railway.app/actuator/health

## Workflow
Main is protected, 1 approval needed from collaborator. CI tests must pass and nobody including admins can push directly to main. 
## From branch to prod
1. Create a branch from the latest main
2. Commit, push and open a pull request
3. GitHub actions autoruns ./mvnw test on the pull request
4. Another collaborator reviews the code and leaves comments and either request change or approves
5. When CI is green and PR is approved, it is merged to main
6. Railway deplos main, it first calls /actuator/health and only routes traffic to new version if it returns 200. If health check fails, previous version keeps running.

## Observability
Logging INFO for normal events (booking/room saved or deleted), WARN for problems like customer not found or room already booked, ERROR for failures like customer-service unreachable.
Spring Boot actuator at /actuator/health. Only the health endpoint is exposed with no details except UP or DOWN.
Logs available in Railway.
