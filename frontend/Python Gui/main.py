import tkinter as tk


class SliderApp(tk.Tk):
    def __init__(self):
        super().__init__()
        self.title("Slider Example")
        self.geometry("300x100")

        # Create a slider
        self.slider = tk.Scale(self, from_=0, to=100, orient=tk.HORIZONTAL, length=200)
        self.slider.pack(pady=20)

        # Create a label to display the slider value
        self.label = tk.Label(self, text="0")
        self.label.pack(pady=10)

        # Set up a callback to update the label when the slider value changes
        self.slider.bind("<B1-Motion>", self.update_label)

    def update_label(self, event):
        # Get the current slider value and update the label
        value = self.slider.get()
        self.label.configure(text=str(value))
        print(event)


def main():
    # Create an instance of the application
    app = SliderApp()
    # Start the event loop
    app.mainloop()


if __name__ == "__main__":
    main()
