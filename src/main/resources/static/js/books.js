async function loadBooks() {
    const response = await fetch("/api/books");
    const books = await response.json();

    const container = document.getElementById("books");
    container.innerHTML = "";

    books.forEach(book => {
        const card = document.createElement("div");
        card.className = "book-card";
        card.innerHTML = `
            <h3>${book.title}</h3>
            <p>Author: ${book.author}</p>
            <p>Price: ${book.price} pesos</p>
            ${loggedIn ? `<button onclick="orderBook(${book.bookId})">Order</button>` : ""}
        `;
        container.appendChild(card);
    });
}

async function orderBook(bookId) {
    const response = await fetch(`/api/cart/add?bookId=${bookId}&quantity=1`, {
        method: "POST",
        credentials: "include"
    });
    if (response.ok) {
        alert("Book added to cart!");
    } else {
        alert("Failed to add book to cart.");
    }
}
