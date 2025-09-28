package dongpb.agenticai.orchestratorservice.domain.tool.http;

import dongpb.agenticai.orchestratorservice.domain.tool.Tool;
import dongpb.agenticai.orchestratorservice.domain.tool.ToolType;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

@Service
public class HttpTool implements Tool {

    @Override
    public String getType() {
        return ToolType.HTTP.name();
    }

    @Override
    public String getDescription() {
        return """
                 Tool: http
                
                 Input schema:
                 {
                      "method": "string (GET | POST | PUT | DELETE)",
                      "url": "string (full URL, including scheme http/https)",
                      "headers": { "string": "string" },
                      "queryParams": { "string": "string | number | boolean" },
                      "body": "object or string (optional for POST/PUT)"
                 }
                
                 Response schema:
                 {
                   "statusCode": number,
                   "headers": { "string": "string" },
                   "body": json object
                 }
                
                 Example request:
                 {
                    "method": "GET",
                    "url": "https://api.openweathermap.org/data/2.5/weather",
                    "queryParams": { "q": "Hanoi", "appid": "YOUR_API_KEY" },
                    "headers": { "Accept": "application/json" }
                 }
                
                 Example response:
                 {
                   "statusCode": 200,
                   "body": "{ \\"temp\\": 30.5, \\"weather\\": \\"Sunny\\" }"
                 }
                
                """;
    }

    /**
     * input bao gồm method, url, headers, queryParams, body
     *
     * @param input
     * @return
     */
    @Override
    public Map<String, Object> execute(Map<String, Object> input) {
        try {
            String method = (String) input.getOrDefault("method", "GET");
            String url = (String) input.get("url");

            // Handle query params
            if (input.containsKey("queryParams")) {
                @SuppressWarnings("unchecked")
                Map<String, Object> queryParams = (Map<String, Object>) input.get("queryParams");
                if (queryParams != null && !queryParams.isEmpty()) {
                    StringBuilder sb = new StringBuilder(url);
                    if (!url.contains("?")) {
                        sb.append("?");
                    } else if (!url.endsWith("&")) {
                        sb.append("&");
                    }
                    sb.append(queryParams.entrySet().stream()
                            .map(e -> e.getKey() + "=" + e.getValue().toString())
                            .reduce((a, b) -> a + "&" + b).orElse(""));
                    url = sb.toString();
                }
            }

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest.Builder builder = HttpRequest.newBuilder()
                    .uri(URI.create(url));

            // Add headers
            if (input.containsKey("headers")) {
                @SuppressWarnings("unchecked")
                Map<String, String> headers = (Map<String, String>) input.get("headers");
                if (headers != null) {
                    headers.forEach(builder::header);
                }
            }

            // Add body if POST/PUT
            if ("POST".equalsIgnoreCase(method) || "PUT".equalsIgnoreCase(method)) {
                String body = input.containsKey("body") ? input.get("body").toString() : "";
                builder.method(method.toUpperCase(), HttpRequest.BodyPublishers.ofString(body));
            } else {
                builder.method(method.toUpperCase(), HttpRequest.BodyPublishers.noBody());
            }

            HttpRequest request = builder.build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return Map.of(
                    "statusCode", response.statusCode(),
                    "body", response.body()
            );

        } catch (Exception e) {
            return Map.of(
                    "statusCode", 500,
                    "body", "Error: " + e.getMessage()
            );
        }
    }

}
