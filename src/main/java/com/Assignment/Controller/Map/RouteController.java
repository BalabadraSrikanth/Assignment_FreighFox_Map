package com.Assignment.Controller.Map;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Assignment.Service.GoogleMapService;
import com.Assignment.Entity.Map.Route;
import com.Assignment.Repository.Map.RouteRepository;
import com.Assignment.Response.Map.RouteInfo;

@RestController
@RequestMapping("/api/routes")
public class RouteController {

    private final GoogleMapService googleMapService;
    private final RouteRepository routeRepository;

    @Autowired
    public RouteController(GoogleMapService googleMapService, RouteRepository routeRepository) {
        this.googleMapService = googleMapService;
        this.routeRepository = routeRepository;
    }

    @GetMapping
    public RouteInfo getRoute(@RequestParam String fromPincode, @RequestParam String toPincode) {
        Optional<Route> cachedRoute = routeRepository.findByFromPincodeAndToPincode(fromPincode, toPincode);

        // Check if the route is cached
        if (cachedRoute.isPresent()) {
            // Return the cached route mapped to RouteInfo
            return new RouteInfo(cachedRoute.get());
        }

        // Fetch the route from Google Maps API if not cached
        RouteInfo routeInfo = googleMapService.getCachedRoute(fromPincode, toPincode);

        // Save the new route in the database
        Route route = new Route();
        route.setFromPincode(fromPincode);
        route.setToPincode(toPincode);
        route.setDistance(routeInfo.getDistance());
        route.setDuration(routeInfo.getDuration());
        routeRepository.save(route);

        return routeInfo;
    }
}
