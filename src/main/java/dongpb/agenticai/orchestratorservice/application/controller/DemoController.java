package dongpb.agenticai.orchestratorservice.application.controller;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
