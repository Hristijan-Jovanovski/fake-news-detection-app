# 📰 Fake News Detection Web App

<div align="center">

![Python](https://img.shields.io/badge/python-3.10+-green.svg)
![Java](https://img.shields.io/badge/java-17+-orange.svg)
![React](https://img.shields.io/badge/react-18+-61dafb.svg)

*An intelligent full-stack application that leverages machine learning to detect fake news articles with real-time classification and user tracking.*

</div>

---

## 🎯 Overview

This application provides a comprehensive solution for fake news detection using state-of-the-art machine learning models. It features a modern React frontend, robust Spring Boot backend, and an intelligent Flask-based ML service.

### ✨ Key Features

- 🤖 **AI-Powered Detection** - Uses advanced NLP models for accurate classification
- 👤 **User Management** - Individual user tracking and prediction history
- 📊 **Confidence Scoring** - Real-time accuracy metrics
- 💾 **Persistent Storage** - PostgreSQL database for data persistence
- 🎨 **Modern UI** - Responsive React interface with real-time feedback

---

## 🏗️ Architecture

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   React App     │───▶│  Spring Boot    │───▶│   Flask ML      │
│   (Frontend)    │    │   (Backend)     │    │   (Model API)   │
│   Port: 3000    │    │   Port: 8080    │    │   Port: 5000    │
└─────────────────┘    └─────────────────┘    └─────────────────┘
                                │
                                ▼
                       ┌─────────────────┐
                       │   PostgreSQL    │
                       │   (Database)    │
                       │   Port: 5432    │
                       └─────────────────┘
```

### 🔧 Technology Stack

| Component | Technology | Purpose |
|-----------|------------|---------|
| **Frontend** | React 18+ | User interface and interaction |
| **Backend API** | Spring Boot (Java 17+) | Business logic and data management |
| **ML Service** | Flask + Hugging Face | Model inference and predictions |
| **Database** | PostgreSQL | Data persistence and user history |
| **ML Models** | Transformers | Fake news detection and translation |

---

## 🚀 Quick Start

### Prerequisites

- **Node.js** 16+ and npm
- **Java** 17+ and Maven
- **Python** 3.10+
- **PostgreSQL** 12+





### Start Services

#### 🤖 ML Model Service (Flask)

```bash
cd model_call_flask_app
pip install -r requirements.txt
python app.py
```

**Service URL:** `http://localhost:5000`

#### 🔙 Backend API (Spring Boot)

```bash
cd backend_spring_app

# Configure database connection in application.properties
echo "spring.datasource.url=jdbc:postgresql://localhost:5432/fake_news
spring.datasource.username=postgres
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update" > src/main/resources/application.properties

# Run the application
./mvnw spring-boot:run
```

**API URL:** `http://localhost:8080`




## 📖 API Documentation

### Prediction Endpoints

#### Create Prediction
```http
POST /api/predictions
Content-Type: application/json

{
  "username": "johndoe",
  "text": "The moon is made of cheese and scientists are hiding this fact."
}
```

**Response:**
```json
{
  "id": 1,
  "username": "johndoe",
  "text": "The moon is made of cheese...",
  "label": "Fake news",
  "confidence": 0.9745,
  "timestamp": "2025-01-15T10:30:00Z"
}
```

#### Get User History
```http
GET /api/predictions/user/{username}
```

**Response:**
```json
[
  {
    "id": 1,
    "username": "johndoe",
    "text": "Sample news text...",
    "label": "Real news",
    "confidence": 0.8234,
    "timestamp": "2025-01-15T10:30:00Z"
  }
]
```





## 🧪 Testing Examples

### Real News Example
```bash
curl -X POST http://localhost:8080/api/predictions \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "text": "Scientists at NASA have confirmed the successful landing of the Perseverance rover on Mars."
  }'
```

### Fake News Example
```bash
curl -X POST http://localhost:8080/api/predictions \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "text": "Aliens have invaded Earth and are living among us disguised as politicians."
  }'
```

---

## 📁 Project Structure

```
fake-news-detection-app/
├── 📂 frontend_react_app/          # React frontend application
│   ├── 📂 src/
│   │   ├── 📂 components/
│   │   ├── 📂 services/
│   │   └── 📄 App.js
│   └── 📄 package.json
├── 📂 backend_spring_app/          # Spring Boot backend API
│   ├── 📂 src/main/java/
│   │   └── 📂 com/example/
│   ├── 📂 src/main/resources/
│   └── 📄 pom.xml
├── 📂 model_call_flask_app/        # Flask ML service
│   ├── 📄 app.py
│   ├── 📄 requirements.txt
│   └── 📂 models/
└── 📄 README.md
```

---

## 🔬 Machine Learning Details

### Models Used

| Model | Purpose | Language | Accuracy |
|-------|---------|----------|----------|
| [NarrativaAI/fake-news-detection-spanish](https://huggingface.co/NarrativaAI/fake-news-detection-spanish) | Fake news classification | Spanish | ~94% |
| [Helsinki-NLP/opus-mt-en-es](https://huggingface.co/Helsinki-NLP/opus-mt-en-es) | English to Spanish translation | EN → ES | High quality |

### Processing Pipeline

1. **Input Validation** - Text preprocessing and sanitization
2. **Language Detection** - Automatic language identification
3. **Translation** - English text translated to Spanish if needed
4. **Classification** - ML model predicts fake/real with confidence
5. **Response** - Structured output with metadata

---


#### Backend (Spring Boot)
```properties
# Database Configuration
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/fake_news
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=your_password

# ML Service Configuration
```






<div align="center">


</div>
