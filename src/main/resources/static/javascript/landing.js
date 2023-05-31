// #1 - Login  form //
let loginForm = document.getElementById('login-form');

let loginUsername = document.getElementById('login-username');
let loginPassword = document.getElementById('login-password');


// #2 - Register form //
const registerForm = document.getElementById('register-form');

const firstName = document.getElementById('register-first-name');
const lastName = document.getElementById('register-last-name');
const email = document.getElementById('register-email');
const username = document.getElementById('register-username');
const password = document.getElementById('register-password');


// #3 - For Modal toggling //
const navLoginSignup = document.getElementById('nav-login-signup');
const modal = document.getElementById('modal');
const modalContent = document.querySelector('.modal-content');
const container = document.getElementById('container');


// #4 - Switch between Register form and Login form //
const switchToRegisterButton = document.getElementById('switch-to-register-button');
const switchToLoginButton = document.getElementById('switch-to-login-button');




// #1 and #2 - User Login OR User Registration//
const headers = {
  'Content-Type': 'application/json'
};

const baseUrl = 'http://localhost:8080/users';

const handleSubmit = async (e) => {
  e.preventDefault();

  let bodyObj = {};

  if (e.target === loginForm) {
    bodyObj = {
      username: loginUsername.value,
      password: loginPassword.value
    };
  } else if (e.target === registerForm) {
    bodyObj = {
      firstName: firstName.value,
      lastName: lastName.value,
      email: email.value,
      username: username.value,
      password: password.value
    };
  }

  try {
    const endpoint = e.target === loginForm ? 'login' : 'register';
    const response = await fetch(`${baseUrl}/${endpoint}`, {
      method: 'POST',
      body: JSON.stringify(bodyObj),
      headers: headers
    });

    const responseArr = await response.json();

    if (response.status === 200) {
      document.cookie = `userId=${responseArr[1]}`;
      window.location.replace(responseArr[0]);
    }
  } catch (err) {
    console.error(err.message);
  }
};

loginForm.addEventListener('submit', handleSubmit);
registerForm.addEventListener('submit', handleSubmit);



// #3 - Modal  is hidden until clicked (Register and Login forms) //
const mainSection = document.getElementById('main-section');

navLoginSignup.addEventListener('click', () => {
  modal.style.display = 'block';
  mainSection.style.display = 'none';  // Hide main section

    // Add the Lottie player
    document.getElementById('lottie-modal-container').innerHTML = `
      <script src="https://unpkg.com/@lottiefiles/lottie-player@latest/dist/lottie-player.js"></script>
      <lottie-player src="https://assets6.lottiefiles.com/packages/lf20_duq85prc.json"  background="transparent"  speed=".5"  style="width: 100%; height: 100%;"  autoplay></lottie-player>
    `;

    const modalContainerMirror = document.getElementById('lottie-modal-container-mirror');
    modalContainerMirror.innerHTML = `
      <script src="https://unpkg.com/@lottiefiles/lottie-player@latest/dist/lottie-player.js"></script>
      <lottie-player src="https://assets6.lottiefiles.com/packages/lf20_duq85prc.json" background="transparent" speed=".5" style="width: 100%; height: 100%;" autoplay></lottie-player>
      `;
});

document.addEventListener('click', (event) => {
  const isInsideContainer = container.contains(event.target);
  const isNavLoginSignup = event.target === navLoginSignup;
  const isModalVisible = modal.style.display === 'block';

  if (!isInsideContainer && !isNavLoginSignup && isModalVisible) {
    modal.style.display = 'none';
    mainSection.style.display = 'block';  // Show main section
  }
});

modalContent.addEventListener('click', (event) => {
  event.stopPropagation();
});

modal.addEventListener('click', () => {
  modal.style.display = 'none';
  mainSection.style.display = 'block';  // Show main section

  document.getElementById('lottie-modal-container').innerHTML = '';
});



// #4 - Switch between Register form and Login form //
switchToRegisterButton.addEventListener('click', () => {
	container.classList.add("right-panel-active");
});

switchToLoginButton.addEventListener('click', () => {
	container.classList.remove("right-panel-active");
});