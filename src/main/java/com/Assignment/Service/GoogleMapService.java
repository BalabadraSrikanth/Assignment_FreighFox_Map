package com.Assignment.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.Assignment.Response.Map.GoogleRouteResponse;
import com.Assignment.Response.Map.RouteInfo;

@Service
public class GoogleMapService {
	

    

    @Value("${google.maps.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    // Constructor with RestTemplate injection
    public GoogleMapService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Cacheable("routes") // Enable caching for this method
    public RouteInfo getCachedRoute(String fromPincode, String toPincode) {
        return getRoute(fromPincode, toPincode); // This calls the actual method to get the route
    }
   

    // Actual method to fetch route from Google Maps API
    public RouteInfo getRoute(String fromPincode, String toPincode) {
        // Google Maps API URL
        String url = "https://maps.googleapis.com/maps/api/directions/json?origin=" + fromPincode + 
                     "&destination=" + toPincode + "&key=" + apiKey;

        // Calling the Google Maps API via RestTemplate
        ResponseEntity<GoogleRouteResponse> response = restTemplate.getForEntity(url, GoogleRouteResponse.class);

        // Checking if response is successful and not null
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            GoogleRouteResponse routeResponse = response.getBody();

            // Extract distance and duration from the API response
            String distance = routeResponse.getRoutes().get(0).getLegs().get(0).getDistance().getText();
            String duration = routeResponse.getRoutes().get(0).getLegs().get(0).getDuration().getText();

            // Return a new RouteInfo object with the route details
            
            return new RouteInfo(fromPincode, toPincode, distance, duration);
        } else {
            throw new RuntimeException("Failed to fetch route from Google Maps API");
        }
        
    }
}
