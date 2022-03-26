# java-task
[![Java](https://img.shields.io/badge/java-17-brightgreen.svg)](https://docs.oracle.com/en/java/javase/17/docs/api/index.html)
[![Spring](https://img.shields.io/badge/spring-2.6.5-brightgreen.svg)](https://docs.spring.io/spring-boot/docs/2.6.5/reference/html/)
[![Gson](https://img.shields.io/badge/gson-2.8.9-brightgreen.svg)](https://javadoc.io/doc/com.google.code.gson/gson/2.8.9/com.google.gson/module-summary.html)
[![JUnit](https://img.shields.io/badge/junit-5.7.0-brightgreen.svg)](https://junit.org/junit5/docs/current/user-guide/)
[![rest-assured](https://img.shields.io/badge/rest--assured-5.0.0-brightgreen.svg)](https://github.com/rest-assured/rest-assured/wiki/ReleaseNotes50)

<!-- TABLE OF CONTENTS -->
## Table of Contents
* [Getting Started](#getting-started)
   * [Installation](#installation)
* [Development](#development)
* [API docs](#api-docs)

<!-- GETTING STARTED -->
## Getting Started
To get a local copy up and running follow these simple example steps.

<!-- INSTALLATION -->
### Installation
1. Clone the repo
``
git clone https://github.com/bbronek/java-task.git``
2. Install all dependencies using gradle

<!-- DEVELOPMENT -->
## Development
Path to SpringBootApplication: `vl-internship-master/src/main/java/com/virtuslab/internship/Main.java`

## API docs
----
  Returns a receipt json for passed a shopping cart information in json format.

* **URL**

  /api/v1/basket

* **Method:**

  `POST`

* **Request Samples** <br>
    Content Type: 
    `application/json`
  
  ```
  {
   "products":[
      {
         "name":"Milk",
         "type":"DAIRY",
         "price":2.7
      },
      {
         "name":"Cheese",
         "type":"DAIRY",
         "price":20.5
      },
      {
         "name":"Cheese",
         "type":"DAIRY",
         "price":20.5
      },
      {
         "name":"Banana",
         "type":"FRUITS",
         "price":4.4
      },
      {
         "name":"Bread",
         "type":"GRAINS",
         "price":5
      },
      {
         "name":"Bread",
         "type":"GRAINS",
         "price":5
      },
      {
         "name":"Bread",
         "type":"GRAINS",
         "price":5
      }
   ]
  }
  
 * **Response Samples** <br>
     Status code: `201` <br>
      Content Type: 
     `application/json`
   ```
    {
     "entries":[
        {
           "product":{
              "name":"Banana",
              "type":"FRUITS",
              "price":4.4
           },
           "quantity":1,
           "totalPrice":4.4
        },
        {
           "product":{
              "name":"Bread",
              "type":"GRAINS",
              "price":5
           },
           "quantity":3,
           "totalPrice":15
        },
        {
           "product":{
              "name":"Milk",
              "type":"DAIRY",
              "price":2.7
           },
           "quantity":1,
           "totalPrice":2.7
        },
        {
           "product":{
              "name":"Cheese",
              "type":"DAIRY",
              "price":20.5
           },
           "quantity":2,
           "totalPrice":41.0
        }
     ],
     "discounts":[
        "FifteenPercentDiscount",
        "TenPercentDiscount"
     ],
     "totalPrice":48.2715
   }
   
