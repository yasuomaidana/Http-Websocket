import asyncio
import json
from dataclasses import asdict
from typing import Callable, Coroutine, Dict

import websockets

from DTO.answerDTO import AnswerDTO
from DTO.errorDTO import WebSocketError
from DTO.led_data import LedData

Handler = Callable[[websockets.WebSocketServerProtocol, str], Coroutine]  # Type alias for handlers


class WebSocketServer:
    def __init__(self, host: str = "localhost", port: int = 8000):
        self.host = host
        self.port = port
        self.routes: Dict[str, Handler] = {}  # Dictionary to store path handlers
        self.connected_clients = set()  # Track connected clients
        self.valid_tokens = {"mock_token123", "another_mock_token"}  # Set of valid tokens

    def route(self, path: str) -> Callable[[Handler], Handler]:
        """Decorator to register a path handler"""
        def register_handler(handler: Handler) -> Handler:
            """Registers a coroutine handler for a specific WebSocket path"""
            self.routes[path] = handler
            return handler
        return register_handler

    async def start(self):
        start_server = websockets.serve(self.handler, self.host, self.port)
        await start_server
        await asyncio.Future()  # Run forever

    async def handler(self, websocket: websockets.WebSocketServerProtocol, path: str):

        # Authentication Check
        if "token" in websocket.request_headers:
            token = websocket.request_headers["token"]
            if token in self.valid_tokens:
                print(f"Client authenticated: {websocket}")
            else:
                print(f"Authentication failed for: {websocket}")
                await websocket.close(code=4001, reason="Invalid Token")
                return  # Close the connection on failure
        else:
            print("Missing token in headers")
            await websocket.close(code=4001, reason="Missing Token")
            return  # Close the connection on failure

        """ Handles incoming WebSocket connections """
        self.connected_clients.add(websocket)  # Add to connected clients

        print(f"Client connected: {websocket}")
        """Handles incoming WebSocket connections"""
        try:
            if path in self.routes:
                await self.routes[path](websocket, path)
            else:
                print(f"Unknown Path: {path}")
        finally:
            self.connected_clients.remove(websocket)
            print(f"Client disconnected: {websocket}")


def main():
    server = WebSocketServer()

    # Register your LED path handler
    @server.route("/led")
    async def led_handler(websocket: websockets.WebSocketServerProtocol, _):
        async for message in websocket:
            try:
                data = json.loads(message)
                led_data = LedData(**data)
                value = led_data.slider
                print(f"LED value: {value}")
                await websocket.send(json.dumps(asdict(AnswerDTO("OK", "LED_ON" if value > 75 else "LED_OFF"))))
            except (json.JSONDecodeError, TypeError) as e:
                error = WebSocketError("Invalid message format")
                await websocket.send(json.dumps(error.to_dict()))

    loop = asyncio.new_event_loop()
    asyncio.set_event_loop(loop)
    loop.run_until_complete(server.start())


if __name__ == "__main__":
    main()
