package thesis.demo.load.testing;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import java.time.Duration;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class LoadTester extends Simulation { // 3
    HttpProtocolBuilder httpProtocol = HttpDsl.http
            .baseUrl("http://192.168.0.175:32163")
            .userAgentHeader("Gatling/Performance Test");
    ScenarioBuilder scn = CoreDsl.scenario("Load Test")
            .exec(http("add-data")
                    .post("/add-data")
                    .header("Content-Type", "application/json")
                    .body(StringBody("{ \"value\": \"someValue\" }"))
                    .check(status().is(202))
            );

    public LoadTester() {
        this.setUp(scn.injectOpen(constantUsersPerSec(50).during(Duration.ofSeconds(600))))
                .protocols(httpProtocol);
    }
}