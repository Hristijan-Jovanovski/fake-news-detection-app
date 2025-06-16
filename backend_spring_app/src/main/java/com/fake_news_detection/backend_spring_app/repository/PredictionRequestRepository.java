package com.fake_news_detection.backend_spring_app.repository;

import com.fake_news_detection.backend_spring_app.model.PredictionRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PredictionRequestRepository extends JpaRepository<PredictionRequest,Long> {
    List<PredictionRequest> findByUsername(String username);
    List<PredictionRequest> findByUserId(Long userId);


}
