package com.psjw.resilience4jtest.b_retry_without_resilience4j;

import com.psjw.resilience4jtest.exception.RetryException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RetryService {

    private static final int MAX_ATTEMPS = 3;
    private static final int WAIT_DURATION = 1000;

    public String process(String param) throws InterruptedException {
        String result = null;

        int retryCount = 0;
        while (retryCount++ < MAX_ATTEMPS) {
            try {
                result = callAnotherServer(param);
            } catch (RetryException e) {
                if(retryCount == MAX_ATTEMPS) {
                    return fallback(param, e);
                }

                Thread.sleep(WAIT_DURATION);
            }
            if(result!= null) {
                break;
            }
        }
        return result;
    }

    private String fallback(String param, Exception e) {
        //retry에 전부 실패해야 fallback이 호출된다.
        log.info("fallback! your request is {}", param);
        return "Recovered : " + e.toString();
    }

    private String callAnotherServer(String param) {
        log.info("callAnotherServer! your request is {}", param);
        //retry exception은 retry된다.
        throw new RetryException("retry exception");
        //ignore exception은 retry되지 않고 바로 바로 예외가 클라이언트에 전달된다.
        //throw new IgnoreException("ignore exception");
    }
}
