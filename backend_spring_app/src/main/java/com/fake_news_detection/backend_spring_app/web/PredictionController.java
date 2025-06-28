package com.fake_news_detection.backend_spring_app.web;

import com.fake_news_detection.backend_spring_app.model.PredictionRequest;
import com.fake_news_detection.backend_spring_app.model.PredictionResponse;
import com.fake_news_detection.backend_spring_app.model.User;
import com.fake_news_detection.backend_spring_app.repository.UserRepository;
import com.fake_news_detection.backend_spring_app.service.PredictionRequestService;
import com.fake_news_detection.backend_spring_app.service.PredictionResponseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://frontend-app:3000")
@RestController
@RequestMapping("/api/predictions")
public class PredictionController {

    private final PredictionRequestService predictionRequestService;
    private final PredictionResponseService predictionResponseService;
    private final RestTemplate restTemplate;
    private final UserRepository userRepository;

    public PredictionController(PredictionRequestService predictionRequestService,
                                PredictionResponseService predictionResponseService,
                                RestTemplate restTemplate, UserRepository userRepository) {
        this.predictionRequestService = predictionRequestService;
        this.predictionResponseService = predictionResponseService;
        this.restTemplate = restTemplate;
        this.userRepository = userRepository;
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<PredictionRequest>> getRequestsByUser(@PathVariable String username) {
        List<PredictionRequest> requests = predictionRequestService.getRequestsByUser(username);
        if (requests.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(requests);
    }
    @GetMapping("/user")
    public ResponseEntity<List<PredictionRequest>> getRequestsByUser(
            @RequestParam("userId") Long userId) {

        List<PredictionRequest> requests = predictionRequestService.getRequestsByUserId(userId);
        if (requests.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(requests);
    }
    String flaskUrl = "http://flask-app:5000/predict";
    @PostMapping
    public ResponseEntity<PredictionResponse> createPrediction(
            @RequestBody PredictionRequest request,
            @RequestHeader("userId") Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        request.setUser(user);
        if (request.getText() == null || request.getText().isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        if (request.getUsername() == null || request.getUsername().isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        Map<String, Object> flaskResponse = restTemplate.postForObject(
                flaskUrl,
                Map.of("text", request.getText()),
                Map.class
        );

        PredictionResponse response = new PredictionResponse();
        response.setLabel((String) flaskResponse.get("label"));
        response.setConfidence((Double) flaskResponse.get("confidence"));

        request.setResponse(response);

        PredictionRequest savedRequest = predictionRequestService.save(request);

        return ResponseEntity.ok(savedRequest.getResponse());
    }
}