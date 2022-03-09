package demo;

import com.alibaba.ttl.TransmittableThreadLocal;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;

/**
 * context.
 *
 * @author <a href="mailto:likelovec@gmail.com">韦朕</a>
 * @date 2022/3/9 10:23
 */
public class Context {

    public static String TRACER_ID = "tracer_id";
    private static final TransmittableThreadLocal<Context> CONTEXT_THREAD_LOCAL = new TransmittableThreadLocal<Context>() {
        @Override
        protected Context initialValue() {
            return new Context();
        }
    };

    private String traceId;

    private HttpServletRequest request;

    // clear context
    public static void clear() {
        CONTEXT_THREAD_LOCAL.remove();
    }

    // get set
    public static void traceId(String traceId) {
        CONTEXT_THREAD_LOCAL.get().traceId = traceId;
    }

    @Nullable
    public static String traceId() {
        return CONTEXT_THREAD_LOCAL.get().traceId;
    }

    public static void request(HttpServletRequest request) {
        CONTEXT_THREAD_LOCAL.get().request = request;
    }

    @Nullable
    public static HttpServletRequest request() {
        return CONTEXT_THREAD_LOCAL.get().request;
    }
}