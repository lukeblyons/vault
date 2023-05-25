// Cookie //
const cookieArr = document.cookie.split("=");
const userId = cookieArr[1];

// Headers and Base Url //
const headers = {
  "Content-Type": "application/json",
};

const baseUrl = "http://localhost:8080";

// Fetch and create account cards
async function getAccountsByUserId(userId) {
  const response = await fetch(`${baseUrl}/accounts/user/${userId}`, {
    method: "GET",
    headers: headers,
  });
  console.log('getAccountsByUserId response:', response);
  const data = await response.json();
  console.log('getAccountsByUserId data:', data);
  createAccountCard(data);
}

// Create account cards
const createAccountCard = (array) => {
  const grid = document.querySelector('.grid');
  grid.innerHTML = '';

  array.sort((a, b) => a.id - b.id);

  array.forEach(obj => {
    console.log('obj.id:', obj.id);
    let accountCard = document.createElement("div");
    accountCard.classList.add("card");
    accountCard.setAttribute("id", obj.id); // Set account ID as id attribute
    accountCard.innerHTML = `
      <div class="account-nickname">${obj.nickname} Account:</div>
      <div class="account-balance">$ ${obj.accountBalance}</div>
      <div class="account-number">Account Number: ${obj.accountNumber}</div>`;
    grid.append(accountCard);

    // Set up event listener for each card
    accountCard.addEventListener("click", () => {
      // Remove active class from all cards
      document.querySelectorAll(".card").forEach((c) => c.classList.remove("card-active"));

      // Add active class to clicked card
      accountCard.classList.add("card-active");

      // Fetch transactions for this account
      const accountId = accountCard.getAttribute("id");
      console.log('accountId:', accountId);
      getAllTransactionsByAccountId(accountId);
    });
  });
};

// Fetch and create transaction rows
async function getAllTransactionsByAccountId(accountId) {
  const response = await fetch(`${baseUrl}/transactions/account/${accountId}`, {
    method: "GET",
    headers: headers,
  });
  console.log('getAllTransactionsByAccountId response:', response);
  const data = await response.json();
  console.log('getAllTransactionsByAccountId data:', data);
  createTransactionRows(data);
}

// Create transaction rows
function createTransactionRows(data) {
  const tbody = document.querySelector(".transaction-row");
  tbody.innerHTML = "";

  data.forEach((transaction) => {
    let transactionRow = document.createElement("tr");
    transactionRow.innerHTML = `
      <td class="table-data">${transaction.transactionType}
        <p>Approved</p>
      </td>
      <td class="table-data">$${transaction.amount}
        <p>USD</p>
      </td>
      <td class="table-data">${transaction.description}
        <p>Memo</p>
      </td>
      <td class="table-data">${transaction.dateTime}
        <p>MST</p>
      </td>
    `;

    tbody.appendChild(transactionRow);
  });
}

// Initialize the page
getAccountsByUserId(userId);
