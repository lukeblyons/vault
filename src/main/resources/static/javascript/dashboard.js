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
  const data = await response.json();
  createAccountCard(data);
}


// ACCOUNT CARDS //
const createAccountCard = async (array) => {
  const grid = document.querySelector('.container-1');
  grid.innerHTML = '';

  // Fetch total balance and create "Total Balance" card
  const response = await fetch(`${baseUrl}/transactions/user/${userId}/totalBalance`, {
    method: "GET",
    headers: headers,
  });
  const totalBalance = await response.json();

  let totalBalanceCard = document.createElement("div");
  totalBalanceCard.classList.add("card");
  totalBalanceCard.innerHTML = `
    <div class="account-nickname">Total Balance:</div>
    <div class="account-balance">$ ${Number(totalBalance).toLocaleString()}</div>
    <div class="account-number">ALL ACCOUNTS</div>
    <i class="material-icons add-icon">add</i>`;

  grid.append(totalBalanceCard); // Adds the Total Balance card at the beginning of the grid


  // Set up event listener for the "Total Balance" card
  totalBalanceCard.addEventListener("click", () => {
    document.querySelectorAll(".card").forEach((c) => c.classList.remove("card-active"));
    totalBalanceCard.classList.add("card-active");
    document.querySelector(".which-account").innerText = "| ALL ACCOUNTS";

    // Fetch transactions for this user
    getAllTransactionsByUserId(userId);
  });

  const addIcon = totalBalanceCard.querySelector(".add-icon");
  addIcon.addEventListener("click", async (event) => {
    event.stopPropagation();
    const response = await fetch(`${baseUrl}/accounts/user/${userId}`, {
      method: "POST",
      headers: headers,
      body: JSON.stringify({}) // Add later
    });

    getAccountsByUserId(userId);
  });

  array.sort((a, b) => a.id - b.id);

  array.forEach(obj => {
    let accountCard = document.createElement("div");
    accountCard.classList.add("card");
    accountCard.setAttribute("id", obj.id); // Set account ID as id attribute
    accountCard.innerHTML = `
      <div class="account-nickname">${obj.nickname} Account:</div>
      <div class="account-balance">$ ${Number(obj.accountBalance).toLocaleString()}</div>
      <div class="account-number">Account Number: ${obj.accountNumber}</div>
      <i class="material-icons remove-icon">remove</i>`;
    grid.append(accountCard);

    // Set up event listener for account cards
    accountCard.addEventListener("click", () => {
      document.querySelectorAll(".card").forEach((c) => c.classList.remove("card-active"));
      accountCard.classList.add("card-active");
      document.querySelector(".which-account").innerText = "| " + obj.nickname + " Account";

      // Delete account icon
      const subtractIcon = accountCard.querySelector(".remove-icon");
      subtractIcon.addEventListener("click", async (event) => {
        event.stopPropagation();
        const response = await fetch(`${baseUrl}/accounts/${obj.id}`, {
          method: "DELETE",
          headers: headers,
        });
        getAccountsByUserId(userId);
      });

      // Fetch transactions for this account
      const accountId = accountCard.getAttribute("id");
      getAllTransactionsByAccountId(accountId);
    });
  });
};




// TRANSACTION ROWS //
async function getAllTransactionsByAccountId(accountId) {
  const response = await fetch(`${baseUrl}/transactions/account/${accountId}`, {
    method: "GET",
    headers: headers,
  });
  const data = await response.json();
  createTransactionRows(data);
}

// Fetch and create all transactions by user ID
async function getAllTransactionsByUserId(userId) {
  const response = await fetch(`${baseUrl}/transactions/user/${userId}`, {
    method: "GET",
    headers: headers,
  });
  const data = await response.json();
  createTransactionRows(data);
}

// Create transaction rows
function createTransactionRows(data) {
  const tbody = document.querySelector(".transactions-table-body");
  tbody.innerHTML = "";

  data.sort((a, b) => new Date(b.date) - new Date(a.date));

  data.forEach((transaction) => {
    let transactionRow = document.createElement("tr");
    let iconClass = "";


    transactionRow.innerHTML = `
      <td class="table-data">${transaction.transactionType}
        <p>${transaction.formattedDate} , ${transaction.formattedTime}</p>
      </td>
      <td class="table-data">$${Number(transaction.amount).toLocaleString()}
        <p>USD</p>
      </td>
      <td class="table-data">${transaction.description}
        <p>Memo</p>
      </td>
    `;

    tbody.appendChild(transactionRow);
  });
}

// Initialize the page
getAccountsByUserId(userId);




