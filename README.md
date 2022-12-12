# XML-File-Uploader-Service

### To create an image from the Dockerfile, you have to run:

```` $> docker-compose -f docker-compose-local.yaml up ```` (spins the db up for the tests) <br>
```` $> mvn package ```` (creates the .jar file) <br>
```` $> docker build --tag=file-uploader:latest . ```` (creates the docker image)

--------------------------------------------------------------------------------------------------

## How to run
### 1st option:
```` $> docker-compose up ````
##### It will spin up the postgresql database and the file-uploader within the same network.
##### The service will wait for the postgres db to spin up and then connect to: jdbc:postgresql://database:5432/file_uploader 
##### After that it will be available under: localhost:8080

### 2nd option:
- ```` $> docker-compose -f docker-compose-local.yaml up ```` (spins the db up at localhost) <br>
- change to ````spring.datasource.url=jdbc:postgresql://localhost:5432/file_uploader```` 
in the application.properties <br>
- run the application over your IDE

--------------------------------------------------------------------------------------------------

## Service endpoints overview:

### Upload the xml file:
```` POST http://localhost:8080/files ```` with file in the body

### Get the file data:
```` GET /files/pages/<pagenumber>?filterBy=<filterName>&filter=<value>&asc=<trueOrFalse>&sortBy=<sortName> ````
#### Example:
```` GET http://localhost:8080/files/pages/1?filterBy=screenDpi&filter=160&asc=false&sortBy=screenHeight ````

Possible sorting: "name", "uploadedAt", "newspaperName", "screenWidth", "screenHeight", "screenDpi"
<br>
Possible filters: "name", "newspaperName", "screenWidth", "screenHeight", "screenDpi"

--------------------------------------------------------------------------------------------------

### How to run the tests:
#### For running the db on localhost, (i.e. to run the tests) you need to run before:
```` $> docker-compose -f docker-compose-local.yaml up ```` <br>
```` $> mvn test ````