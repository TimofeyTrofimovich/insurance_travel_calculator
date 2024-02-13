This project is a TRAVEL INSURANCE CALCULATOR.

Technologies used in this project: Spring, Hibernate, Gradle, MySql, H2, Liquibase, JUnit, Mockito, Thymeleaf, Logback and others.

Briefly about the project:

The agreement price is calculated based on many parameters,
such as the duration of agreement, the country, the age of each person,
the classification of agreement (for example only medical insurance or trip cancellation insurance and etc.),
insurance limit, trip cost and etc.

For each insurance classification formula is different.
Now only medical and cancellation risks are implemented.

For example, if you have an insurance which contains medical risk and travel cancellation risk,
your price will be calculated as the sum of prices for each risk: 

Medical risk price = age coefficient *  country coefficient * insurance limit coefficient * number of days.

Trip cancellation risk price = country coefficient * trip cost * age coefficient * number of days.

Age and country coefficient are different for each risk.
They are retrieved from database.


I used MySql database for my application and H2 for tests, but now the whole project is configured to use H2, so that you can run the application and test it by yourself.

Here is the diagram of the database:

![Diagram 1](images/diagram1.png)
![Diagram 2](images/diagram2.png)

You can see SQL script in src/test/resources/data.sql
src/test/resources/schema.sql
and there is also Liquibase script in src/main/resources/db.changelog as my app uses Liquibase.

You can make an agreement for many persons.
Every agreement is saved into a database.
When you enter person data, it will check if such a person already exists in database, and if not, it will save him.
Then it saves all the agreement data, so that next you can get the whole information about the agreement:
all the persons with their data, period of the agreement, country, trip cost, insurance limit level, all the selected risks with their prices and finally the price of the agreement.

We can test the app with such endpoints:

http://localhost:8080/insurance/travel/api/v2/
This controller has a JSON response, so you can test it with Postman




http://localhost:8080/insurance/travel/web/v2
This controller has an HTML response, so you can test it in your browser.

There is no frontend there, so it may not look beautiful.
I used Thymeleaf for this web page.
Looks like this:
![Web page](images/web_form.png)

As you see, we can add more persons and remove it.

And here is the result:
![Result](images/web_answer.png).

All the fields has many validations, so you won't be able to leave some necessary data empty or incorrect.

![Result](images/validation.png).



http://localhost:8080/insurance/travel/api/internal/agreement/{agreement_uuid}
this controller gets the uuid of agreement and if such agreement exists returns of the information about this agreement


