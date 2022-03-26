package pl.estrix.warehouse.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import pl.estrix.warehouse.util.SessionManager;

public class RestService {

    private static RestService instance = null;

    private Client client = Client.create();

    private final Gson gson = new GsonBuilder().create();

    private static String address = "http://localhost:8081";
    private static String port = "8081";

    public static RestService getInstance() {
        if (instance == null) {
            instance = new RestService();
        }

        if (SessionManager.customsProperties.containsKey("serverAddress")) {
            address = SessionManager.customsProperties.get("serverAddress") + ":" + SessionManager.customsProperties.get("serverPort");
        } else {
            address = SessionManager.SERVICE_URL + ":" + SessionManager.SERVICE_PORT;
        }

        return instance;
    }

    public Boolean ping(){
        client.setReadTimeout(500);
        try {
            WebResource webResource = client.resource(
                    address +"/api/healthcheck");
            ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);
            if (response.getStatus() != 200) {
                return false;
            }
        } catch (Exception e ){
            return false;
        }
        return true;
    }



//    public GetCollectorDetailsDto getSession(String number){
//        client.setReadTimeout(500);
//        try {
//            WebResource webResource = client.resource(
//                    address +
//                            "/user/session?number=" + number);
//            ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);
//            if (response.getStatus() != 200) {
//                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
//            }
//
//            return gson.fromJson(response.getEntity(String.class), GetCollectorDetailsDto.class);
//        } catch (ClientHandlerException e ){
//            return new GetCollectorDetailsDto(new CollectorDto("0","000"),408 /*Request Timeout*/);
//        }
//    }
}
