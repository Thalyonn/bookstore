// //check and get form submission to redirect after login
// document.getElementById("loginForm").addEventListener("submit", function(event) {
//     event.preventDefault();
//     const formData = new FormData(this);
//
//     fetch("/login", {
//         method: "POST",
//         body: formData,
//         credentials: "include" //keep the session cookie
//     }).then(response => {
//         if (response.ok) {
//             window.location.href = "/index.html";
//         } else {
//             document.getElementById("message").innerText = "Login failed!";
//         }
//     });
// });