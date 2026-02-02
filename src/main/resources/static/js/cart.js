async function loadCart() {
    const response = await fetch("/api/cart", { credentials: "include" });
    if (!response.ok) {
        document.getElementById("cart").innerText = "Need to log in to view your cart.";
        return;
    }

    const cart = await response.json();
    const container = document.getElementById("cart");
    container.innerHTML = "";

    if (!cart.items || cart.items.length === 0) {
        container.innerHTML = "<p>Your cart is empty.</p>";
        return;
    }

    cart.items.forEach(item => {
        const div = document.createElement("div");
        div.className = "cart-item";
        div.innerHTML = `
            <h3>${item.title}</h3>
            <p>Quantity: ${item.quantity}</p>
            <p>Price: ${item.price} pesos</p>
        `;
        container.appendChild(div);
    });
}

async function checkout() {
    const response = await fetch("/api/cart/checkout", {
        method: "POST",
        credentials: "include"
    });
    if (response.ok) {
        alert("Order placement has been successfull!");
        window.location.href = "index.html";
    } else {
        alert("Checkout failed.");
    }
}
