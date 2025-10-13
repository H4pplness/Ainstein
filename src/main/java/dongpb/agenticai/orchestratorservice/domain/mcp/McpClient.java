package dongpb.agenticai.orchestratorservice.domain.mcp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public abstract class McpClient {
    protected final ObjectMapper objectMapper = new ObjectMapper();
    protected final AtomicLong requestId = new AtomicLong(0);

    // Client information
    protected String clientName;
    protected String clientVersion;

    public McpClient(String name, String version) {
        this.clientName = name;
        this.clientVersion = version;
    }

    /**
     * Tạo JSON-RPC request
     */
    protected Map<String, Object> createRequest(String method, Map<String, Object> params) {
        Map<String, Object> request = new HashMap<>();
        request.put("jsonrpc", "2.0");
        request.put("id", requestId.incrementAndGet());
        request.put("method", method);
        if (params != null) {
            request.put("params", params);
        }
        return request;
    }

    /**
     * Khởi tạo kết nối và handshake
     */
    public abstract void connect() throws Exception;

    /**
     * Gửi request và nhận response
     */
    public abstract JsonNode sendRequest(String method, Map<String, Object> params) throws Exception;

    /**
     * Đóng kết nối
     */
    public abstract void close() throws Exception;

    /**
     * Initialize handshake với server
     */
    protected JsonNode initialize() throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("protocolVersion", "2024-11-05");

        Map<String, Object> clientInfo = new HashMap<>();
        clientInfo.put("name", clientName);
        clientInfo.put("version", clientVersion);
        params.put("clientInfo", clientInfo);

        Map<String, Object> capabilities = new HashMap<>();
        params.put("capabilities", capabilities);

        return sendRequest("initialize", params);
    }
}
