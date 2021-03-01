import requests
from gpiozero import Button

def main():
    url = 'http://localhost:5000/switches' 

    print("Monitoring Beaver Tail Switch...")

    beaverTail = Button(2)

    while True:
        printValues()
        waitForSwitchOn(url)
        printValues()
        waitForSwitchOff(url)

def printValues():
    print("Current Values of beaverTail object:")
    print("is_held: " + str(beaverTail.is_held))
    print("is_pressed: " + str(beaverTail.is_pressed))
    print("held_time: " + str(beaverTail.held_time))
    print("hold_repeat: " + str(beaverTail.hold_repeat))
    print("hold_time: " + str(beaverTail.hold_time))
    print("is_pressed: " + str(beaverTail.is_held))
    print("is_pressed: " + str(beaverTail.is_held))
    print("is_pressed: " + str(beaverTail.is_held))
    print("is_pressed: " + str(beaverTail.is_held))


def waitForSwitchOn(url):
    beaverTail.wait_for_press()
    r = requests.patch(url, json=payload)
    print("Beaver Tail switched on!")
    
def waitForSwitchOff(url):
    beaverTail.wait_for_release()
    r = requests.patch(url, json=payload)
    print("Beaver Tail switched off!")

main()


#def htmlTest():
#    payload = {'name': 'A New Switch', 'status': '1', 'countFlips': '9001'}
#    r = requests.post(url, json=payload)
#    print(r.text)

