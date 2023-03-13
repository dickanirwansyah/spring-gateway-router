package com.gateway.app.appgatewayrouter.service;

import com.gateway.app.appgatewayrouter.entity.ApiRouter;
import lombok.AllArgsConstructor;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.BooleanSpec;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;

@AllArgsConstructor
public class ApiGatewayPathRouteLocatorService implements RouteLocator {

    private final ApiGatewayRouteService apiGatewayRouteService;

    private final RouteLocatorBuilder routeLocatorBuilder;

    public Flux<Route> getRoutes(){
        RouteLocatorBuilder.Builder routesBuilder = routeLocatorBuilder.routes();
        return apiGatewayRouteService.findApiRouter()
                .map(apiRouter -> routesBuilder.route(String.valueOf(apiRouter.getId()),
                        predicateSpec -> setBuildPredicableSpec(apiRouter, predicateSpec)))
                .collectList()
                .flatMapMany(mayBuilders -> routesBuilder.build().getRoutes());
    }

    private Buildable<Route> setBuildPredicableSpec(ApiRouter apiRouter, PredicateSpec predicateSpec){
        BooleanSpec booleanSpec = predicateSpec.path(apiRouter.getPath());
        if (!StringUtils.isEmpty(apiRouter.getMethod())){
            booleanSpec.and().method(apiRouter.getMethod());
        }
        return booleanSpec.uri(apiRouter.getUri());
    }
}
