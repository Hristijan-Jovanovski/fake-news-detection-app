package com.fake_news_detection.backend_spring_app.service.impl;

import com.fake_news_detection.backend_spring_app.model.PredictionResponse;
import com.fake_news_detection.backend_spring_app.repository.PredictionResponseRepository;
import com.fake_news_detection.backend_spring_app.service.PredictionResponseService;
import org.springframework.stereotype.Service;

@Service
public class PredictionResponseServiceImpl  implements PredictionResponseService {
    private final PredictionResponseRepository predictionResponseRepository;

    public PredictionResponseServiceImpl(PredictionResponseRepository predictionResponseRepository) {
        this.predictionResponseRepository = predictionResponseRepository;
    }


    public PredictionResponse save(PredictionResponse response) {
        return predictionResponseRepository.save(response);
    }
}
