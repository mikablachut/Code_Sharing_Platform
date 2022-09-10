# Code Sharing Platform
* [Description](#description)
* [Technologies](#technologies)
* [Setup](#setup)
* [REST API Endpoints](#rest-api-endpoints)
    * [Add Code Endpoint](#add-code-endpoint)
    * [Get Code Endpoint](#get-code-endpoint)
    * [Get List Of Latest Code Endpoint](#get-list-of-latest-code-endpoint)
* [Web Interface Requests](#web-interface-requests)
    * [Add Code Request](#add-code-request)
    * [Get Code Request](#get-code-request)
    * [Get List Of Latest Code Request](#get-list-of-latest-code-request)

## Description

<details>
<summary>Click here to see general information about <b>Project</b>!</summary>

Web application that allows to share code. WEB interface and REST API has been implemented. The user can add a code snippets, specifying the time for which it will be 
available in the database and the number of its possible views. If one above-mentioned restrictions is reached, the code is automatically removed from the databse. The 
user can also view the added code after entering its UUID or can view the last 10 added code snippets that were not restricted with a limit of the number of views or
viewing time.

The idea for project cames from Java Beckend Developer track in [JetBrains Academy](https://www.jetbrains.com/academy/).
</details>

## Technologies

<ul>
  <li>Java 17</li>
  <li>Spring Boot</li>
  <li>Spring Boot Web MVC</li>
  <li>Spring Data</li>
  <li>Hibernate</li>
  <li>H2</li>
  <li>HTML, CSS</li>
  <li>JavaScript</li>
  <li>FreeMarker</li>
  <li>Gradle</li>
  <li>JUnit</li>
  <li>REST API</li>
  <li>Project Lombok</li>
</ul>

## Setup

Make sure you have [git](https://git-scm.com/) installed. The application uses H2 as the database by default.

### Gradle

<b>Java 17 is required for this step.</b>

Clone repository and enter its folder:

```
git clone https://github.com/dominikablachut/Code_Sharing_Platform.git
cd Code_Sharing_Platform
```

Now you can run the app using Gradle:

```
gradle bootRun
```

We can also run the application using traditional way using ```java -jar``` command but for that, we need to generate jar of out spring boot application.  
Let see those steps for Gradle:

```
gradle build
java -jar .\build\libs\code_sharing_platform-2.7.2.jar
```

## REST API Endpoints

When you start the application a H2 database containing initial tables will be automaticly created. You can use [Postman](https://www.postman.com) or any similar 
program for testing existing endpoints.

### Add Code Endpoint

Endpoint takes a JSON object with a field code and two other fields:
- time field contains the time (in seconds) during which the snippet is accessible.
- views field contains a number of views allowed for this snippet.
Negative values and 0 corresponds to the absence of the restriction.Endpoint return JSON with a single field id. ID is the unique UUID number of the snippet that helps you
access it via the endpoint GET /api/code/UUID.

POST method

```POST /api/code/new```

Request body:
```json
{
    "code": "Secret code",
    "time": 5000,
    "views": 5
}
```

<details>
<summary><b>Sample response</b></summary>
<p>
  
```json 
{ 
   "id" : "2187c46e-03ba-4b3a-828b-963466ea348c" 
}
```
 
</p>
</details>

### Get Code Endpoint

Endpoint  returns a code snippet with a specified id as a JSON object (where {id} is the id of a code snippet). The code snippet is not accessible if one of the 
restrictions is triggered. Endpoint will return 404 Not Found in this case and all the cases when no snippet with such a UUID was found.

GET Method

```GET /api/code/2187c46e-03ba-4b3a-828b-963466ea348c```

Path Variable:
```String id```

<details>
<summary><b>Sample response</b></summary>
<p>

```json 
{
    "code": "Secret code",
    "date": "2020/05/05 12:01:45",
    "time": 4995,
    "views": 4
}
```

</p>
</details>

### Get List Of Latest Code Endpoint

Endpoint return a JSON array with 10 most recently uploaded code snippets sorted from the newest to the oldest. Endpoint dosen't return any restricted snippets.

GET method

```GET /api/code/latest```

<details>
<summary><b>Sample response</b></summary>
<p>

```json 
[
    {
        "code": "public static void ...",
        "date": "2020/05/05 12:00:43",
        "time": 0,
        "views": 0
    },
    {
        "code": "class Code { ...",
        "date": "2020/05/05 11:59:12",
        "time": 0,
        "views": 0
    }
]
```

</p>
</details>

## Web Interface Requests

When you start the application a H2 database containing initial tables will be automaticly created. You can use your own browser and enter the address
```http://localhost:8080``` for testing the existing requests.

### Add Code Request

Request should return HTML that contains:
- Tags:
  - <textarea id="code_snippet"> ... </textarea> where you can paste a code snippet;
  - <input id="time_restriction" type="text"/> should contain the time restriction.
  - <input id="views_restriction" type="text"/> should contain the views restriction.
- Title Create;
- Button <button id="send_snippet" type="submit" onclick="send()">Submit</button>.

Request:
```http://localhost:8080/code/new```

<details>
<summary><b>Sample response</b></summary>
<p>

</p>
</details>

### Get Code Request

Request return HTML that contains uploaded code snippet with a specified id(UUID). The code snippet is not accessible if one of the restrictions is triggered.

Request:
```http://localhost:8080/code/2187c46e-03ba-4b3a-828b-963466ea348c```

<details>
<summary><b>Sample response</b></summary>
<p>

</p>
</details>

### Get List Of Latest Code Request

 Request return HTML that contains 10 most recently uploaded code snippets sorted from the newest to the oldest. Request doesn't return any restricted snippets.
 
 Request:
```http://localhost:8080/code/latest```

<details>
<summary><b>Sample response</b></summary>
<p>

</p>
</details>

 
