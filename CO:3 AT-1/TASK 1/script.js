const studentForm = document.getElementById("studentForm");
const profileContainer = document.getElementById("profileContainer");
const formMessage = document.getElementById("formMessage");

studentForm.addEventListener("submit", function (event) {
    event.preventDefault();

    const formData = new FormData(studentForm);
    const name = formData.get("name").trim();
    const registerNumber = formData.get("registerNumber").trim();
    const department = formData.get("department").trim();
    const year = formData.get("year");

    if (!name || !registerNumber || !department || !year) {
        showMessage("Please complete all fields before displaying the profile.", "error");
        return;
    }

    // Generate Student Initials for Avatar
    const nameParts = name.split(/\s+/).filter(part => part.length > 0);
    let initials = "ST";
    if (nameParts.length > 0) {
        if (nameParts.length > 1) {
            initials = (nameParts[0][0] + nameParts[nameParts.length - 1][0]).toUpperCase();
        } else {
            initials = nameParts[0].substring(0, 2).toUpperCase();
        }
    }

    const profile = document.createElement("article");
    profile.classList.add("student-profile");

    // Define internal structure for Student Smart Card
    profile.innerHTML = `
        <div class="smart-card-header">
            <div class="smart-card-logo">
                <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5" stroke-linecap="round" stroke-linejoin="round"><path d="M21.42 10.922a1 1 0 0 0-.019-1.838L12.83 5.18a2 2 0 0 0-1.66 0L2.6 9.08a1 1 0 0 0 0 1.832l8.57 3.908a2 2 0 0 0 1.66 0z"/><path d="M6 12v5c0 2 2 3 6 3s6-1 6-3v-5"/><path d="M21.5 12H16c-.5 0-1 .5-1 1v2c0 .5.5 1 1 1h5.5"/></svg>
                <span>ACADEMIA ID</span>
            </div>
            <div class="smart-card-chip"></div>
        </div>
        <div class="smart-card-body">
            <div class="smart-card-avatar-wrapper">
                <div class="smart-card-avatar">${escapeHTML(initials)}</div>
                <span class="smart-card-badge">MEMBER</span>
            </div>
            <div class="smart-card-info">
                <h3 class="smart-card-name">${escapeHTML(name)}</h3>
                <div class="smart-card-field">
                    <span class="field-label">REGISTER NUMBER</span>
                    <span class="field-value">${escapeHTML(registerNumber)}</span>
                </div>
                <div class="smart-card-field">
                    <span class="field-label">DEPARTMENT</span>
                    <span class="field-value">${escapeHTML(department)}</span>
                </div>
                <div class="smart-card-field">
                    <span class="field-label">YEAR OF STUDY</span>
                    <span class="field-value">${escapeHTML(year)}</span>
                </div>
            </div>
        </div>
        <div class="smart-card-footer">
            <div class="smart-card-barcode">
                <span></span><span></span><span></span><span></span><span></span>
                <span></span><span></span><span></span><span></span><span></span>
                <span></span><span></span><span></span><span></span><span></span>
                <span></span><span></span><span></span><span></span><span></span>
            </div>
        </div>
    `;

    // Create the interactive Remove button and append it to card footer
    const removeButton = document.createElement("button");
    removeButton.type = "button";
    removeButton.classList.add("remove-button");
    removeButton.textContent = "Remove Profile";
    removeButton.addEventListener("click", function () {
        profileContainer.replaceChildren(createEmptyMessage());
        showMessage("Profile removed.", "success");
    });

    profile.querySelector(".smart-card-footer").appendChild(removeButton);

    profileContainer.replaceChildren(profile);
    showMessage("Profile displayed successfully.", "success");
});

studentForm.addEventListener("reset", function () {
    profileContainer.replaceChildren(createEmptyMessage());
    showMessage("Form cleared.", "success");
});

function showMessage(text, type) {
    formMessage.textContent = text;
    formMessage.className = "form-message"; // Reset classes
    if (type) {
        formMessage.classList.add(type);
    }
}

function createEmptyMessage() {
    const message = document.createElement("p");
    message.classList.add("empty-message");
    message.textContent = "Your profile will appear here.";
    return message;
}

function escapeHTML(str) {
    return str.replace(/[&<>'"]/g, 
        tag => ({
            '&': '&amp;',
            '<': '&lt;',
            '>': '&gt;',
            "'": '&#39;',
            '"': '&quot;'
        }[tag] || tag)
    );
}
