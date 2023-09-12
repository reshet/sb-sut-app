package sut;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

public class AppSutTailLatenciesSimulation extends Simulation {

    HttpProtocolBuilder httpProtocol =
        http.baseUrl("http://localhost:3080")
            .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
            .acceptLanguageHeader("en-US,en;q=0.5")
            .acceptEncodingHeader("gzip, deflate")
            .userAgentHeader(
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0"
            );

    ChainBuilder read = exec(http("Read Spring Rest Controller").get("/read"));
    ScenarioBuilder users = scenario("Users Read").exec(read);

    {
        setUp(
            users.injectOpen(
                atOnceUsers(1),
                nothingFor(2),
                atOnceUsers(4),
                rampUsers(50).during(10),
                constantUsersPerSec(20.0).during(20),
                constantUsersPerSec(30.0).during(30)
            )
        ).protocols(httpProtocol);
    }
}
