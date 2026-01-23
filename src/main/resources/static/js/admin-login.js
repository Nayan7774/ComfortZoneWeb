document.getElementById("adminLoginForm").addEventListener("submit", function(e){
    e.preventDefault();

    const data = {
        username: document.getElementById("username").value,
        password: document.getElementById("password").value
    };

    fetch("/api/admin/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(data)
    })
    .then(res => {
        if(res.ok){
            window.location.href = "/admin/admin.html";
        } else {
            document.getElementById("loginError").style.display = "block";
        }
    })
    .catch(err => console.error(err));
});
