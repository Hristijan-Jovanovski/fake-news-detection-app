package com.fake_news_detection.backend_spring_app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PredictionResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String label;
    private double confidence;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_id", nullable = false)
    @ToString.Exclude
    private PredictionRequest request;

    public void setRequest(PredictionRequest request) {
        if (this.request != null) {
            this.request.setResponse(null);
        }
        if (request != null) {
            request.setResponseWithoutBidirectional(this);
        }
        this.request = request;
    }

    public void setRequestWithoutBidirectional(PredictionRequest request) {
        this.request = request;
    }

    public String getLabel() {
        return label;
    }

    public double getConfidence() {
        return confidence;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }
}