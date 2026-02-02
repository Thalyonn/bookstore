let loggedIn = false;

async function setupNav() {
    const response = await fetch("/api/users/me", { credentials: "include" });
    const data = await response.json();

    const nav = document.getElementById("nav");
    nav.innerHTML = "";

    if (data.authenticated === "true") {
        loggedIn = true;
        nav.innerHTML = `
          <span>Hello, ${data.username}</span>
          <a href="#" onclick="logout()">Logout</a>
          <a href="/cart.html">Cart</a>
          <a href="/orders.html">Orders</a>
        `;
    } else {
        loggedIn = false;
        nav.innerHTML = `
          <a href="login.html">Login</a>
          <a href="register.html">Register</a>
        `;
    }

}

async function logout() {
    await fetch("/logout", { method: "POST", credentials: "include" });
    window.location.href = "login.html";
}
