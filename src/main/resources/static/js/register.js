async function register() {
    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    const response = await fetch("/api/users/register", {
        method: "POST",
        headers: { "Content-Type" : "application/json" },
        body: JSON.stringify({username, password})
    });
    console.log("sending is done")
    if(response.ok) {
        console.log("response is ok")
        const user= await response.json();
        document.getElementById("message").innerText = "Registered succesfully with username " +
            user.username;
        setTimeout(() =>  window.location.href = "login.html", 2000);
    } else if (response.status === 400) {
        console.log("status 400 returned")
        const errorData = await response.text();
        document.getElementById("message").innerText =
            errorData || "Username is already taken.";
    } else {
        document.getElementById("message").innerText =
            "Registration has failed. Please try again.";
    }
}