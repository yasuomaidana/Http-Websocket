import asyncio
import tkinter as tk
import websockets
import asynctkinter as atk


class SliderApp(tk.Tk):
    def __init__(self):
        super().__init__()
        self.title("Slider Example with LED Indicator")
        # self.geometry("350x150")

        # Create a slider
        self.slider = tk.Scale(self, from_=0, to=100, orient=tk.HORIZONTAL, length=200)
        self.slider.pack(pady=20)
        # self.slider.grid(row=0, column=0)

        # Create a label to display the slider value
        self.label = tk.Label(self, text="0")
        self.label.pack(pady=10)

        # Create an LED indicator (simulated using a label)
        self.led_indicator = tk.Label(self, bg="black", width=10, height=10)
        self.led_indicator.pack(pady=10)
        # self.led_indicator.grid(row=0, column=1)

        # Set up a callback to update the label and LED indicator when the slider value changes
        self.slider.bind("<B1-Motion>", self.update_elements)

        # WebSocket connection
        self.websocket = None
        self.loop = asyncio.new_event_loop()
        self.loop.call_soon_threadsafe(self.start_websocket_connection)  # Start connection attempt on startup


    def start_websocket_connection(self):
        # Schedule the execution of the connect_websocket coroutine
        self.loop.create_task(self.connect_websocket())

    async def connect_websocket(self):
        while True:  # Attempt connection and reconnection if needed
            try:
                async with websockets.connect("ws://localhost:8000/led") as self.websocket:
                    await self.websocket_handler()
            except ConnectionRefusedError:
                print("Server not ready, trying again in 5 seconds...")
                await asyncio.sleep(5)
            except Exception as e:
                print(f"WebSocket error: {e}")
                await asyncio.sleep(5)  # Wait before retrying

    async def websocket_handler(self):
        print("WebSocket connected.")
        async for message in self.websocket:
            if message == "LED_ON":
                self.led_indicator.configure(bg="red", text=" ")
            elif message == "LED_OFF":
                self.led_indicator.configure(bg="black", text=" ")
            else:
                print(f"Unknown message: {message}")

        # Send slider value on change if websocket is open
        self.slider.bind("<B1-Motion>", self.send_slider_value)

    async def send_slider_value(self, event):
        if self.websocket:  # Only send if connected
            value = self.slider.get()
            await self.websocket.send(f"Slider: {value}")

    def update_elements(self, event):
        value = self.slider.get()
        self.label.configure(text=str(value))
        # Update LED indicator based on slider value
        if value > 50:
            self.led_indicator.configure(bg="red", text=" ")  # Turn on the LED (red color)
        else:
            self.led_indicator.configure(bg="black", text=" ")  # Turn off the LED (black color)


def main():
    app = SliderApp()
    app.mainloop()


if __name__ == "__main__":
    main()
