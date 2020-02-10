package com.skillsoft.percipio.api.test;

import com.skillsoft.percipio.api.testdata.SuiteData;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.*;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assume.assumeTrue;

/*
 * This is a common parent for all tests
 */
@RunWith(SuiteTestRunner.class)
public class BaseTest extends BaseData {
    private static final Logger log = LoggerFactory.getLogger(BaseTest.class);
    @Rule
    public TestName name = new TestName();

    @BeforeClass
    public static void resetRestAssured() {
        RestAssured.reset();
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    public static RequestSpecification bearer(String jwt) {
        return newRequestSpecBuilder().addHeader("Authorization", String.format("bearer %s", jwt)).build();
    }

    public static RequestSpecification serviceAccount(String orgId, String serviceAccountId) {
        return newRequestSpecBuilder()
            .addHeader("X-AUTHOR-ORG-ID", orgId)
            .addHeader("X-AUTHOR-SERVICE-ACCOUNTID", serviceAccountId)
            .build();
    }

    public static RequestSpecification serviceName(String serviceName) {
        return newRequestSpecBuilder()
            .addHeader("X-Author-Servicename", serviceName)
            .build();
    }

    public static RequestSpecification serviceName() {
        return serviceName("PINTS_INTEGRATION_TESTS");
    }

    /**
     * Just for adding configuration to ALL request-spec builders we make from convenience methods.
     *
     * @return A new RequestSpecBuilder
     */
    protected static RequestSpecBuilder newRequestSpecBuilder() {
        return new RequestSpecBuilder();
    }

    public static ResponseSpecification hasErrorCount(int count) {
        return new ResponseSpecBuilder()
            .expectContentType(ContentType.JSON)
            .expectBody("errors.code", hasSize(1))
            .build();
    }

    public static ResponseSpecification hasMessage(String message) {
        return new ResponseSpecBuilder()
            .expectContentType(ContentType.JSON)
            .expectBody("message", hasItem(message))
            .build();
    }

    public static ResponseSpecification hasError(String resource, String code) {
        return new ResponseSpecBuilder()
            .expectContentType(ContentType.JSON)
            .expectBody("errors.resource", hasItem(resource))
            .expectBody("errors.code", hasItem(code))
            .build();
    }

    @Before
    public void runBeforeMethod() throws NoSuchMethodException {



    }

}
