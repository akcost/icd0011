package tests;

import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.client.ClientProperties;
import org.hamcrest.core.AnyOf;
import org.junit.Test;
import tests.model.LoginData;
import tests.model.Order;
import tests.model.Result;
import util.RequestResult;

import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.Is.is;


public class Hw10 extends AbstractHw {

    private final String baseUrl = "http://localhost:8080/";

    @Test
    public void versionInfoIsNotProtected() {
        RequestResult result = getRequest("/api/version");

        assertThat(result.getStatusCode(), is(200));
    }

    @Test
    public void canNotAccessProtectedResourceWithoutLoggingIn() {
        RequestResult result = getRequest("api/orders");

        assertThat(result.getStatusCode(), is(authFailureCode()));
    }

    @Test
    public void canAccessProtectedResourceWhenLoggedIn() {
        String token = loginWith("user", "user");

        RequestResult result = getRequest("api/orders", token);

        assertThat(result.getStatusCode(), is(200));
    }

    @Test
    public void userCanAccessOnlyOwnInfo() {
        String token = loginWith("user", "user");

        assertThat(getRequest("api/users", token).getStatusCode(),
                is(authFailureCode()));
        assertThat(getRequest("api/users/jill", token).getStatusCode(),
                is(authFailureCode()));
        assertThat(getRequest("api/users/user", token).getStatusCode(), is(200));
    }

    @Test
    public void adminAddsOrderWithOrderRows() {

        String token = loginWith("admin", "admin");

        String id1 = postOrderAuth("api/orders", token, "A123", "CPU", "Motherboard");
        String id2 = postOrderAuth("api/orders", token, "A456", "Mouse");
        String id3 = postOrderAuth("api/orders", token, "A789", "Monitor", "Printer");

        List<Order> orderList = getListAuth("api/orders", token);

        assertHasIds(orderList, id1, id2, id3);

        assertContainsItems(orderList, id1, "CPU", "Motherboard");
        assertContainsItems(orderList, id2, "Mouse");
        assertContainsItems(orderList, id3, "Monitor", "Printer");
    }

    @Test
    public void userAddsOrderWithOrderRows() {

        String token = loginWith("user", "user");

        String id1 = postOrderAuth("api/orders", token, "C123", "CPU", "Motherboard");
        String id2 = postOrderAuth("api/orders", token, "C456", "Mouse");
        String id3 = postOrderAuth("api/orders", token, "C789", "Monitor", "Printer");

        List<Order> orderList = getListAuth("api/orders", token);

        assertHasIds(orderList, id1, id2, id3);

        assertContainsItems(orderList, id1, "CPU", "Motherboard");
        assertContainsItems(orderList, id2, "Mouse");
        assertContainsItems(orderList, id3, "Monitor", "Printer");
    }

    @Test
    public void adminCanAccessAllUsersInfo() {
        String token = loginWith("admin", "admin");

        assertThat(getRequest("api/users", token).getStatusCode(), is(200));
        assertThat(getRequest("api/users/user", token).getStatusCode(), is(200));
    }

    @Test
    public void adminCanDeleteOrderById() {

        String token = loginWith("admin", "admin");

        Result<Order> result = postOrderAuth("api/orders", new Order("A1"), token);

        String idOfPostedOrder = result.getValue().getId();

        deleteAuth("api/orders/"+idOfPostedOrder, token);

        List<Order> allOrders = getListAuth("api/orders", token);

        List<String> allIds = allOrders.stream()
                .map(Order::getId)
                .toList();

        assertThat(allIds, not(hasItems(idOfPostedOrder)));
    }

    @Test
    public void userCantDeleteOrderById() {
        String token = loginWith("user", "user");

        Result<Order> result = postOrderAuth("api/orders", new Order("C1"), token);

        String idOfPostedOrder = result.getValue().getId();

        deleteAuth("api/orders/"+idOfPostedOrder, token);

        List<Order> allOrders = getListAuth("api/orders", token);

        List<String> allIds = allOrders.stream()
                .map(Order::getId)
                .toList();

        assertThat(allIds, hasItems(idOfPostedOrder));
    }



    private String loginWith(String userName, String password) {
        RequestResult requestResult = postJson("api/login",
                new LoginData(userName, password));

        var token = requestResult.getAuthorization();

        assertThat(token, is(notNullValue()));

        return token;
    }

    private RequestResult postJson(String path, LoginData data) {
        Invocation.Builder request = getTarget()
                .path(path)
                .request(MediaType.APPLICATION_JSON);

        try (Response response = request.post(Entity.entity(data, MediaType.APPLICATION_JSON))) {
            return new RequestResult()
                .withStatusCode(response.getStatus())
                .withAuthorization(response.getHeaderString("Authorization"));
        }
    }

    private RequestResult getRequest(String path) {
        return getRequest(path, null);
    }

    private RequestResult getRequest(String path, String token) {
        WebTarget target = getTarget()
                .property(ClientProperties.FOLLOW_REDIRECTS, Boolean.FALSE)
                .path(path);

        Invocation.Builder request = target.request();

        request.header("Authorization", token);

        Response response = request.get();

        return new RequestResult()
                .withContents(response.readEntity(String.class))
                .withStatusCode(response.getStatus());
    }

    private AnyOf<Integer> authFailureCode() {
        return anyOf(equalTo(401), equalTo(403));
    }

    @Override
    protected String getBaseUrl() {
        return baseUrl;
    }
}
