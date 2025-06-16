package com.fake_news_detection.backend_spring_app.model;

import jakarta.persistence.*;
import lombok.*;
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class PredictionRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = Integer.MAX_VALUE)
    private String text;

    public void setUser(User user) {
        this.user = user;
    }

    private String username;

    @OneToOne(mappedBy = "request", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private PredictionResponse response;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public void setResponse(PredictionResponse response) {
        if (response == null) {
            if (this.response != null) {
                this.response.setRequest(null);
            }
        } else {
            response.setRequestWithoutBidirectional(this);
        }
        this.response = response;
    }

    public PredictionResponse getResponse() {
        return this.response;
    }
    public void setResponseWithoutBidirectional(PredictionResponse response) {
        this.response = response;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }
}