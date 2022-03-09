package demo;

import cn.hutool.core.util.IdUtil;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * gateway configuration.
 *
 * @author <a href="mailto:likelovec@gmail.com">韦朕</a>
 * @date 2022/3/9 10:16
 */
@org.springframework.context.annotation.Configuration
public class Configuration {

    String TRACER_ID = "tracer_id";

    @Component
    public class GlobalFilterImpl implements GlobalFilter {

        @Override
        public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
            ServerHttpRequest request = exchange.getRequest();
            // append tracer id
            String tracerId = request.getHeaders().getFirst(TRACER_ID);
            if (tracerId == null) {
                tracerId = IdUtil.fastSimpleUUID();
            }
            request.mutate().header(TRACER_ID, tracerId).build();
            return chain.filter(exchange);
        }

    }
}