The Asset Management Solution
======================================

This is a Spring Boot RESTful application which can serve the following Account operations:

 * Making a transfer between two accounts

How to run
----------

Build the project using gradle. From project root:

```
./gradlew clean build
```

Example request:

```
curl -i -X PUT \
   -H "Content-Type:application/json" \
   -d \
'{
  "accountFromId":"Id-1",
  "accountToId":"Id-2",
  "amount": "1.55"
}' \
 'http://localhost:8080/accounts/transfer'
```

Assumptions
-----------

* You cannot make a transfer to the same account ID. In other words, you cannot send money to yourself.
* When making a transfer the amount is in pounds / dollars not in pence / cents. For example, 100.45.
* DB connectivity is not created and instead use concurrentHashMap in repository class to store account object.
* We have tested synchronzation with some limited requests not with any tool.

Further improvements
--------------------

* In RESTful services, it is not unusual to see the same request coming twice. In a production system we should
handle those hiccups. One idea would be to perform a 2-step transfer. In the first step we create a transaction ID
and then we request a transfer given this transaction ID. Once a transfer is made the transaction ID cannot be used
anymore.
* For thread safe and avoiding deadlocks we can use Lock/Executor framework.
* We can use JMeter or any other tool for further synchronization related testing.
