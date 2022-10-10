### Rewards Application?

An application to calculate reward points for every user. You can start the application by importing it ion any preferred IDE and then starting the application class `RewardsApplication`

Application uses : H2 Database, Spring, Java 17, Actuator and Swagger

Swagger link: `http://localhost:8080/swagger-ui.html`

Health Endpoint:  `http://localhost:8080/actuator/health`

APIs for fetching the customer specific rewards :
```
curl -X 'GET' \
  'http://localhost:8080/api/customer/rewards?customerId=1' \
  -H 'accept: */*'
  
{
  "customerId": 1,
  "rewards": {
    "2022-Sep": 240,
    "2022-Aug": 70,
    "2022-Jul": 240
  },
  "totalRewards": 550
}
```

APIs for fetching rewards for all transaction :
```
curl -X 'GET' \
  'http://localhost:8080/api/customer/rewards/all' \
  -H 'accept: */*'
  
Response:

[
  {
    "customerId": 1,
    "rewards": {
      "2022-Sep": 240,
      "2022-Aug": 70,
      "2022-Jul": 240
    },
    "totalRewards": 550
  },
  {
    "customerId": 100,
    "rewards": {
      "2022-Oct": 1850
    },
    "totalRewards": 1850
  }
]
```

Create a new transaction:

```
curl -X 'POST' \
  'http://localhost:8080/api/customer/transaction' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
    "customerId":100,
    "productName":"ABC",
    "amount":1000.0
}'

```