package com.hwq.wudi.config.filter;

import com.alibaba.fastjson.JSON;
import com.hwq.wudi.util.HttpContextUtils;
import com.hwq.wudi.util.RespEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * @Auther: haowenqiang
 * @Description:
 */
@Slf4j
@WebFilter(filterName = "signFilter",urlPatterns = "/*")
public class SignFilter implements Filter {

    private final String TIME_KEY = "timestemp";

    private final String SIGN_KEY = "sign";

    private final String SALT = "QIANGwuDi";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        String url = request.getRequestURL().toString();
        if (checkurl(url)) {
            String time = request.getParameter(TIME_KEY);
            String sign = request.getParameter(SIGN_KEY);
            //校验时间
            if (StringUtils.isBlank(time) || (System.currentTimeMillis() - Long.valueOf(time)) / 1000 > 30) {
                writeResult(servletResponse, RespEntity.fail("1001", "请求超时", null));
                return;
            }
            //校验签名
            if (StringUtils.isBlank(sign) || !verifySign(request, sign)) {
                writeResult(servletResponse, RespEntity.fail("1002", "签名验证失败", null));
                return;
            }
        }
        chain.doFilter(request, servletResponse);
    }

    private boolean verifySign(HttpServletRequest request, String sign) {
        Enumeration<String> parameterNames = request.getParameterNames();
        Map<String, Object> paramMap = new HashMap<>();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            paramMap.put(paramName, request.getParameter(paramName));
        }
        return sign.equals(createSing(paramMap));
    }

    private String createSing(Map<String, Object> paramMap) {
        Set<String> keySet = paramMap.keySet();
        //参数排序
        Object[] objects = keySet.toArray();
        Arrays.sort(objects);
        StringBuilder sb = new StringBuilder();
        for (Object key : objects) {
            if(SIGN_KEY.equals(key)) continue;
            sb.append(key).append("=").append(paramMap.get(key)).append("&");
        }
        sb.append(SALT);
        log.info("拼接后的:==={}", sb.toString());
        return DigestUtils.md5DigestAsHex(sb.toString().getBytes());
    }


    private boolean checkurl(String url) {
        ResourceBundle bundle = ResourceBundle.getBundle("verify_sign_url");
        Enumeration<String> keys = bundle.getKeys();
        while (keys.hasMoreElements()) {
            if (url.contains(keys.nextElement())) {
                return true;
            }
        }
        return false;
    }

    private void writeResult(ServletResponse resp, RespEntity<Object> rest) throws IOException {
        resp.setContentType("text/html; charset=utf-8");
        PrintWriter writer = resp.getWriter();
        writer.write(JSON.toJSONString(rest));
        writer.close();
    }

}
