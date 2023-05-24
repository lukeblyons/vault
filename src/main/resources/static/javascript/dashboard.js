// Cookie //
const cookieArr = document.cookie.split("=")
const userId = cookieArr[1];

// DOM Elements //
const cardContainer = document.getElementById('card-container')
const transactionBody = document.getElementById("transaction-body");

// #1 - Account Cards //
let accountBalanceTotal = document.getElementById('total-account-balance');

let nickname = document.getElementById('account-nickname');
let accountBalance = document.getElementById('account-balance');
let accountNumber = document.getElementById('account-number');


// #2 Transaction Rows //
let transactionType = document.getElementById('transaction-type');
let amount = document.getElementById('transaction-amount');
let description = document.getElementById('transaction-description');
let dateTime = document.getElementById('transaction-date');
let recipientAccountNumber = document.getElementById('transaction-recipient-account');


const headers = {
    'Content-Type': 'application/json'
}

const baseUrl = 'http://localhost:8080/accounts';

async function getAccountsByUserId(userId) {
    await fetch(`${baseUrl}/user/${userId}`,{
        method: "GET",
        headers: headers
    })
        .then(response => response.json())
        .then(data => createAccountCard(data))
        .catch(err => console.error(err))
}

const createAccountCard = (array) => {
    const grid = document.querySelector('.grid');
    grid.innerHTML = '';

    array.sort((a, b) => a.accountId - b.accountId);

    array.forEach(obj => {
        let accountCard = document.createElement("div");
        accountCard.classList.add("card");
        accountCard.innerHTML = `
            <div class="account-nickname">${obj.nickname} Account:</div>
            <div class="account-balance">$ ${obj.accountBalance}</div>
            <div class="account-number">Account Number: ${obj.accountNumber}</div>`;
        grid.append(accountCard);
    });
};
getAccountsByUserId(userId);



// Transaction Rows //
async function getAllTransactionsByAccountId(accountId) {
  await fetch(`${baseUrl}/transactions/${userId}/${accountId}`, {
    method: "GET",
    headers: headers
  })
    .then((response) => response.json())
    .then((data) => createTransactionRow(data))
    .catch((err) => console.error(err));
}

const createTransactionRow = (array) => {
  transactionBody.innerHTML = "";

  array.forEach((obj) => {
    let transactionRow = document.createElement("tr");
    transactionRow.innerHTML = `
      <td class="table-data transaction-type">
        <p class="top-p">${obj.transactionType}</p>
        <p class="bottom-p">Approved</p>
      </td>
      <td class="table-data transaction-amount">
        <p class="top-p">$${obj.amount}</p>
        <p class="bottom-p">USD</p>
      </td>
      <td class="table-data transaction-description">
        <p class="top-p">${obj.description}</p>
        <p class="bottom-p">Direct Deposit</p>
      </td>
      <td class="table-data transaction-date">
        <p class="top-p">${obj.dateTime}</p>
        <p class="bottom-p">USD</p>
      </td>
      <td class="table-data transaction-recipient-account">
        <p class="top-p">${obj.recipientAccountNumber}</p>
        <p class="bottom-p">Approved</p>
      </td>`;
    transactionBody.append(transactionRow);
  });
};

// Call the function with the userId variable
getAllTransactionsByAccountId(accountId);

/*
      <td class="table-data transaction-date">
        <p class="top-p">${obj.dateTime.format(DateTimeFormatter.ofPattern('dd MMM, yyyy HH:mm:ss'))}</p>
        <p class="bottom-p">${obj.dateTime.format(DateTimeFormatter.ofPattern('h:mm:ss a'))}</p>
      </td>
*/





// Card color changes on hover and active //
const cards = document.querySelectorAll('.card');

cards.forEach(card => {
    card.addEventListener('click', () => {
        cards.forEach(c => c.classList.remove('card-active'));
        card.classList.add('card-active');
    });
});
