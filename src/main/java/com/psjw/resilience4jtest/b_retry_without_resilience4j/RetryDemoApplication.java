package com.psjw.resilience4jtest.b_retry_without_resilience4j;

import io.github.resilience4j.core.registry.EntryAddedEvent;
import io.github.resilience4j.core.registry.EntryRemovedEvent;
import io.github.resilience4j.core.registry.EntryReplacedEvent;
import io.github.resilience4j.core.registry.RegistryEventConsumer;
import io.github.resilience4j.retry.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@Slf4j
@SpringBootApplication
public class RetryDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(RetryDemoApplication.class, args);
    }

    @Bean
    public RegistryEventConsumer<Retry> myRegistryEventConsumer() {
        return new RegistryEventConsumer<Retry>() {
            @Override
            public void onEntryAddedEvent(EntryAddedEvent<Retry> entryAddedEvent) {
                //빌셍힌 이벤트에 대해서 로그 확인
                log.info("RegistryEventConsumer onEntryAddedEvent: {}",
                        entryAddedEvent.getAddedEntry().getName());
                entryAddedEvent.getAddedEntry().getEventPublisher().onEvent(event -> {
                    log.info("RegistryEventConsumer onEntryAddedEvent: {}", event);
                });
            }

            @Override
            public void onEntryRemovedEvent(EntryRemovedEvent<Retry> entryRemoveEvent) {
                log.info("RegistryEventConsumer onEntryRemovedEvent: {}",
                        entryRemoveEvent.getRemovedEntry().getName());
            }

            @Override
            public void onEntryReplacedEvent(EntryReplacedEvent<Retry> entryReplacedEvent) {
                log.info("RegistryEventConsumer onEntryReplacedEvent: {}",
                        entryReplacedEvent.getNewEntry().getName());
            }
        };
    }
}
