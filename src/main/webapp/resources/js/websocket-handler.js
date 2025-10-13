const wsUrl = "ws://" + location.host + "/warehouse-automation/asyncServer";
window.websocket = null;

    function connectWebSocket() {
        websocket = new WebSocket(wsUrl);

        websocket.onopen = function() {
            console.log("WebSocket Connected to " + wsUrl);
        };

        websocket.onmessage = function(event) {
            const jsonMessage = event.data;
            console.log("Received JSON: " + jsonMessage);

            try {
                const message = JSON.parse(jsonMessage);
               switch (message.type) {
                   case 'ITEM_ADDED':
                       handleItemAdded();
                       break;
                   case 'VIEWING_UPDATE':
                       handleViewingUpdate(message.data);
                       break;
                   // Add more cases for other features (e.g., ITEM_DELETED, PRICE_CHANGED)
                   default:
                       console.warn("Unknown message type:", message.type);
               }
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
     function handleItemAdded() {
            const refreshCommand = "PrimeFaces.ab({s:'ws-push',u:'itemsTable'});";
            eval(refreshCommand);
     }

connectWebSocket();