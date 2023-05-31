// Action Icon Event Listeners
document.querySelector('.deposit-icon').addEventListener('click', () => displayForm('deposit-form', ['Account ID', 'Amount', 'Description'], submitDepositForm, 'Deposit'));
document.querySelector('.withdraw-icon').addEventListener('click', () => displayForm('withdraw-form', ['Account ID', 'Amount', 'Description'], submitWithdrawForm, 'Withdraw', 'Hold your phone up to the ATM sensor and click "Withdraw"'));
document.querySelector('.transfer-icon').addEventListener('click', () => displayForm('transfer-form', ['From Account ID', 'To Account ID', 'Amount', 'Description'], submitTransferForm, 'Transfer'));
document.querySelector('.send-icon').addEventListener('click', () => displayForm('send-form', ['Account ID', 'To Account ID', 'Amount', 'Description'], submitSendForm, 'Send'));

// Common function to display any form
function displayForm(formId, labels, eventListenerFunction, formTitle, instruction = null) {
    const transactionFormContainer = document.querySelector('.transaction-form-container');
    let innerHTML = `<h2>${formTitle}</h2><form id="${formId}">`;

    labels.forEach((label, index) => {
        if (label.toLowerCase().includes('account id')) {
            let accountIdType = (label.toLowerCase().includes('from')) ? '-from-account-id' : '-to-account-id';
            let inputType = (label.toLowerCase().includes('to') && formId === 'send-form') ? 'input' : 'select';
            if (formId !== 'transfer-form' && formId !== 'send-form') accountIdType = '-account-id';
            innerHTML += `
                <label for="${formId}${accountIdType}">${label}</label>
                <${inputType} id="${formId}${accountIdType}" autocomplete="off"></${inputType}>`;
        } else {
            innerHTML += `
                <label for="${formId}-${label.toLowerCase().replace(' ', '-')}">${label}</label>
                <input id="${formId}-${label.toLowerCase().replace(' ', '-')}"" type="text" autocomplete="off" required />`;
        }
    });

    if (instruction) {
        innerHTML += `<p class="form-instruction">${instruction}</p>`;
    }

    innerHTML += `<button type="submit">${formTitle}</button></form>`;
    transactionFormContainer.innerHTML = innerHTML;

    // fill account options
    const accountIdSelects = document.querySelectorAll(`#${formId} select[id$="-account-id"]`);
    if (accountIdSelects) {
        accountIdSelects.forEach(select => fillAccountOptions(select));
    }

    document.querySelector(`#${formId}`).addEventListener('submit', eventListenerFunction);
}


// Fetch and fill account options
async function fillAccountOptions(accountIdSelect) {
    const response = await fetch(`${baseUrl}/accounts/user/${userId}`, {
        method: "GET",
        headers: headers,
    });

    let data = await response.json();

    // Sort the accounts by nickname
    data = data.sort((a, b) => a.nickname.localeCompare(b.nickname));

    data.forEach(account => {
        const option = document.createElement('option');
        option.value = account.id;
        option.text = account.nickname;
        accountIdSelect.appendChild(option);
    });
}

// Handling form submission
const handleSubmit = async (e) => {
    e.preventDefault();
    const form = e.target;
    const accountId = form.querySelector('select[id$="-from-account-id"], select[id$="-account-id"]').value;
    const amount = form.querySelector('input[id$="-amount"]').value;
    const description = form.querySelector('input[id$="-description"]').value;
    let bodyObj = {};

    switch (e.target.id) {
        case 'deposit-form':
        case 'withdraw-form':
            bodyObj = {
                transactionType: e.target.id.split('-')[0],
                amount: amount,
                description: description
            };
            break;
        case 'transfer-form':
            const toAccountId = form.querySelector('select[id$="-to-account-id"]').value;
            bodyObj = {
                transactionType: "Transfer to",
                toAccountId: toAccountId,
                amount: amount,
                description: description
            };
            break;
        case 'send-form':
            const toAccountIdSend = form.querySelector('input[id$="-to-account-id"]').value;
            bodyObj = {
                transactionType: "Send to",
                toAccountId: toAccountIdSend,
                amount: amount,
                description: description
            };
            break;
    }

    try {
        const response = await fetch(`${baseUrl}/transactions/${accountId}`, {
            method: 'POST',
            body: JSON.stringify(bodyObj),
            headers: headers
        });

        if (response.ok) {
            const jsonResponse = await response.json();
            console.log(jsonResponse);
            form.reset();
        } else {
            console.log(`Error: ${response.status}`);
        }
    } catch (error) {
        console.error('Error:', error);
    }

    location.reload();
};


// Event listeners for form submissions
const submitDepositForm = (e) => handleSubmit(e);
const submitWithdrawForm = (e) => handleSubmit(e);
const submitTransferForm = (e) => handleSubmit(e);
const submitSendForm = (e) => handleSubmit(e);

// Trigger 'click' event on the 'send-icon' element after the page loads
window.onload = function() {
    document.querySelector('.send-icon').click();
};