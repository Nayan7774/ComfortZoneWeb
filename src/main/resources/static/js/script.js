document.addEventListener("DOMContentLoaded", () => {

    const THEME_COLOR = "#28a745"; // Global theme color

    // -------------------------
    // 1. HELPERS & VALIDATION
    // -------------------------
    const isValidPhone = (phone) => /^[6-9]\d{9}$/.test(phone);
    const isValidEmail = (email) => /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);

    /**
     * Reusable function to handle API calls and SweetAlerts
     * @param {Object} data - The form data object
     * @param {string} endpoint - The API route
     * @param {HTMLFormElement} formElement - The form to reset
     * @param {HTMLElement} popupElement - Optional popup to close
     */
	
	// new updated js submit form data
	async function submitFormData(data, endpoint, formElement, popupElement = null) {
	    try {
	        const response = await fetch(endpoint, {
	            method: "POST",
	            headers: { "Content-Type": "application/json" },
	            body: JSON.stringify(data)
	        });

	        if (!response.ok) {
	            const errText = await response.text();
	            throw new Error(errText || `HTTP ${response.status}`);
	        }

	        if (popupElement) popupElement.style.display = "none";

	        Swal.fire({
	            title: "Success!",
	            text: "Thank you! We will contact you soon.",
	            icon: "success",
	            confirmButtonColor: THEME_COLOR
	        });

	        formElement.reset();

	    } catch (error) {
	        console.error("Form submit failed:", error);

	        if (popupElement) popupElement.style.display = "none";

	        Swal.fire({
	            icon: "error",
	            title: "Server Error",
	            text: "Something went wrong. Please try again later.",
	            confirmButtonColor: THEME_COLOR
	        });
	    }
	}

	
	
   /*
   	old js 
   async function submitFormData(data, endpoint, formElement, popupElement = null) {
        try {
            await fetch(endpoint, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(data)
            });

            if (popupElement) popupElement.style.display = "none";

            Swal.fire({
                title: "Success!",
                text: "Thank you! We will contact you soon.",
                icon: "success",
                confirmButtonColor: THEME_COLOR,
                background: "#f0fff0",
                iconColor: THEME_COLOR
            });

            formElement.reset();

        } catch (error) {
            if (popupElement) popupElement.style.display = "none";
            
            Swal.fire({
                icon: "error",
                title: "Server Error",
                text: "Something went wrong. Please try again later.",
                confirmButtonColor: THEME_COLOR
            });
        }
    } 
*/
    // ---------------------------------------------------
    // 2. HERO FORM SUBMISSION
    // ---------------------------------------------------
    const heroForm = document.getElementById("heroForm");
    if (heroForm) {
        heroForm.addEventListener("submit", (e) => {
            e.preventDefault();
            const name = document.getElementById("heroName").value.trim();
            const contact = document.getElementById("heroContact").value.trim();

            if (!name || !contact) {
                return Swal.fire({ icon: "warning", title: "Incomplete", text: "Fill all fields.", confirmButtonColor: THEME_COLOR });
            }
            if (!isValidPhone(contact)) {
                return Swal.fire({ icon: "error", title: "Invalid Phone", text: "Enter a valid Indian phone number.", confirmButtonColor: THEME_COLOR });
            }

            submitFormData({ name, contact }, "/api/submitHero", heroForm);
        });
    }

    // ---------------------------------------------------
    // 3. CONTACT FORM SUBMISSION
    // ---------------------------------------------------
    const contactForm = document.getElementById("contactForm");
    if (contactForm) {
        contactForm.addEventListener("submit", (e) => {
            e.preventDefault();
            const name = document.getElementById("contactName").value.trim();
            const phone = document.getElementById("contactPhone").value.trim();
            const email = document.getElementById("contactEmail").value.trim();
            const message = document.getElementById("contactMessage").value.trim();

            if (!name || !phone || !email || !message) {
                return Swal.fire({ icon: "warning", title: "Incomplete", text: "Fill all fields.", confirmButtonColor: THEME_COLOR });
            }
            if (!isValidPhone(phone)) {
                return Swal.fire({ icon: "error", title: "Invalid Phone", text: "Enter a valid number.", confirmButtonColor: THEME_COLOR });
            }
            if (!isValidEmail(email)) {
                return Swal.fire({ icon: "error", title: "Invalid Email", text: "Enter a valid email address.", confirmButtonColor: THEME_COLOR });
            }

            submitFormData({ name, phone, email, message }, "/api/contact", contactForm);
        });
    }

    // ---------------------------------------------------
    // 4. POPUP FORM SUBMISSION
    // ---------------------------------------------------
    const popupForm = document.getElementById("popupForm");
    const popupWrapper = document.getElementById("scrollPopup");

    if (popupForm) {
        popupForm.addEventListener("submit", (e) => {
            e.preventDefault();
            const name = document.getElementById("popupName").value.trim();
            const contact = document.getElementById("popupContact").value.trim();

            if (!name || !contact) {
                if (popupWrapper) popupWrapper.style.display = "none";
                return Swal.fire({ icon: "warning", title: "Incomplete", text: "Fill all fields.", confirmButtonColor: THEME_COLOR });
            }
            if (!isValidPhone(contact)) {
                if (popupWrapper) popupWrapper.style.display = "none";
                return Swal.fire({ icon: "error", title: "Invalid Phone", text: "Enter a valid number.", confirmButtonColor: THEME_COLOR });
            }

            submitFormData({ name, contact }, "/api/popupSubmit", popupForm, popupWrapper);
        });
    }

    // ---------------------------------------------------
    // 5. POPUP SHOW/HIDE LOGIC
    // ---------------------------------------------------
    const closePopup = document.getElementById("popupCloseBtn");
    let popupShown = false;

    function showPopup() {
        if (!popupShown && popupWrapper) {
            popupWrapper.style.display = "flex";
            popupShown = true;
        }
    }

    // Show after 6 seconds
    setTimeout(showPopup, 6000);

    // Show after scrolling 50%
    window.addEventListener("scroll", () => {
        if (popupShown) return;
        const scrollPosition = window.scrollY + window.innerHeight;
        const pageHeight = document.documentElement.scrollHeight;
        if (scrollPosition >= pageHeight * 0.5) showPopup();
    });

    // Close buttons/click outside
    if (closePopup) {
        closePopup.addEventListener("click", () => { popupWrapper.style.display = "none"; });
    }
    if (popupWrapper) {
        popupWrapper.addEventListener("click", (e) => {
            if (e.target === popupWrapper) popupWrapper.style.display = "none";
        });
    }
	
	/*            whatsapp-btn       */
	
	document.querySelector('.whatsapp-btn').addEventListener('click', function() {
	    document.querySelector('#heroForm h2').innerText = "WhatsApp Support";
	    document.querySelector('#heroForm p').innerText = "Enter your details to start a chat with our team.";	
	});
	
	/* */
	
	/*document.querySelector('btn-outline').addEventListener('click', function() {
	    document.querySelector('#services');
	   // document.querySelector('#heroForm p');	
	});
	*/
	
	/* ===========================================

	NEW: TRUST COUNTER SECTION

	=========================================== */
	
	document.addEventListener("DOMContentLoaded", () => {

	    console.log("Counter logic running");

	    const counters = document.querySelectorAll(".count");

	    counters.forEach(counter => {
	        const target = +counter.dataset.target;
	        let current = 0;

	        const step = Math.max(1, Math.floor(target / 50));

	        const interval = setInterval(() => {
	            current += step;

	            if (current >= target) {
	                counter.textContent = target;
	                clearInterval(interval);
	            } else {
	                counter.textContent = current;
	            }
	        }, 30);
	    });

	});


	});
	
