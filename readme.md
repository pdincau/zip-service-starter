# ZipService

## How to run unit tests

You can run the unit tests with:

```sh
mvn clean test
```

## How to run integration tests

You can run the integration tests with:

```sh
mvn test-compile failsafe:integration-test
```

## How to package

You can create a fat jar by running:

```
mvn package
```

## How to run the application

Once you have a fat jar, you can run the application:

```sh
java -jar target/zip-service-jar-with-dependencies.jar
```

By default the application will listen on port 8080, you can override the default port by setting the `$http.server.port` value:


```sh
java -jar -Dhttp.server.port=8081 target/zip-service-jar-with-dependencies.jar
```

## How to run acceptance tests

When the application is running, you can run the acceptance tests with:

```sh
mvn -Dtest="*UAT" test
```

By default the acceptance tests will be executed against an application that is listening on port 8080 of localhost. You can override this as follows:

```sh
mvn -Dhost=adifferenthost -Dport=8081 -Dtest="*UAT" test
```

In the host flag you don't have to prefix the url with `http://`.

## Running the code coverage

You can generate reports for the code coverage as follows:


```sh
mvn jacoco:report
```


