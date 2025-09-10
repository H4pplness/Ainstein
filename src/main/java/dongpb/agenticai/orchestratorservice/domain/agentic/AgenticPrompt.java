package dongpb.agenticai.orchestratorservice.domain.agentic;

import dongpb.agenticai.orchestratorservice.domain.model.AIRequest;
import dongpb.agenticai.orchestratorservice.domain.resource.Function;
import dongpb.agenticai.orchestratorservice.domain.resource.Resource;
import dongpb.agenticai.orchestratorservice.domain.tool.Tool;

import java.util.List;

public class AgenticPrompt {
    private static final String PROMPT = """
            Bạn là một trợ lý AI hoạt động theo hướng agentic.
            Nhiệm vụ của bạn là tiếp nhận yêu cầu từ người dùng, tự động suy nghĩ các bước cần làm, gọi tool thích hợp, nhận kết quả từ hệ thống, và cuối cùng trả lại câu trả lời cho người dùng bằng ngôn ngữ tự nhiên.
            
            ### Luồng hoạt động:
            
            1) **Người dùng gửi yêu cầu ban đầu**
            Cấu trúc:
            {
              "role": "user",
              "message": "<Yêu cầu của người dùng>"
            }
            
            ---
            
            2) **AI xử lý**
            AI sẽ có ba loại phản hồi:
            
            a) Suy nghĩ (không gọi tool, chỉ mô tả quá trình):
            {
              "role": "assistant",
              "method": "think",
              "message": {
                "goal": "<Mục tiêu tổng quát>",
                "thought": "<Suy nghĩ tiếp theo>"
              }
            }
            
            b) Thực thi (gọi tool):
            {
              "role": "assistant",
              "method": "execute",
              "message": {
                "tool": "<Tên tool>",
                "input": {
                  "<Tham số đầu vào cho tool>"
                }
              }
            }
            
            c) Trả kết quả cuối cho người dùng:
            {
              "role": "assistant",
              "method": "return",
              "message": "<Câu trả lời cuối cùng cho người dùng>"
            }
            
            ---
            
            3) **Hệ thống trả kết quả tool**
            Khi AI gọi tool, hệ thống sẽ trả kết quả về theo cấu trúc:
            {
              "role": "system",
              "method": "response",
              "message": {
                "tool": "<Tên tool>",
                "output": {
                  "<Kết quả trả về từ tool>"
                }
              }
            }
            
            ---
            
            4) **Vòng lặp**
            AI có thể lặp lại nhiều lần bước **think → execute → response từ system** cho đến khi đạt mục tiêu.
            Khi đã đủ thông tin, AI sẽ dùng **method=return** để trả về kết quả cuối cùng cho người dùng.
            
            ---
            - Các tool mà bạn có thể sử dụng:
            %s
            
            - Danh sách tài nguyên mà bạn được phép truy cập:
            %s
            
            ### LƯU Ý:
            - Bạn chỉ được phản hồi bằng JSON theo đúng cấu trúc trên, không được thêm text ngoài JSON.
            - Nếu không có tool phù hợp hoặc không tìm được câu trả lời, bạn phải trả về luôn với `method = "return"`.
            """;

    private static final String HTTP_TOOL = """
            http:
            Tên : http
            Mục đích : để thao tác với các hệ thống bên ngoài qua giao thức http
            Mẫu input :
            {
            	"url" : "<url của hệ thống ngoài>",
            	"method" : "<GET,POST,PUT,DEL...>"
            	"queryParams" : {
            		"<param1>" : "<Giá trị>",
            		"<param2>" : "<Giá trị>"
            	},
            	"body" : {
            		"<field1>" : "Giá trị",
            		"<field2>" : {
            			<Giá trị>
            		}
            	}
            }
            
            Những API được sử dụng để tương tác với hệ thống ngoài bao gồm các API sau :
            
            - Lấy thông tin người dùng :
            Method : GET
            URL : http://localhost:8081/v1/user
            Query params :
                    name : Tên người dùng | String
                   	email : Email của người dùng | String
                   	phone : Số điện thoại của người dùng | String
            
            - Tra cứu thông tin của tài khoản
            Method : GET
            URL : http://localhost:8081/v1/account/{id}
            
            - Truy vấn giao dịch của tài khoản :
            Method : GET
            URL : http://localhost:8081/v1/account/{id}/query
            Query params :
            	from : Ngày bắt đầu | dd/MM/yyyy
            	to : Ngày kết thúc | dd/MM/yyyy
            """;

    public static AIRequest getSimpleInitPrompt(){
        String initPrompt =  String.format(PROMPT, HTTP_TOOL);
        AIRequest.Message initMessage = AIRequest.Message.builder()
                .role("user")
                .content(initPrompt)
                .build();
        AIRequest.Message exampleMessage = AIRequest.Message.builder()
                .role("assistant")
                .content("""
                        {
                            "role" : "assistant",
                            "method" : "return",
                            "message" : "Xin chào, tôi là AI hỗ trợ cho bạn !"
                        }
                        """)
                .build();

        AIRequest aiRequest = new AIRequest();
        aiRequest.setModel("groq");
        aiRequest.addMessages(List.of(initMessage,exampleMessage));
        return aiRequest;
    }

    public static AIRequest getInitPrompt(List<Tool> tools, List<String> resourceDescriptions) {
        StringBuilder toolSb = new StringBuilder();
        for (Tool tool : tools) {
            toolSb.append(tool.getDescription()).append("\n");
        }

        StringBuilder resourceSb = new StringBuilder();
        for (String resourceDes : resourceDescriptions) {
            resourceSb.append(resourceDes).append("\n");
        }

        String initPrompt = String.format(PROMPT, toolSb, resourceSb);

        AIRequest.Message initMessage = AIRequest.Message.builder()
                .role("user")
                .content(initPrompt)
                .build();

        AIRequest.Message exampleMessage = AIRequest.Message.builder()
                .role("assistant")
                .content("""
                        {
                            "role" : "assistant",
                            "method" : "return",
                            "message" : "Xin chào, tôi là AI hỗ trợ cho bạn !"
                        }
                        """)
                .build();

        AIRequest aiRequest = new AIRequest();
        aiRequest.setModel("groq");
        aiRequest.addMessages(List.of(initMessage,exampleMessage));
        return aiRequest;
    }
}
