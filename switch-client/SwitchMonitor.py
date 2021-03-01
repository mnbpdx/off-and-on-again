import requests
from gpiozero import Button

def main():
    print("Monitoring Beaver Tail Switch...")

    beaverTail = Button(2)

    while True:
        printValues()
        beaverTail.wait_for_press()
        print("Beaver tail switched on!")
        printValues()
        beaverTail.wait_for_release()
        print("Beaver tail switched off!")

def htmlTest():
    payload = {'name': 'ACoolSwitch', 'status': '1', 'countFlips': '8'}
    r = requests.post('http://localhost:5000/switches', json=payload)
    print(r.text)

def printValues():
    print("Current Values of beaverTail object:")
    print("is_held: " + str(beaverTail.is_held))
    print("is_pressed: " + str(beaverTail.is_held))


htmlTest()
#main()
