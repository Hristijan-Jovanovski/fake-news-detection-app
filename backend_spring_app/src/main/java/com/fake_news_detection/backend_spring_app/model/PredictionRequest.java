package com.fake_news_detection.backend_spring_app.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PredictionRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = Integer.MAX_VALUE)
    private String text;

    private String username;

    @OneToOne(mappedBy = "request", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @JsonManagedReference("request-response")
    private PredictionResponse response;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference("user-requests")
    private User user;

    public void setUser(User user) {
        this.user = user;
    }
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