// Check login status
fetch("/api/admin/check")
.then(res => {
    if(res.status === 401){
        window.location.href = "/admin/login.html";
    }
});

// Fetch quotations and render table
function loadQuotations(){
    fetch("/api/admin/quotations")
    .then(res => res.json())
    .then(data => {
        const tbody = document.querySelector("#quotationTable tbody");
        tbody.innerHTML = "";

        data.forEach(q => {
            const row = document.createElement("tr");
            row.innerHTML = `
                <td>${q.id}</td>
                <td>${q.propertyType}</td>
                <td>${q.city}</td>
                <td>${q.unitType}</td>
                <td>${q.name}</td>
                <td>${q.mobile}</td>
                <td>${q.email}</td>
                <td>${new Date(q.createdAt).toLocaleString()}</td>
            `;
            tbody.appendChild(row);
        });
    });
}

// Logout
document.getElementById("logoutBtn").addEventListener("click", function(){
    fetch("/api/admin/logout", { method: "POST" })
    .then(res => window.location.href = "/admin/login.html");
});

loadQuotations();
