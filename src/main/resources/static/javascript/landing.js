// #1 - Login  form //
const loginForm = document.getElementById('login-form')

const loginUsername = document.getElementById('login-username');
const loginPassword = document.getElementById('login-password');


// #2 - Register form //
const registerForm = document.getElementById('register-form')

const firstName = document.getElementById('register-first-name');
const lastName = document.getElementById('register-last-name');
const email = document.getElementById('register-email');
const username = document.getElementById('register-username');
const password = document.getElementById('register-password')


// #3 - For Modal toggling //
const navLoginSignup = document.getElementById('nav-login-signup');
const modal = document.getElementById('modal');
const modalContent = document.querySelector('.modal-content');
const container = document.getElementById('container');


// #4 - Switch between Register form and Login form //
const switchToRegisterButton = document.getElementById('switch-to-register-button');
const switchToLoginButton = document.getElementById('switch-to-login-button');




// #1 User Login //






// #2 - Register user //
const headers = {
    'Content-Type':'application/json'
};

const baseUrl = 'http://localhost:8080/users';

const handleSubmit = async (e) => {
    e.preventDefault();

    let bodyObj = {
        firstName: firstName.value,
        lastName: lastName.value,
        email: email.value,
        username: username.value,
        password: password.value
    };

    const response = await fetch(`${baseUrl}/register`, {
        method: "POST",
        body: JSON.stringify(bodyObj),
        headers: headers
    })
    .catch(err => console.error(err.message));

    const responseArr = await response.json();

    if (response.status === 200) {
        window.location.replace(responseArr[0]);
    }
};

registerForm.addEventListener("submit", handleSubmit);



// #3 - Modal  is hidden until clicked (Register and Login forms) //
navLoginSignup.addEventListener('click', () => {
  modal.style.display = 'block';
});

document.addEventListener('click', (event) => {
  const isInsideContainer = container.contains(event.target);
  const isNavLoginSignup = event.target === navLoginSignup;
  const isModalVisible = modal.style.display === 'block';

  if (!isInsideContainer && !isNavLoginSignup && isModalVisible) {
    modal.style.display = 'none';
  }
});

modalContent.addEventListener('click', (event) => {
  event.stopPropagation();
});

modal.addEventListener('click', () => {
  modal.style.display = 'none';
});



// #4 - Switch between Register form and Login form //
switchToRegisterButton.addEventListener('click', () => {
	container.classList.add("right-panel-active");
});

switchToLoginButton.addEventListener('click', () => {
	container.classList.remove("right-panel-active");
});