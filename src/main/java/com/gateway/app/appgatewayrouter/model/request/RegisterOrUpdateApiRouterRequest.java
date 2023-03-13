package com.gateway.app.appgatewayrouter.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterOrUpdateApiRouterRequest {

    private Long id;
    private String path;
    private String method;
    private String uri;
}
