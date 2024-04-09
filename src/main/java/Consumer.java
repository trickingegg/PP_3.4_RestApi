import org.json.JSONObject;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class Consumer {
    private static final RestTemplate restTemplate = new RestTemplate();

    public static void main(String[] args) {
        String c = getCookie();

        saveUser(c);
        editUser(c);
        deleteUser(c);
    }

    private static void deleteUser(String cookie) {
        String url = "http://94.198.50.185:7081/api/users/3";


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Cookie", cookie);


        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.DELETE,
                requestEntity,
                String.class);

        HttpStatus statusCode = (HttpStatus) responseEntity.getStatusCode();
        String responseBody = responseEntity.getBody();

        System.out.println("Status Code: " + statusCode);
        System.out.println("Response Body: " + responseBody);
    }

    public static String getCookie() {
        String url = "http://94.198.50.185:7081/api/users";
        ResponseEntity<List<User>> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<User>>() {
                });
        return responseEntity.getHeaders().getFirst(HttpHeaders.SET_COOKIE).replace(" JSESSIONID=", "").split(";")[0];
    }

    public static String saveUser(String cookie) {
        String url = "http://94.198.50.185:7081/api/users";

        User user = new User(3L, "James", "Brown", (byte) 23);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Cookie", cookie);

        HttpEntity<User> requestEntity = new HttpEntity<>(user, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                String.class);

        HttpStatus statusCode = (HttpStatus) responseEntity.getStatusCode();
        String responseBody = responseEntity.getBody();

        System.out.println("Status Code: " + statusCode);
        System.out.println("Response Body: " + responseBody);
        ResponseEntity<List<User>> responseEntity2 = restTemplate.exchange(
                "http://94.198.50.185:7081/api/users",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<User>>() {
                });

        List<User> response = responseEntity2.getBody();
        System.out.println(response);
        return  responseEntity2.getHeaders().getFirst(HttpHeaders.SET_COOKIE);

    }

    private static void editUser(String cookie) {
        String url = "http://94.198.50.185:7081/api/users";

        User user = new User(3L, "Thomas", "Shelby", (byte) 23);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Cookie", cookie);

        System.out.println(headers);

        HttpEntity<User> requestEntity = new HttpEntity<>(user, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.PUT,
                requestEntity,
                String.class);

        System.out.println(requestEntity);
        HttpStatus statusCode = (HttpStatus) responseEntity.getStatusCode();
        String responseBody = responseEntity.getBody();

        System.out.println("Status Code: " + statusCode);
        System.out.println("Response Body: " + responseBody);
    }
}
