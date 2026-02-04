async function loadOrders() {
    const response = await fetch("/api/orders", { credentials: "include" });
    if (!response.ok) {
        document.getElementById("order-header").innerText = "Need to log in to view your orders.";
        return;
    }
    const orders = await response.json();

    const container = document.getElementById("orders");
    container.innerHTML = "";

    orders.forEach(order => {
        const orderDiv = document.createElement("div");
        orderDiv.className = "order";

        const header = document.createElement("div");
        header.className = "order-header";
        header.innerHTML = `
            <h3>Order ID#${order.orderId}</h3>
            <p><strong>Date:</strong> ${new Date(order.orderDate).toLocaleString()}</p>
            <p><strong>Status:</strong> ${order.status}</p>
            <p><strong>Total:</strong> ₱${order.totalAmount.toFixed(2)}</p>
        `;
        orderDiv.appendChild(header);
        const itemsGrid = document.createElement("div");
        itemsGrid.className = "items-grid";
        order.items.forEach(item => {
            const itemDiv = document.createElement("div");
            itemDiv.className = "order-item";
            itemDiv.innerHTML = `
                <h4>${item.title}</h4>
                <p><em>${item.author}</em></p>
                <p>Quantity: ${item.quantity}</p>
                <p>Unit Price: ₱${item.unitPrice.toFixed(2)}</p>
                <p>Subtotal: ₱${(item.quantity * item.unitPrice).toFixed(2)}</p>
            `;
            itemsGrid.appendChild(itemDiv);
        });

        orderDiv.appendChild(itemsGrid);
        container.appendChild(orderDiv);
    });
}
