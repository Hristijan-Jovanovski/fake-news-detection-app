from flask import Flask, request, jsonify
from transformers import pipeline

app = Flask(__name__)
print(" Loading translation model...")
try:
    translator = pipeline("translation_en_to_es", model="Helsinki-NLP/opus-mt-en-es")
    print(" Translator ready.")
    print(" Loading fake news classifier...")
    ckpt = "Narrativaai/fake-news-detection-spanish"
    classifier = pipeline("text-classification", model=ckpt)
    print(" Classifier ready.")
except Exception as e:
    print(f" Error loading models: {str(e)}")
    raise e

CONFIDENCE_THRESHOLD = 0.7


@app.route('/predict', methods=['POST'])
def predict():
    try:
        data = request.get_json()
        if not data or 'text' not in data:
            return jsonify({'message': 'No input text provided'}), 400

        english_text = data['text']
        print(" Received:", english_text)


        translation_result = translator(english_text, max_length=512)
        spanish_text = translation_result[0]['translation_text']
        print(" Translated:", spanish_text)

        prediction = classifier(spanish_text)[0]
        score = prediction["score"]
        raw_label = prediction["label"].lower()
        print(" Prediction result:", prediction)

        if score < CONFIDENCE_THRESHOLD:
            label = "Uncertain"
        else:
            label = "Fake news" if raw_label == "fake" else "Real news"

        return jsonify({
            'label': label,
            'confidence': round(score, 4)
        })

    except Exception as e:
        print(f" Error processing request: {str(e)}")
        return jsonify({'error': str(e)}), 500


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)