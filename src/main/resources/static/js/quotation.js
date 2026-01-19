document.addEventListener("DOMContentLoaded", () => {

    /* ===============================
       ELEMENTS
    ================================ */
    const steps = document.querySelectorAll(".step");
    const indicators = document.querySelectorAll(".step-indicator");

    const propertyTypeInput = document.getElementById("propertyType");
    const unitTypeInput = document.getElementById("unitType");
    const cityInput = document.getElementById("city");

    const resSection = document.getElementById("resSection");
    const commSection = document.getElementById("commSection");

    const nextBtn = document.querySelector(".next-btn");
    const backBtn = document.querySelector(".back-btn");

    const propertyError = document.getElementById("propertyError");
    const cityError = document.getElementById("cityError");
    const unitError = document.getElementById("unitError");

    /* ===============================
       STEP CONTROL (FIXED SCOPE)
    ================================ */
    function showStep(index) {
        steps.forEach((step, i) => {
            step.classList.toggle("active", i === index);
            indicators[i].classList.toggle("active", i <= index);
        });
    }

    /* ===============================
       PROPERTY TYPE
    ================================ */
    document.querySelectorAll(".option").forEach(btn => {
        btn.addEventListener("click", () => {

            document.querySelectorAll(".option").forEach(b => b.classList.remove("selected"));
            btn.classList.add("selected");

            propertyTypeInput.value = btn.dataset.value;
            propertyError.style.display = "none";

            unitTypeInput.value = "";
            nextBtn.disabled = true;
            unitError.style.display = "none";

            document.querySelectorAll(".unit-btn").forEach(b => b.classList.remove("selected"));
            document.querySelectorAll(".otherUnitWrapper").forEach(w => w.style.display = "none");

            if (btn.dataset.value === "RESIDENTIAL") {
                resSection.style.display = "block";
                commSection.style.display = "none";
            } else {
                resSection.style.display = "none";
                commSection.style.display = "block";
            }
        });
    });

    /* ===============================
       UNIT TYPE
    ================================ */
    document.querySelectorAll(".unit-btn").forEach(btn => {
        btn.addEventListener("click", () => {

            document.querySelectorAll(".unit-btn").forEach(b => b.classList.remove("selected"));
            btn.classList.add("selected");

            document.querySelectorAll(".otherUnitWrapper").forEach(wrapper => {
                wrapper.style.display = "none";
                const input = wrapper.querySelector(".otherUnitInput");
                if (input) input.value = "";
            });

            if (btn.dataset.value === "Other") {
                const section = btn.closest(".unit-section");
                const wrapper = section.querySelector(".otherUnitWrapper");
                wrapper.style.display = "block";
                unitTypeInput.value = "";
                nextBtn.disabled = true;
            } else {
                unitTypeInput.value = btn.dataset.value;
                nextBtn.disabled = false;
            }

            unitError.style.display = "none";
        });
    });

    document.querySelectorAll(".otherUnitInput").forEach(input => {
        input.addEventListener("input", () => {
            unitTypeInput.value = input.value.trim();
            nextBtn.disabled = !unitTypeInput.value;
        });
    });

    /* ===============================
       CITY DROPDOWN
    ================================ */
    const citySearch = document.getElementById("citySearch");
    const cityList = document.getElementById("cityList");

    function updateCity(value) {
        cityInput.value = value.trim();
        if (cityInput.value) cityError.style.display = "none";
    }

    citySearch.addEventListener("input", e => {
        const value = e.target.value.replace(/[^a-zA-Z\s]/g, "");
        citySearch.value = value;
        updateCity(value);

        cityList.classList.add("show");
        cityList.querySelectorAll("li").forEach(li => {
            li.style.display = li.textContent.toLowerCase().includes(value.toLowerCase())
                ? "block" : "none";
        });
    });

    cityList.querySelectorAll("li").forEach(li => {
        li.addEventListener("click", () => {
            citySearch.value = li.textContent;
            updateCity(li.dataset.city);
            cityList.classList.remove("show");
        });
    });

    document.addEventListener("click", e => {
        if (!e.target.closest(".custom-dropdown")) {
            cityList.classList.remove("show");
        }
    });

    /* ===============================
       NEXT STEP VALIDATION
    ================================ */
    nextBtn.addEventListener("click", () => {
        let valid = true;

        if (!propertyTypeInput.value) {
            propertyError.style.display = "block";
            valid = false;
        }

        if (!cityInput.value.trim()) {
            cityError.style.display = "block";
            valid = false;
        }

        if (!unitTypeInput.value.trim()) {
            unitError.style.display = "block";
            valid = false;
        }

        if (!valid) return;

        showStep(1);
    });

    /* ===============================
       BACK BUTTON (FIXED)
    ================================ */
    backBtn.addEventListener("click", () => {
        showStep(0);
        if (unitTypeInput.value.trim()) {
            nextBtn.disabled = false;
        }
    });

	/* ===============================
	   FORM SUBMIT (FULL VALIDATION)
	================================ */
	document.getElementById("quotationForm").addEventListener("submit", e => {
	    e.preventDefault();

	    const name = document.getElementById("fullName").value.trim();
	    const mobile = document.getElementById("mobile").value.trim();
	    const email = document.getElementById("email").value.trim();

	    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]{2,}$/;
	    const mobileRegex = /^[6-9]\d{9}$/; // Indian format

	    /* ===== BLANK FIELD CHECK ===== */
	    if (
	        !propertyTypeInput.value ||
	        !cityInput.value ||
	        !unitTypeInput.value ||
	        !name ||
	        !mobile ||
	        !email
	    ) {
	        Swal.fire({
	            icon: "warning",
	            title: "Missing Details",
	            text: "Please fill all required fields before submitting.",
	            confirmButtonColor: "#128C7E"
	        });
	        return;
	    }

	    /* ===== EMAIL VALIDATION ===== */
	    if (!emailRegex.test(email)) {
	        Swal.fire({
	            icon: "error",
	            title: "Invalid Email",
	            text: "Please enter a valid email address.",
	            confirmButtonColor: "#128C7E"
	        });
	        return;
	    }

	    /* ===== MOBILE VALIDATION ===== */
	    if (!mobileRegex.test(mobile)) {
	        Swal.fire({
	            icon: "error",
	            title: "Invalid Mobile Number",
	            text: "Please enter a valid 10-digit mobile number.",
	            confirmButtonColor: "#128C7E"
	        });
	        return;
	    }
		
		
		function lockBodyScroll() {
		    const scrollBarWidth = window.innerWidth - document.documentElement.clientWidth;
		    document.body.style.overflow = "hidden";
		    document.body.style.paddingRight = scrollBarWidth + "px";
		}

		function unlockBodyScroll() {
		    document.body.style.overflow = "";
		    document.body.style.paddingRight = "";
		}


	    /* ===== PREVENT FOOTER JUMP ===== */
		lockBodyScroll();

				// Form submission
				document.getElementById("quotationForm").addEventListener("submit", function (e) {
				    e.preventDefault(); // prevent default form submission

				    const payload = {
				        name: document.getElementById("fullName").value,
				        mobile: document.getElementById("mobile").value,
				        email: document.getElementById("email").value,
				        propertyType: document.getElementById("propertyType").value,
				        city: document.getElementById("city").value,
				        unitType: document.getElementById("unitType").value
				    };

				    console.log("FORM SUBMITTED – JS HANDLER WORKING", payload);

					fetch("http://localhost:8080/api/submitQuotation", { 
					    method: "POST",
					    headers: { "Content-Type": "application/json" },
					    body: JSON.stringify(payload)
					})
				    .then(response => {
				        if (!response.ok) {
				            return response.json().then(err => { throw err; });
				        }
				        return response.json();
				    })
				    .then(data => {
				        console.log("Response:", data);
				        Swal.fire({
				            icon: "success",
				            title: "Quotation Submitted 🎉",
				            text: data.message || "Our team will contact you shortly.",
				            confirmButtonColor: "#128C7E"
				        }).then(() => location.reload());
				    })
				    .catch(error => {
				        console.error("Error:", error);
				        Swal.fire({
				            icon: "error",
				            title: "Submission Failed",
				            text: error.message || "Please try again later.",
				            confirmButtonColor: "#128C7E"
				        });
				    });
				});

				// Mobile number input validation
				document.getElementById("mobile").addEventListener("input", function () {
				    // Only digits, max 10
				    this.value = this.value.replace(/\D/g, "").slice(0, 10);
				});
		});
		});