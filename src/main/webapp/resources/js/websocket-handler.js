const wsUrl = "ws://" + location.host + "/warehouse-automation/asyncServer";
let websocket;

    function connectWebSocket() {
        websocket = new WebSocket(wsUrl);

        websocket.onopen = function() {
            console.log("WebSocket Connected to " + wsUrl);
        };

        websocket.onmessage = function(event) {
            const command = event.data;
            console.log("Received command: " + command);

            try {
                eval(command);
            } catch (e) {
                console.error("Error executing command: " + e);
            }
        };

        websocket.onclose = function() {
            console.log("WebSocket Disconnected. Attempting to reconnect in 5s...");
            setTimeout(connectWebSocket, 5000);
        };

        websocket.onerror = function(error) {
            console.error("WebSocket Error: " + error);
        };

    }

connectWebSocket();