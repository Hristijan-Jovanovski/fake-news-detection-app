package com.fake_news_detection.backend_spring_app.service;

import com.fake_news_detection.backend_spring_app.model.PredictionRequest;

import java.util.List;

public interface PredictionRequestService {
    PredictionRequest save(PredictionRequest predictionRequest);
    List<PredictionRequest> getRequestsByUser(String username);

    List<PredictionRequest> getRequestsByUserId(Long userId);
}
