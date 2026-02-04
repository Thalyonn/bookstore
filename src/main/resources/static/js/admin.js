document.getElementById("addBookForm").addEventListener("submit", async function(event) {
    event.preventDefault();
    const formData = new FormData(this);
    const payload = Object.fromEntries(formData.entries());

    const response = await fetch("/api/admin/addBook", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload),
        credentials: "include"
    });

    if (response.ok) {
        document.getElementById("message").innerText = "Book added successfully.";
        alert("Book added.");
        this.reset();
    } else {
        document.getElementById("message").innerText = "Failed to add book.";
        alert("Book failed to add.");
    }
});

document.getElementById("registerAdminForm").addEventListener("submit", async function(event) {
    event.preventDefault();
    const formData = new FormData(this);
    const payload = Object.fromEntries(formData.entries());

    const response = await fetch("/api/users/registerAdmin", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload),
        credentials: "include"
    });

    if (response.ok) {
        document.getElementById("message").innerText = "Admin registered successfully.";
        alert("Admin Registered successfully.");
        this.reset();
    } else {
        document.getElementById("message").innerText = "Failed to register a new admin.";
        alert("Admin Registration failed.");

    }
});
