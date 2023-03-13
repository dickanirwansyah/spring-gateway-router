package com.gateway.app.appgatewayrouter.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table("api_router")
public class ApiRouter {

    @Id
    private Long id;
    private String path;
    private String method;
    private String uri;
}