/* ===========================================
   NEW: TRUST COUNTER SECTION (FINAL FIX)
=========================================== */

const counters = document.querySelectorAll(".count");
let counterStarted = false;
const DURATION = 2000; // all counters finish in 2 seconds

function easeOutCubic(t) {
    return 1 - Math.pow(1 - t, 3);
}

function animateCounter(counter) {
    const target = parseInt(counter.dataset.target, 10);
    let startTime = null;

    function update(timestamp) {
        if (!startTime) startTime = timestamp;

        const elapsed = timestamp - startTime;
        const progress = Math.min(elapsed / DURATION, 1);
        const eased = easeOutCubic(progress);

        const value = Math.floor(eased * target);
        counter.textContent = value;

        if (progress < 1) {
            requestAnimationFrame(update);
        } else {
            counter.textContent = target;
        }
    }

    requestAnimationFrame(update);
}

/* Scroll-based trigger */
window.addEventListener("scroll", () => {
    if (counterStarted) return;

    const section = document.querySelector(".trust-counter");
    if (!section) return;

    const rect = section.getBoundingClientRect();
    const triggerPoint = window.innerHeight * 0.75;

    if (rect.top < triggerPoint) {
        counterStarted = true;

        counters.forEach(counter => {
            counter.textContent = "0";
            animateCounter(counter);
        });
    }
	
	/*    Review   */ 
	const track = document.querySelector(".review-track");

	   // Duplicate reviews for seamless scroll
	   track.innerHTML += track.innerHTML;

	   let position = 0;
	   const speed = 0.8; // adjust speed here
	   let paused = false;

	   // Pause on hover
	   track.addEventListener("mouseenter", () => paused = true);
	   track.addEventListener("mouseleave", () => paused = false);

	   function animateReviews() {
	       if (!paused) {
	           position -= speed;

	           // Reset seamlessly when half the track is scrolled
	           if (Math.abs(position) >= track.scrollWidth / 2) {
	               position = 0;
	           }

	           track.style.transform = `translateX(${position}px)`;
	       }

	       requestAnimationFrame(animateReviews);
	   }

	   animateReviews();
});


// WhatsApp number hidden in JS
// WhatsApp number hidden in JS
const whatsappNumber = "9112006636";   //9529769417

// Select the sticky button
const whatsappLink = document.getElementById("whatsapp-link");

whatsappLink.addEventListener("click", function(e) {
    e.preventDefault();

    Swal.fire({
        title: 'Choose Service',
        text: "Which Interior design service are you interested in?",
        icon: 'question',
        showCancelButton: true,
        confirmButtonText: 'Residential',
        cancelButtonText: 'Commercial',
        reverseButtons: true,
        focusConfirm: false,
        customClass: {
            confirmButton: 'swal-btn-confirm',
            cancelButton: 'swal-btn-cancel'
        }
    }).then((result) => {
        let message = "";

        if (result.isConfirmed) {
            message = "Hi! I’m interested in Residential Interior design services.";
        } else if (result.dismiss === Swal.DismissReason.cancel) {
            message = "Hi! I’m interested in Commercial Interior design services.";
        } else {
            return; // User closed the popup
        }

        // Open WhatsApp with pre-filled message
        const url = `https://api.whatsapp.com/send?phone=${whatsappNumber}&text=${encodeURIComponent(message)}`;
        window.open(url, "_blank");
    });
});



// WhatsApp number
/*
const whatsappNumber = "1144558877";  // hidden in JS
const prefilledMessage = encodeURIComponent("Hi! I want to get a consultation for decorating my home. Can we schedule a call or visit?."); 

// Set the href dynamically

const whatsappLink = document.getElementById("whatsapp-link");
whatsappLink.href = `https://api.whatsapp.com/send?phone=${whatsappNumber}&text=${prefilledMessage}`;

*/