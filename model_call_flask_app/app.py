from flask import Flask, request, jsonify
from transformers import pipeline

app = Flask(__name__)

translator = pipeline("translation_en_to_es", model="Helsinki-NLP/opus-mt-en-es")

ckpt = "Narrativaai/fake-news-detection-spanish"
classifier = pipeline("text-classification", model=ckpt)

CONFIDENCE_THRESHOLD = 0.7

@app.route('/predict', methods=['POST'])
def predict():
    data = request.get_json()
    if not data or 'text' not in data:
        return jsonify({'message': 'No input text provided'}), 400

    english_text = data['text']
    spanish_text = translator(english_text, max_length=512)[0]['translation_text']

    print("Translated:", spanish_text)

    prediction = classifier(spanish_text)[0]
    score = prediction["score"]
    raw_label = prediction["label"].lower()

    if score < CONFIDENCE_THRESHOLD:
        label = "Uncertain"
    else:
        label = "Fake news" if raw_label == "fake" else "Real news"

    return jsonify({
        'label': label,
        'confidence': round(score, 4)
    })


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)
