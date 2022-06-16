#System of Payments and Orders  

###*Goal*  
> We received a monolith and separated payment and ordering services into micro services  

###*Description*  
>A payment micro service integrated with order micro service, using mysql database, spring cloud eureka for discovery and logging, spring cloud gateway for port management and load balance

##Technologies  

* **Spring Cloud Eureka** for discovery and logging
* **Spring Cloud Gateway** port management and load balance
* **Spring Feign** for the integration of services
* **Spring Validation** for entities
* **Flyway** for database version control
* **Swagger** for documentation 

## Swagger

> [Order-ms](https://localhost:8082/order-ms/swagger-ui.html)  
> [Payments-ms](https://localhost:8082/payments-ms/swagger-ui.html)
