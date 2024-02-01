# -*- coding: utf-8 -*-
import os
from os import chdir
import sys
import inspect
import io
# from refrigerator.models import Image

def deep_learning(image_path):

    current_file_path = os.path.abspath(inspect.getfile(inspect.currentframe()))
    base_path = os.path.dirname(os.path.dirname(os.path.dirname(current_file_path)))

    yolo_path = os.path.join(base_path, 'yolov5')

    chdir(yolo_path)

    # YOLOv5 실행
    os.system('python detect.py --save-crop --source "{}" --weights ./best.pt --img 416 --conf 0.5 --save-txt --exist-ok --project ../media/'.format(image_path))

    chdir(base_path)

    # 레이블 파일 경로
    path_dir = './media/exp/labels/'
    file_list = os.listdir(path_dir)

    sorted_file_list = [(os.path.join(path_dir, file), os.path.getctime(os.path.join(path_dir, file))) for file in file_list]
    last_file = max(sorted_file_list, key=lambda x: x[1])[0]



    with io.open(last_file, 'r', encoding='UTF8') as f:
        labeling = f.readline()

    cls = int(labeling.split()[0])

    chdir(base_path)
    path_dir2 = 'refrigerator/static/jeryo/jeryolist.txt'

    with open(path_dir2, 'r') as f:
        for i in range(cls + 1):
            labeling2 = f.readline()

    name = labeling2.split()[1]

    return name
