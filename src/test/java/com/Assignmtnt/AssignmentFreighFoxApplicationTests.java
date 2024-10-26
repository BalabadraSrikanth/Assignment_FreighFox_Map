package com.Assignmtnt;


import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.Assignment.Service.GoogleMapService;
import com.Assignment.Repository.Map.RouteRepository;
import com.Assignment.Controller.Map.RouteController;
import com.Assignment.Entity.Map.Route;
import com.Assignment.Response.Map.RouteInfo;
import java.util.Optional;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AssignmentFreighFoxApplicationTests {


    @InjectMocks
    private RouteController routeController;

    @Mock
    private GoogleMapService googleMapService;

    @Mock
    private RouteRepository routeRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetRoute_WhenRouteIsCached() {
        // Mock the cached route
        Route cachedRoute = new Route();
        cachedRoute.setFromPincode("123456");
        cachedRoute.setToPincode("654321");
        cachedRoute.setDistance("10 km");
        cachedRoute.setDuration("15 mins");

        when(routeRepository.findByFromPincodeAndToPincode("123456", "654321"))
                .thenReturn(Optional.of(cachedRoute));

        // Call the method
        RouteInfo result = routeController.getRoute("123456", "654321");

        // Assert that the cached route is returned
        assertEquals("123456", result.getFromPincode());
        assertEquals("654321", result.getToPincode());
        assertEquals("10 km", result.getDistance());
        assertEquals("15 mins", result.getDuration());

        // Verify that the GoogleMapService was not called
        verify(googleMapService, times(0)).getCachedRoute(anyString(), anyString());
    }

    @Test
    public void testGetRoute_WhenRouteIsNotCached() {
        // Mock no cached route
        when(routeRepository.findByFromPincodeAndToPincode("123456", "654321"))
                .thenReturn(Optional.empty());

        // Mock the GoogleMapService response
        RouteInfo newRouteInfo = new RouteInfo("123456", "654321", "12 km", "20 mins");
        when(googleMapService.getCachedRoute("123456", "654321")).thenReturn(newRouteInfo);

        // Call the method
        RouteInfo result = routeController.getRoute("123456", "654321");

        // Assert that the new route from Google Maps API is returned
        assertEquals("123456", result.getFromPincode());
        assertEquals("654321", result.getToPincode());
        assertEquals("12 km", result.getDistance());
        assertEquals("20 mins", result.getDuration());

        // Verify that the route was saved in the repository
        verify(routeRepository, times(1)).save(any(Route.class));
    }
}
