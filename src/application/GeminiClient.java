package application;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GeminiClient {

    private static final String API_KEY = "AIzaSyDVa-eChT7Jm_zq9nuuc3Kols3EvItjrOo";
    private static final String ENDPOINT = "https://generativelanguage.googleapis.com/v1/models/gemini-1.5-flash:generateContent?key=" + API_KEY;

    public static String askGemini(String userInput) {
        try {
            String requestBody = """
                {
                  "contents": [
                    {
                      "parts": [
                        {
                          "text": "%s"
                        }
                      ]
                    }
                  ]
                }
                """.formatted(userInput.replace("\"", "\\\""));

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(ENDPOINT))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                return "Erro do Gemini (HTTP " + response.statusCode() + "):\n" + response.body();
            }

            String responseBody = response.body();

            ObjectMapper mapper = new ObjectMapper();
            JsonNode json = mapper.readTree(responseBody);

            return json.path("candidates").get(0)
                       .path("content").path("parts").get(0)
                       .path("text").asText("Erro: resposta inesperada.");

        } catch (Exception e) {
            return "Erro ao conectar com Gemini: " + e.getMessage();
        }
    }
}
