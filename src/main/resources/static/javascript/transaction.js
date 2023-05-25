// Cookie //
const cookieArr = document.cookie.split("=")
const userId = cookieArr[1];

const headers = {
    'Content-Type': 'application/json'
}

const baseUrl = 'http://localhost:8080';


// DOM Elements //
const transactionRow = document.querySelector(".transaction-row");

// Fetch and create transaction rows //
async function getAllTransactionsByAccountId(accountId) {
  await fetch(`${baseUrl}/transactions/account/${accountId}`, {
    method: "GET",
    headers: headers,
  })
    .then((response) => response.json())
    .then((data) => createTransactionRows(data))
    .catch((err) => console.error(err));
}

// Create transaction rows //
function createTransactionRows(data) {
  const tbody = document.querySelector(".transaction-row");
  tbody.innerHTML = "";

  data.forEach((transaction) => {
    let transactionRow = document.createElement("tr");
    transactionRow.innerHTML = `
      <td class="table-data">${transaction.transactionType}</td>
      <td class="table-data">$${transaction.amount}</td>
      <td class="table-data">${transaction.description}</td>
      <td class="table-data">${transaction.dateTime}</td>
    `;

    tbody.appendChild(transactionRow);
  });
}

// Card color changes on click and active //
const cards = document.querySelectorAll(".card");

cards.forEach((card) => {
  card.addEventListener("click", () => {
    cards.forEach((c) => c.classList.remove("card-active"));
    card.classList.add("card-active");

    const accountId = card.getAttribute("id");
    getAllTransactionsByAccountId(accountId);
  });
});
