package demo;

import cn.dev33.satoken.filter.SaServletFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author <a href="mailto:likelovec@gmail.com">韦朕</a>
 * @date 2022/3/9 10:21
 */
@Slf4j
@org.springframework.context.annotation.Configuration
public class Configuration {

    @Bean
    public SaServletFilter saServletFilter() {
        return new SaServletFilter() {
            @Override
            public void doFilter(final ServletRequest request, final ServletResponse response,
                                 final FilterChain chain) throws IOException, ServletException {
                try {
                    super.doFilter(request, response, chain);
                } catch (final Exception e) {
                    throw e;
                } finally {
                    // 清除上下文
                    Context.clear();
                }
            }
        }.addInclude("/**")
                .setBeforeAuth(o -> {
                    // init context
                    final HttpServletRequest request = getHttpServletRequest();
                    Context.request(request);
                    Context.traceId(request.getHeader(Context.TRACER_ID));
                });
    }

    private static HttpServletRequest getHttpServletRequest() {
        try {
            return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        } catch (final Exception e) {
            throw new RuntimeException("can not get Request.");
        }
    }

}