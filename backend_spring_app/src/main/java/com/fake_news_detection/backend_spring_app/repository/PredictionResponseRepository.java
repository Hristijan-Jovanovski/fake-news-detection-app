package com.fake_news_detection.backend_spring_app.repository;

import com.fake_news_detection.backend_spring_app.model.PredictionResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PredictionResponseRepository extends JpaRepository<PredictionResponse, Long> {
}
