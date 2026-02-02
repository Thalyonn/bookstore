//used to populate the categorySelect dropdown.
async function loadCategories() {
    //get all categories
    const res = await fetch("/api/categories");
    if (!res.ok) return;
    const categories = await res.json();
    const select = document.getElementById("categorySelect");

    categories.forEach(cat => {
        const option = document.createElement("option");
        option.value = cat.name;
        option.textContent = cat.name;
        select.appendChild(option);
    });
}
/*
loads all books
 */
async function loadBooks() {
    const response = await fetch("/api/books");
    const books = await response.json();
    renderBooks(books);
}
/*
applies filters for title search and category
 */
async function applyFilters() {
    const search = document.getElementById("searchInput").value;
    const category = document.getElementById("categorySelect").value;

    let url = "/api/books/filter";
    const params = [];
    if (search) params.push(`search=${encodeURIComponent(search)}`);
    if (category) params.push(`category=${encodeURIComponent(category)}`);
    if (params.length > 0) url += "?" + params.join("&");

    const response = await fetch(url);
    if (!response.ok) {
        document.getElementById("books").innerHTML = "<p>No books found.</p>";
        return;
    }
    const books = await response.json();
    renderBooks(books);
}
/*
renders books passed into the grid
 */
function renderBooks(books) {
    const container = document.getElementById("books");
    container.innerHTML = "";

    books.forEach(book => {
        const card = document.createElement("div");
        card.className = "book-card";
        card.innerHTML = `
            <h3>${book.title}</h3>
            <p>Author: ${book.author}</p>
            <p>Category: ${book.categoryName}</p>
            <p>Price: ${book.price} pesos</p>
            <p>Stock: ${book.stock}</p>
            ${loggedIn ? `<button onclick="orderBook(${book.bookId})">Order</button>` : ""}
        `;
        container.appendChild(card);
    });
}

/*
add to cart, but not confirm purchase.
 */
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


