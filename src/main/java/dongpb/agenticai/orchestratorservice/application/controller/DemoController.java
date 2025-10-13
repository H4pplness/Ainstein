package dongpb.agenticai.orchestratorservice.application.controller;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
@RequestMapping("/api/v1/")
public class DemoController {
    @Data
    @Builder
    private static class User {
        String name;
        String email;
        String phone;
        String address;
        String accountId;
    }

    @Data
    @Builder
    private static class Account {
        String accountId;
        String name;
        String balance;
        String currency;
        String createdAt;
    }

    @RequestMapping("user")
    public ResponseEntity<User> getUser(@RequestParam(name = "phone") String phone) {
        return ResponseEntity.ok(
                User.builder()
                        .name("Donald Trump")
                        .email("trump@trump.com")
                        .phone("123456789")
                        .address("123456789, 123 Main St, San Francisco, CA 94102")
                        .accountId("123")
                        .build());
    }

    @RequestMapping("/account/{accountId}")
    public ResponseEntity<Account> getAccount(@PathVariable("accountId") String id){
        return ResponseEntity.ok(Account.builder()
                        .accountId("123")
                        .name("Donald Trump")
                        .balance("2,000,000,000.00")
                        .currency("USD")
                        .createdAt("2019-01-01T00:00:00Z")
                .build());
    }

    private final CopyOnWriteArrayList<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    @GetMapping(path = "/connect", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter connect() {
        SseEmitter emitter = new SseEmitter(0L); // No timeout
        emitters.add(emitter);

        emitter.onCompletion(() -> {
            emitters.remove(emitter);
            System.out.println("Client disconnected. Active: " + emitters.size());
        });

        emitter.onTimeout(() -> emitters.remove(emitter));
        emitter.onError(e -> emitters.remove(emitter));

        // Gửi welcome message
        try {
            emitter.send(SseEmitter.event()
                    .name("connected")
                    .data("Welcome to chat!"));

            emitter.send(SseEmitter.event()
                    .name("hello")
                    .data("Hello, world!"));
        } catch (IOException e) {
            emitters.remove(emitter);
        }

        return emitter;
    }
}
