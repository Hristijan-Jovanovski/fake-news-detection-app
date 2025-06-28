package com.fake_news_detection.backend_spring_app.service.impl;

import com.fake_news_detection.backend_spring_app.model.PredictionRequest;
import com.fake_news_detection.backend_spring_app.model.PredictionResponse;
import com.fake_news_detection.backend_spring_app.repository.PredictionRequestRepository;
import com.fake_news_detection.backend_spring_app.service.PredictionRequestService;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional

public class PredictionRequestServiceImpl implements PredictionRequestService {

    private final PredictionRequestRepository predictionRequestRepository;
    private final RestTemplate restTemplate;

    public PredictionRequestServiceImpl(PredictionRequestRepository predictionRequestRepository) {
        this.predictionRequestRepository = predictionRequestRepository;
        this.restTemplate = new RestTemplate();
    }

    public PredictionRequest save(PredictionRequest request) {

        Map<String, String> payload = new HashMap<>();
        payload.put("text", request.getText());

        String flaskUrl = "http://flask-app:5000/predict";

        ResponseEntity<Map> responseEntity = restTemplate.postForEntity(flaskUrl, payload, Map.class);
        Map<String, Object> predictionMap = responseEntity.getBody();

        if (predictionMap == null || !predictionMap.containsKey("label")) {
            throw new RuntimeException("Prediction service returned invalid response");
        }

        PredictionResponse predictionResponse = new PredictionResponse();
        predictionResponse.setLabel((String) predictionMap.get("label"));
        predictionResponse.setConfidence(((Number) predictionMap.get("confidence")).doubleValue());

        predictionResponse.setRequest(request);
        request.setResponse(predictionResponse);

        return predictionRequestRepository.save(request);
    }

    public List<PredictionRequest> getRequestsByUser(String username) {
        return predictionRequestRepository.findByUsername(username);
    }

    @Override
    public List<PredictionRequest> getRequestsByUserId(Long userId) {
     return predictionRequestRepository.findByUserId(userId);
    }
}
