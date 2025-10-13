package dongpb.agenticai.orchestratorservice.domain.mcp;

import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.HashMap;
import java.util.Map;

public class HttpMcpClient extends McpClient {
    private final String serverUrl;
    private final CloseableHttpClient httpClient;
    private final Map<String, String> headers;

    public HttpMcpClient(String name, String version, String url, Map<String, String> headers) {
        super(name, version);
        this.serverUrl = url;
        this.headers = headers;
        this.httpClient = HttpClients.createDefault();
    }

    @Override
    public void connect() throws Exception {
        // HTTP không cần "connect" như stdio
        // Chỉ cần thực hiện initialize
        JsonNode response = initialize();
        System.out.println("Connected to MCP server: " + response);
    }

    @Override
    public JsonNode sendRequest(String method, Map<String, Object> params) throws Exception {
        // Tạo request
        Map<String, Object> request = createRequest(method, params);
        String requestJson = objectMapper.writeValueAsString(request);

        // Tạo HTTP POST request
        HttpPost httpPost = new HttpPost(serverUrl);
        httpPost.setEntity(new StringEntity(requestJson));
        httpPost.setHeader("Content-Type", "application/json");

        // Thêm custom headers
        if (headers != null) {
            headers.forEach(httpPost::setHeader);
        }

        // Gửi request
        return httpClient.execute(httpPost, response -> {
            String responseBody = EntityUtils.toString(response.getEntity());
            JsonNode jsonResponse = objectMapper.readTree(responseBody);

            if (jsonResponse.has("error")) {
                throw new RuntimeException("MCP Error: " + jsonResponse.get("error"));
            }

            return jsonResponse.get("result");
        });
    }

    @Override
    public void close() throws Exception {
        if (httpClient != null) {
            httpClient.close();
        }
    }

    public static void main(String[] args) {
        try {
            Map<String, String> headers = new HashMap<>();

            HttpMcpClient client = new HttpMcpClient(
                    "mcp-server-demo",
                    "1.0.0",
                    "http://localhost:8080/mcp",
                    headers
            );

            client.connect();

            // Lấy resources
            JsonNode resources = client.sendRequest("resources/list", null);
            System.out.println("Resources: " + resources);

            client.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
