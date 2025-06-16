package com.fake_news_detection.backend_spring_app.service;

import com.fake_news_detection.backend_spring_app.model.PredictionResponse;

public interface PredictionResponseService {
    PredictionResponse save(PredictionResponse response);
}
