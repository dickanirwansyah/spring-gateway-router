package com.gateway.app.appgatewayrouter.service;

import com.alibaba.fastjson.JSON;
import com.gateway.app.appgatewayrouter.entity.ApiRouter;
import com.gateway.app.appgatewayrouter.model.request.RegisterOrUpdateApiRouterRequest;
import com.gateway.app.appgatewayrouter.repository.ApiRouterRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Slf4j
@Service
public class ApiGatewayRouteService {

    @Autowired
    private ApiRouterRepository apiRouterRepository;

    @Autowired
    private ApiGatewayEventService apiGatewayEventService;

    public Flux<ApiRouter> findApiRouter(){
        log.info("find all api router");
        return apiRouterRepository.findAll();
    }

    public Mono<ApiRouter> findOneApiRouter(Long id){
        log.info("find one api router by id = {} ",id);
        return apiRouterRepository.findById(id);
    }

    public Mono<Void> registerApiRouter(RegisterOrUpdateApiRouterRequest request){
        log.info("register api router = {} ", JSON.toJSONString(request));
        ApiRouter apiRouter = new ApiRouter();
        apiRouter.setUri(request.getUri());
        apiRouter.setMethod(request.getMethod());
        apiRouter.setPath(request.getPath());
        return apiRouterRepository.save(apiRouter)
                .doOnSuccess(onSuccess -> apiGatewayEventService.refreshRoutes())
                .then();
    }

    public Mono<Void> updateApiRouter(RegisterOrUpdateApiRouterRequest request){
        log.info("update api router = {} ",JSON.toJSONString(request));
        if (Objects.isNull(request.getId())){
            log.info("update failed because id is null");
            return Mono.error(new RuntimeException(String.format("Invalid request because null id %d ",request.getId())));
        }
        return validateApiRouteIsExist(request.getId())
                .map(apiRouter -> ApiRouter.builder()
                        .id(request.getId())
                        .path(request.getPath())
                        .uri(request.getUri())
                        .method(request.getMethod())
                        .build())
                .flatMap(apiRouterRepository::save)
                .doOnSuccess(onSuccess -> apiGatewayEventService.refreshRoutes())
                .then();
    }

    private Mono<ApiRouter> validateApiRouteIsExist(Long id){
        log.info("validate route is exists = {} ",id);
        return apiRouterRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException(String.format("Api router with id %d not found", id))));
    }

    public Mono<Void> deleteApiRouter(Long id){
        return validateApiRouteIsExist(id)
                .flatMap(apiRouter -> apiRouterRepository.delete(apiRouter))
                .doOnSuccess(onSuccess -> apiGatewayEventService.refreshRoutes())
                .then();
    }
}
