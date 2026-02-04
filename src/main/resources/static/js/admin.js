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
    } else {
        document.getElementById("message").innerText = "Failed to add book.";
    }
});

document.getElementById("registerAdminForm").addEventListener("submit", async function(event) {
    event.preventDefault();
    const formData = new FormData(this);
    const payload = Object.fromEntries(formData.entries());

    const response = await fetch("/api/admin/registerAdmin", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload),
        credentials: "include"
    });

    if (response.ok) {
        document.getElementById("message").innerText = "Admin registered successfully.";
    } else {
        document.getElementById("message").innerText = "Failed to register a new admin.";
    }
});
