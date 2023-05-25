// Cookie //
const cookieArr = document.cookie.split("=")
const userId = cookieArr[1];

// DOM Elements //
const cardContainer = document.getElementById('card-container')

// Headers and Base Url //
const headers = {
    'Content-Type': 'application/json'
}

const baseUrl = 'http://localhost:8080';


// Account Cards //
async function getAccountsByUserId(userId) {
    await fetch(`${baseUrl}/accounts/user/${userId}`,{
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


