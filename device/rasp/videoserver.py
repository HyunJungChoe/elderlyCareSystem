# -*- coding: utf-8 -*- 
from flask import Flask, render_template , url_for
import glob
import sys, time, datetime, os
import subprocess
app = Flask(__name__)


@app.route('/')
def hello_world():
    subprocess.call("find /home/pi/_GUI/static -type f -size -10k -delete", shell=True)  #아무것도 찍히지 않은 잉여 파일들 자동 삭제 
    newest = max(glob.iglob('/home/pi/_GUI/static/*.mp4'), key=os.path.getctime)
    newfilename = os.path.basename(newest)  # 제일 상위 파일 전달 
    return render_template('img_static.html', NewVid = newfilename)
    

if __name__ == '__main__':
    app.debug = True  # 코드 변경되었을 때 서버 재시작.
    app.run(host='192.168.1.19', port = 5000)