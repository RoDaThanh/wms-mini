window.addEventListener("load", function () {
    function sendViewingMessage() {
        if (window.websocket && window.websocket.readyState === WebSocket.OPEN) {
            const viewingMessage = JSON.stringify({
                type: "START_VIEWING",
                data: { itemId: currentItemId }
            });
            window.websocket.send(viewingMessage);
        } else {
            // Retry every 500ms until connection is ready
            setTimeout(sendViewingMessage, 500);
        }
    }

    sendViewingMessage();

    // Notify the server when user leaves or closes tab
    window.addEventListener("beforeunload", function () {
        if (window.websocket && window.websocket.readyState === WebSocket.OPEN) {
            const stopViewingMessage = JSON.stringify({
                type: "STOP_VIEWING",
                data: { itemId: currentItemId }
            });
            window.websocket.send(stopViewingMessage);
        }
    });
});

function escapeHtml(str) {
  return str.replace(/[&<>'"]/g, tag => ({
    '&': '&amp;', '<': '&lt;', '>': '&gt;', "'": '&#39;', '"': '&quot;'
  }[tag]));
}

window.handleViewingUpdate = function (data) {
    const viewerCountElement = document.getElementById("viewerCount");

    if (!viewerCountElement) {
        console.log(`Received silent VIEWING_UPDATE for Item ${data.itemId}.`);
        return;
    }

    if (data.itemId !== currentItemId) return;

    if (Array.isArray(data.viewers) && data.viewers.length > 0) {
        const badges = data.viewers.map(v => `
            <span class="viewer-badge" style="background-color: ${v.color}">
              ${escapeHtml(v.username)}
            </span>
          `);

        const suffix = data.viewers.length > 1 ? "are viewing this item." : "is viewing this item.";
        viewerCountElement.innerHTML = badges.join(" ") + " " + suffix;
    } else {
        viewerCountElement.textContent = "";
    }
};

