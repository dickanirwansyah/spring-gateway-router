package com.gateway.app.appgatewayrouter.controller;

import com.gateway.app.appgatewayrouter.model.request.RegisterOrUpdateApiRouterRequest;
import com.gateway.app.appgatewayrouter.model.response.ApiRouterResponse;
import com.gateway.app.appgatewayrouter.service.ApiGatewayRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@RequestMapping(value = "/api/v1/apigateway")
@RestController
public class ApiGatewayController {

    @Autowired
    private ApiGatewayRouteService apiGatewayRouteService;

    @GetMapping(value = "/list")
    public Mono<List<ApiRouterResponse>> findAllApiRoute(){
        return apiGatewayRouteService.findApiRouter()
                .map(data -> ApiRouterResponse.builder()
                        .id(data.getId())
                        .path(data.getPath())
                        .uri(data.getUri())
                        .method(data.getMethod())
                        .build())
                .collectList()
                .subscribeOn(Schedulers.boundedElastic());
    }

    @PostMapping(
            value = "/create",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<?> registerApiRoutes(@RequestBody RegisterOrUpdateApiRouterRequest request){
        return apiGatewayRouteService.registerApiRouter(request)
                .subscribeOn(Schedulers.boundedElastic());
    }

    @PutMapping(
            value = "/update/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<?> updateApiRoutes(@RequestBody RegisterOrUpdateApiRouterRequest request,
                                   @PathVariable("id")Long id){
        request.setId(id);
        return apiGatewayRouteService.updateApiRouter(request)
                .subscribeOn(Schedulers.boundedElastic());
    }

    @GetMapping(value = "/find/{id}")
    public Mono<ApiRouterResponse> getById(@PathVariable("id")Long id){
        return apiGatewayRouteService.findOneApiRouter(id)
                .map(data -> ApiRouterResponse.builder()
                        .id(data.getId())
                        .path(data.getPath())
                        .uri(data.getUri())
                        .method(data.getMethod())
                        .build())
                .subscribeOn(Schedulers.boundedElastic());
    }
}
