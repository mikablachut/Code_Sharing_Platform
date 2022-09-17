# Code Sharing Platform
* [Description](#description)
* [Technologies](#technologies)
* [Setup](#setup)
* [REST API Endpoints](#rest-api-endpoints)
    * [Add Code Endpoint](#add-code-endpoint)
    * [Get Code Endpoint](#get-code-endpoint)
    * [Get List Of Latest Code Endpoint](#get-list-of-latest-code-endpoint)
* [Web Interface](#web-interface)
    * [Add Code Request](#add-code-request)
    * [Get Code Request](#get-code-request)
    * [Get List Of Latest Code Request](#get-list-of-latest-code-request)

## Description

<details>
<summary>Click here to see general information about the <b>Project</b>!</summary>

Web application that allows you to share code. WEB interface and REST API has been implemented. The user can add code snippets, specifying the time for which it will be available in the database and the number of its possible views. If one of the above restrictions is met, the code is automatically removed from the databse. The user can also view the added code after entering its UUID or can view the last 10 added code snippets that were not restricted with a limit of the number of views or viewing time.

The idea for project comes from [JetBrains Academy](https://www.jetbrains.com/academy/) Java Beckend Developer track.
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

Clone the repository and enter its folder:

```
git clone https://github.com/mikablachut/Code_Sharing_Platform.git
cd Code_Sharing_Platform
```

Now you can run the app using Gradle:

```
gradle bootRun
```

Application can also be started using ```java -jar``` command but for that, we need to generate jar file.  
Steps for this scenario:

```
gradle build
java -jar .\build\libs\code_sharing_platform-2.7.2.jar
```

## REST API Endpoints

When you start the application a H2 database containing initial tables will be automaticly created. You can use [Postman](https://www.postman.com) or any similar 
program for testing existing endpoints.

### Add Code Endpoint

Endpoint accepts a JSON object with the following fields:
- 'code' field contains code snippet
- 'time' field contains the time (in seconds) during which the snippet is accessible.
- 'views' field contains a number of views allowed for this snippet.

Negative values and 0 corresponds to the absence of the restriction.Endpoint returns JSON output with a single field id. ID is the unique UUID number of the snippet which helps you access it via the GET  endpoint: ```/api/code/UUID```.

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

Endpoint returns a code snippet with a specified ID as a JSON object (where {id} is the ID of a code snippet). The code snippet is not accessible if any restriction is triggered. In such case the endpoint will return '404 Not Found' error message. Same error message will appear when no snippet with such a ID.

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

## Web Interface

Enter the below address ```http://localhost:8889``` to open the application.

### Add Code Request

Request:
```http://localhost:8889/code/new```

Request should return a web page that contains:
 - 'Text area' where you can paste a code snippet;
 - 'Time restriction' where you can enter the time restriction.
 - 'Views restriction' where you can enter the views restriction.

<details>
<summary><b>Sample response</b></summary>
   
![GetCodeNew](https://user-images.githubusercontent.com/98345304/189479667-bc354633-000c-4e1f-b88c-b3c81e653d6f.jpg)
   
</details>

### Get Code Request

Request:
```http://localhost:8889/code/2187c46e-03ba-4b3a-828b-963466ea348c```

Request returns web page (by code snippet's ID) that contains uploaded code snippet. The code snippet is not accessible if any of the restriction is triggered.

<details>
<summary><b>Sample response</b></summary>

![GetCode](https://user-images.githubusercontent.com/98345304/189479478-cc8e8990-62fa-4938-8d68-96167edf488f.jpg)

</details>

### Get List Of Latest Code Request

 Request:
```http://localhost:8889/code/latest```

Request returns web page that contains 10 most recently uploaded code snippets sorted from the newest to the oldest. Request doesn't return any restricted snippets.
 
<details>
<summary><b>Sample response</b></summary>
   
![GetCodeLatest](https://user-images.githubusercontent.com/98345304/189479642-0e93574d-e823-4cf1-9fa9-57c47a370fa6.jpg)
   
</details>

 
