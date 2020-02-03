import RPi.GPIO as GPIO
import time
GPIO.setmode(GPIO.BCM)
GPIO.setup(13,GPIO.OUT)
GPIO.setup(19,GPIO.OUT)
GPIO.setup(26,GPIO.OUT)
while True:
    GPIO.output(13,GPIO.LOW)
    GPIO.output(19,GPIO.LOW)
    GPIO.output(26,GPIO.HIGH)
    time.sleep(0.5)
    GPIO.output(13,GPIO.HIGH)
    GPIO.output(19,GPIO.LOW)
    GPIO.output(26,GPIO.LOW)
    time.sleep(0.5)
    GPIO.output(13,GPIO.LOW)
    GPIO.output(19,GPIO.HIGH)
    GPIO.output(26,GPIO.LOW)
    time.sleep(0.5)