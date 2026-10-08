package com.pedrofl2001.MagicFridgeAI.service;

import com.pedrofl2001.MagicFridgeAI.model.FoodItem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GeminiService {

    private final WebClient webClient;

    private final String geminiModel = "gemini-3.8-flash";

    private final String apiKey;

    public GeminiService(
            WebClient webClient,
            @Value("${api.key}") String apiKey) {
        this.webClient = webClient;
        this.apiKey = apiKey;
    }



    public Mono<String> generateRecipe(List<FoodItem> foodItems) {

        String foods = foodItems.stream()
                .map(item -> String.format("%s (%s) - Quantidade: %d. Validade: %s",
                        item.getName(), item.getCategory(), item.getQuantity(), item.getDueDate()))
                .collect(Collectors.joining("\n"));

        String prompt = "Com base no itens que eu tenho disponiveis em minha geladeira e com base em suas data de validade e quantidades. gere uma receita para mim. Seguem os itens: "
                + foods;

        Map<String, Object> requestBody = Map.of(
                "model", geminiModel,
                "input", prompt
        );




        return webClient.post()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header("Api-Revision", "2026-05-20")
                .header("x-goog-api-key", apiKey)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> {
                    var steps = (List<Map<String, Object>>) response.get(("steps"));
                    if(steps != null && !steps.isEmpty()){
                        var modelOutput = steps.stream()
                                .filter(step -> step.get("type").equals("model_output"))
                                .findFirst();
                        if(modelOutput.isPresent()){
                            var content = (List<Map<String, Object>>) modelOutput.get().get("content");

                            if(content != null && !content.isEmpty()){
                                return content.get(0).get("text").toString();
                            }
                        }
                    }
                    return "Nenhuma receita foi gerada";
                });
    };
}
