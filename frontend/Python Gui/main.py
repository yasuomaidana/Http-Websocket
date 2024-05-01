import tkinter as tk


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
