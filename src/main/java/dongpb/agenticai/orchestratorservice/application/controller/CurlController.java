package dongpb.agenticai.orchestratorservice.application.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CurlController {

    @PostMapping("/execute-curl")
    public String executeCurl(@RequestParam String command) {
        if (!command.trim().startsWith("curl")) {
            return "Invalid command. Must start with 'curl'.";
        }

        try {
            // Split the command properly
            List<String> cmdParts = new ArrayList<>();
            for (String part : command.split(" ")) {
                if (!part.isBlank()) cmdParts.add(part);
            }

            ProcessBuilder processBuilder = new ProcessBuilder(cmdParts);
            Process process = processBuilder.start();

            // Read only standard output (stdout)
            BufferedReader stdOut = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = stdOut.readLine()) != null) {
                output.append(line).append("\n");
            }

            // Wait for process to finish
            int exitCode = process.waitFor();

            return output.toString().trim(); // Return only clean JSON

        } catch (Exception e) {
            return "Error executing curl: " + e.getMessage();
        }
    }
}
