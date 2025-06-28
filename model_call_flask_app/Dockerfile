FROM python:3.11-slim as builder

RUN apt-get update && apt-get install -y \
    git cmake build-essential \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /app

RUN pip install --no-cache-dir --upgrade pip && \
    pip install --no-cache-dir numpy==1.26.3

COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt

RUN python -c "from transformers import pipeline; pipeline('translation_en_to_es', model='Helsinki-NLP/opus-mt-en-es')"
RUN python -c "from transformers import pipeline; pipeline('text-classification', model='Narrativaai/fake-news-detection-spanish')"

FROM python:3.11-slim
WORKDIR /app
RUN apt-get update && apt-get install -y libgomp1 && rm -rf /var/lib/apt/lists/*
COPY --from=builder /root/.cache/huggingface /root/.cache/huggingface
COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt
COPY . .
EXPOSE 5000
CMD ["python", "app.py"]