import asyncio
import websockets


class WebSocketServer:
    def __init__(self, host="localhost", port=8000):
        self.host = host
        self.port = port
        self.paths = {}  # Dictionary to store path handlers

    def register_path(self, path, handler):
        """Registers a coroutine handler for a specific WebSocket path"""
        self.paths[path] = handler

    async def start(self):
        start_server = websockets.serve(self.handler, self.host, self.port)
        await start_server
        await asyncio.Future()  # Run forever

    async def handler(self, websocket, path):
        """Handles incoming WebSocket connections"""
        if path in self.paths:
            await self.paths[path](websocket, path)
        else:
            print(f"Unknown Path: {path}")


def main():
    server = WebSocketServer()

    # Register your LED path handler
    async def led_handler(websocket, path):
        async for message in websocket:
            _, value = message.split(": ")
            value = int(value.strip())
            print(f"LED value: {value}")
            if value > 75:
                await websocket.send("LED_ON")
            else:
                await websocket.send("LED_OFF")

    server.register_path("/led", led_handler)

    loop = asyncio.new_event_loop()
    asyncio.set_event_loop(loop)
    loop.run_until_complete(server.start())


if __name__ == "__main__":
    main()