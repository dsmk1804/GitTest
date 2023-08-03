from flask import Flask, render_template
from flask.blueprints import Blueprint

app = Flask(__name__)

# home blueprint 생성
home_bp = Blueprint('home', __name__, url_prefix='/')

# page1 route 생성 (URL: /page1)
@home_bp.route('/page1')
def page_one():
    return render_template('page_one.html')

# page2 route 생성 (URL: /page2)
@home_bp.route('/page2')
def page_two():
    return render_template('path/to/page_two.html')

# app 객체에 blueprint 등록하기
app.register_blueprint(home_bp)

if __name__ == '__main__':
    app.run()
