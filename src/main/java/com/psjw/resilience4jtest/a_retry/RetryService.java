package com.psjw.resilience4jtest.a_retry;

import com.psjw.resilience4jtest.exception.RetryException;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RetryService {
    private static final String SIMPLE_RETRY_CONFIG = "simpleRetryConfig";


    @Retry(name = SIMPLE_RETRY_CONFIG, fallbackMethod = "fallback")
    public String process(String param) {
        return callAnotherServer(param);
    }

    private String fallback(String param, Exception e) {
        //retry에 전부 실패해야 fallback이 호출된다.
        log.info("fallback! your request is {}", param);
        return "Recovered : " + e.toString();
    }

    private String callAnotherServer(String param) {
        //retry exception은 retry된다.
        throw new RetryException("retry exception");
        //ignore exception은 retry되지 않고 바로 바로 예외가 클라이언트에 전달된다.
        //throw new IgnoreException("ignore exception");
    }
}
