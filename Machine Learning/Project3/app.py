from flask import Flask, render_template, request
from wtforms import Form, TextAreaField, validators
from wine import Wine

app = Flask(__name__)

wine = Wine()
result = [0] * 13
pred = 0


class WineForm(Form):
    a = TextAreaField('', [validators.DataRequired()])
    b = TextAreaField('', [validators.DataRequired()])
    c = TextAreaField('', [validators.DataRequired()])
    d = TextAreaField('', [validators.DataRequired()])
    e = TextAreaField('', [validators.DataRequired()])
    f = TextAreaField('', [validators.DataRequired()])
    g = TextAreaField('', [validators.DataRequired()])
    h = TextAreaField('', [validators.DataRequired()])
    i = TextAreaField('', [validators.DataRequired()])
    j = TextAreaField('', [validators.DataRequired()])
    k = TextAreaField('', [validators.DataRequired()])
    l = TextAreaField('', [validators.DataRequired()])
    m = TextAreaField('', [validators.DataRequired()])


class CorrectForm(Form):
    incorrect = TextAreaField('', [validators.DataRequired()])


@app.route('/')
def home():
    form = WineForm(request.form)
    return render_template('homepage.html', form=form)


@app.route('/wine', methods=['POST'])
def index():
    form = WineForm(request.form)
    return render_template('wine.html', form=form)


@app.route('/wine_result', methods=['GET', 'POST'])
def hello():
    form = WineForm(request.form)
    if request.method == 'POST' and form.validate():
        names = ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm']
        for i in range(13):
            result[i] = float(request.form[names[i]])

        pred = wine.predict(result)
        correctForm = CorrectForm()
        return render_template('wine_result.html', name=pred, form=correctForm)
    return render_template('wine.html', form=form)


@app.route('/correct', methods=['POST'])
def success():
    form = CorrectForm(request.form)
    wine.retrain(result, pred)
    return render_template('success.html', form=form)


@app.route('/incorrect', methods=['POST'])
def unsuccess():
    form = CorrectForm(request.form)
    return render_template('unsuccess.html', form=form)


@app.route('/incorrectUpdate', methods=['POST'])
def unsuccess_update():
    form = CorrectForm(request.form)
    if request.method == 'POST' and form.validate():
        target = request.form['incorrect']
        wine.retrain(result, target)
        return render_template('success.html', form=form)
    return render_template('unsuccess.html', form=form)


if __name__ == '__main__':
    app.run(debug=True)
