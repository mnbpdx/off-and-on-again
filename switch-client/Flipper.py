import requests
from gpiozero import Button
from os import system, name
import time

def main():
    url = 'http://localhost:5000/switches'
    switchId = '603c51e024be826bad81e9f2'
    selection = menu()
    message = ""
    
    while (selection != 'q'):
        if (selection == 'a'):
            r = requests.patch(url + '/' + switchId)
            message = "Switch Flipped!"
        else:
            message = "Bad selection, try again"
        print(message)
        time.sleep(1)
        system('clear')
        selection = menu()
    print("Exiting...")

def menu():
    print("------Menu------")
    print("a. Flip Switch")
    print("q. Quit Program\n")
    selection = input("Make a selection: ")   
    print("\n")
    return selection

main()
