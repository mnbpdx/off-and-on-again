import requests
from gpiozero import Button
from os import system, name

def main():
    url = 'http://localhost:5000/switches' 

    print("Monitoring Beaver Tail Switch...")

    beaverTail = Button(2)

    while True:
        printValues(beaverTail)
        waitForSwitchOn(beaverTail, url)
        printValues(beaverTail)
        waitForSwitchOff(beaverTail, url)

def printValues(switch):
    print("Current Values of beaverTail object:")
    print("------------------------------------")
    print("held_time: " + str(switch.held_time))
    print("hold_repeat: " + str(switch.hold_repeat))
    print("hold_time: " + str(switch.hold_time))
    print("is_held: " + str(switch.is_held))
    print("is_pressed: " + str(switch.is_pressed))
    print("pin: " + str(switch.pin))
    print("pull_up: " + str(switch.pull_up))
    print("value: " + str(switch.value))

def waitForSwitchOn(switch, url):
    switch.wait_for_press()
    r = requests.patch(url, json=payload)
    print("Beaver Tail switched on!")
    
def waitForSwitchOff(switch, url):
    switch.wait_for_release()
    r = requests.patch(url, json=payload)
    print("Beaver Tail switched off!")

def objectTest():
    beaverTail = Button(2)

    while True:
        system('clear')
        printValues(beaverTail)
main()
#objectTest()

#def htmlTest():
#    payload = {'name': 'A New Switch', 'status': '1', 'countFlips': '9001'}
#    r = requests.post(url, json=payload)
#    print(r.text)

