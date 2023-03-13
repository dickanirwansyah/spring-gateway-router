package com.gateway.app.appgatewayrouter.configuration;

import com.gateway.app.appgatewayrouter.service.ApiGatewayPathRouteLocatorService;
import com.gateway.app.appgatewayrouter.service.ApiGatewayRouteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class ApiGatewayConfiguration {

    @Bean
    public RouteLocator routeLocator(ApiGatewayRouteService apiGatewayRouteService,
                                     RouteLocatorBuilder locatorBuilder){
        log.info("initialize route locator bean..");
        return new ApiGatewayPathRouteLocatorService(apiGatewayRouteService, locatorBuilder);
    }
}
