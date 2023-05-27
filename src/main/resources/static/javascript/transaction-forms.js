const sendForm = `
<form id="send-form" class="transaction-form">
  <div class="form-title">Send to a friend</div>
  <input type="text" id="send-to-username" placeholder="Username" />
  <input type="text" id="send-description" placeholder="Description" />
  <input type="number" id="send-amount" placeholder="Amount" min="0.01" step="0.01" />
  <input type="submit" value="Send" />
</form>`;

const transferForm = `
<form id="transfer-form" class="transaction-form">
  <div class="form-title">Transfer to another account</div>
  <select id="transfer-from-account"></select>
  <select id="transfer-to-account"></select>
  <input type="text" id="transfer-description" placeholder="Description" />
  <input type="number" id="transfer-amount" placeholder="Amount" min="0.01" step="0.01" />
  <input type="submit" value="Transfer" />
</form>`;

const depositForm = `
<form id="deposit-form" class="transaction-form">
  <div class="form-title"></div>
  <select id="deposit-account">Deposit via Stripe</select>
  <input type="text" id="deposit-description" placeholder="Description" />
  <input type="number" id="deposit-amount" placeholder="Amount" min="0.01" step="0.01" />
  <input type="submit" value="Deposit" />
</form>`;

const withdrawForm = `
<form id="withdraw-form" class="transaction-form">
  <div class="form-title">Withdraw</div>
  <select id="withdraw-account"></select>
  <input type="number" id="withdraw-amount" placeholder="Amount" min="0.01" step="0.01" />
  <p>Hold your phone up to the ATM sensor and click Withdraw</p>
  <input type="submit" value="Withdraw" />
</form>`;


const formContainer = document.querySelector('.transaction-form-container');


function appendForm(form) {
  formContainer.innerHTML = form;
}

// Function to populate select elements with account options
function populateAccountSelects() {
  fetch(``)
    .then(response => response.json())
    .then(accounts => {
      let selectElements = document.querySelectorAll('select');
      selectElements.forEach(select => {
        // Clear previous options
        select.options.length = 0;

        // Add an option for each account
        accounts.forEach(account => {
          let option = document.createElement('option');
          option.value = account.id;
          option.text = account.name;
          select.add(option);
        });
      });
    })
    .catch((error) => {
      console.error('Error:', error);
    });
}

// Add event listeners to the icons
document.querySelector('.send-icon').addEventListener('click', () => appendForm(sendForm));
document.querySelector('.transfer-icon').addEventListener('click', () => {
  appendForm(transferForm);
  populateAccountSelects();
});
document.querySelector('.deposit-icon').addEventListener('click', () => {
  appendForm(depositForm);
  populateAccountSelects();
});
document.querySelector('.withdraw-icon').addEventListener('click', () => {
  appendForm(withdrawForm);
  populateAccountSelects();
});

// Event listener for form submissions
document.addEventListener('submit', function (event) {
  event.preventDefault();


}, false);
