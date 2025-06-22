package br.com.ff.arch_beaver.common.utils.log;

import br.com.ff.arch_beaver.common.utils.value.ResultSetUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.json.JSONObject;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class LogsUtil {

    public static void debug(Class<?> type, HttpServletRequest request, Object object) {
        var jsonObject = createJson(request, object, null);
        var logger = LoggerFactory.getLogger(type);
        var json = ResultSetUtil.toString(jsonObject);
        logger.debug(json);
    }

    public static void debug(Class<?> type, String message) {
        var jsonObject = createJson(message);
        var logger = LoggerFactory.getLogger(type);
        var json = ResultSetUtil.toString(jsonObject);
        logger.debug(json);
    }

    public static JSONObject error(HttpServletRequest request, Object object, String message) {
        var jsonObject = createJson(request, object, message);
        var logger = LoggerFactory.getLogger(retrieveControllerClass(request));
        var json = ResultSetUtil.toString(jsonObject);
        logger.error(json);
        return jsonObject;
    }

    public static JSONObject error(HttpServletRequest request, Object object) {
        var jsonObject = createJson(request, object, null);
        var logger = LoggerFactory.getLogger(retrieveControllerClass(request));
        var json = ResultSetUtil.toString(jsonObject);
        logger.error(json);
        return jsonObject;
    }

    public static void error(Class<?> type, String message) {
        var jsonObject = createJson(message);
        var logger = LoggerFactory.getLogger(type);
        var json = ResultSetUtil.toString(jsonObject);
        logger.error(json);
    }

    private static JSONObject createJson(String message) {
        var jsonObject = new JSONObject();
        jsonObject.put("message", message);
        return jsonObject;
    }

    private static JSONObject createJson(HttpServletRequest request, Object object, String message) {
        var jsonObject = new JSONObject();
        jsonObject.put("method", request.getMethod());
        jsonObject.put("path", request.getRequestURI());
        jsonObject.put("return", new JSONObject(object));

        if (message != null) jsonObject.put("message", message);

        return jsonObject;
    }

    private static Class<?> retrieveControllerClass(HttpServletRequest request) {
        HandlerMethod method = (HandlerMethod) request.getAttribute(HandlerMapping.BEST_MATCHING_HANDLER_ATTRIBUTE);
        return method != null ? method.getBeanType() : LogsUtil.class;
    }
}
