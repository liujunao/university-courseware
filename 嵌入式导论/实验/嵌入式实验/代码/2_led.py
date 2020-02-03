import RPi.GPIO as GPIO

BtnPin = 13
Gpin = 19
Rpin = 26

GPIO.setwarnings(False)

def setup():
    GPIO.setmode(GPIO.BCM)
    GPIO.setup(Gpin,GPIO.OUT)
    GPIO.setup(Rpin,GPIO.OUT)
    GPIO.setup(BtnPin,GPIO.IN,pull_up_down=GPIO.PUD_UP)
    GPIO.add_event_detect(BtnPin,GPIO.BOTH,callback=detect,bouncetime=200)
    
def Led(x):
    if x == 0:
        GPIO.output(Rpin,1)
        GPIO.output(Gpin,0)
    if x == 1:
        GPIO.output(Rpin,0)
        GPIO.output(Gpin,1)
        
def Print(x):
    if x == 0:
        print('****************************')
        print('****   Button Pressed   ****')
        print('****************************')

def detect(chn):
    Led(GPIO.input(BtnPin))
    Print(GPIO.input(BtnPin))
    
def loop():
    while True:
        pass
    
    
def destory():
    GPIO.output(Gpin,GPIO.HIGH)
    GPIO.output(Rpin,GPIO.HIGH)
    GPIO.cleanup()
    
    
if __name__ == '__main__':
    setup()
    try:
        loop()
    except Keyboardinterrupt:
        destroy()










