package com.gateway.app.appgatewayrouter.repository;

import com.gateway.app.appgatewayrouter.entity.ApiRouter;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiRouterRepository extends ReactiveCrudRepository<ApiRouter, Long> {
}
