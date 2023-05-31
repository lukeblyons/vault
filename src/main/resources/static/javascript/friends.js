let people = [
    {fakeUsername: "maddieL", fakeName: "Maddie Lyons", fakeAccountId: 1, fakeCardNumber: '4819228492634171'},
    {fakeUsername: "crewM", fakeName: "Crew Mitchell", fakeAccountId: 4, fakeCardNumber: '8937391056721831'},
    {fakeUsername: "ralphP", fakeName: "Ralph Powers", fakeAccountId: 7, fakeCardNumber: '2984957463892191'},
    {fakeUsername: "zachL", fakeName: "Zach Lyons", fakeAccountId: 10, fakeCardNumber: '7299381029182361'},
    {fakeUsername: "dalonJ", fakeName: "Dalon Jeppson", fakeAccountId: 13, fakeCardNumber: '3847562938475901'},
    {fakeUsername: "kennadyD", fakeName: "Kennady Davis", fakeAccountId: 16, fakeCardNumber: '2938475029185691'},
    {fakeUsername: "norielleM", fakeName: "Norielle Mitchell", fakeAccountId: 19, fakeCardNumber: '9485029183678941'},
    {fakeUsername: "macyS", fakeName: "Macy States", fakeAccountId: 22, fakeCardNumber: '2938574981029181'},
    {fakeUsername: "brittanyP", fakeName: "Brittany Powers", fakeAccountId: 25, fakeCardNumber: '6481928309571231'},
    {fakeUsername: "susanG", fakeName: "Susan Garris", fakeAccountId: 28, fakeCardNumber: '1029384756293851'},
    {fakeUsername: "lukeL", fakeName: "Luke Lyons", fakeAccountId: 31, fakeCardNumber: '5892019384756291'}
];

const friendsList = JSON.parse(localStorage.getItem(userId)) || [];

const addFriendIcon = document.querySelector('.add-friend-icon');
const addFriendFormContainer = document.querySelector('.add-friend-form-container');
const friendsTableBody = document.querySelector('.friends-table-body');

for(const friend of friendsList) {
    addFriendToTable(friend);
}

// create the form
const form = document.createElement('form');
const input = document.createElement('input');
const button = document.createElement('button');

input.type = 'text';
input.name = 'username';
input.placeholder = "Friends' Username"
button.type = 'submit';
button.textContent = 'Add Friend';

form.appendChild(input);
form.appendChild(button);

addFriendFormContainer.appendChild(form);

form.addEventListener('submit', function(e) {
    e.preventDefault();

    const enteredUsername = input.value;

    const person = people.find(p => p.fakeUsername === enteredUsername);

    if(person) {
        friendsList.push(person);

        localStorage.setItem(userId, JSON.stringify(friendsList));

        addFriendToTable(person);

        input.value = '';
    }
});

function addFriendToTable(friend) {
    const row = document.createElement('tr');
    const nameTd = document.createElement('td');
    nameTd.textContent = friend.fakeName;
    const accountIdTd = document.createElement('td');
    accountIdTd.textContent = friend.fakeAccountId;

    // create remove icon
    const removeTd = document.createElement('td');
    const removeIcon = document.createElement('i');
    removeIcon.textContent = 'remove';
    removeIcon.className = 'material-icons remove-friend-icon';
    removeIcon.dataset.username = friend.fakeUsername;
    removeTd.appendChild(removeIcon);

    row.appendChild(nameTd);
    row.appendChild(accountIdTd);
    row.appendChild(removeTd);
    friendsTableBody.appendChild(row);
}

friendsTableBody.addEventListener('click', function(e) {
    if (e.target.classList.contains ('remove-friend-icon')) {
        const username = e.target.dataset.username;  // get username from data attribute
        removeFriend(username);
        e.target.parentNode.parentNode.remove();  // remove friend's row from table
    }
});

function removeFriend(username) {
    const index = friendsList.findIndex(friend => friend.fakeUsername === username);
    if (index > -1) {
        friendsList.splice(index, 1);  // remove friend from array
        localStorage.setItem(userId, JSON.stringify(friendsList));  // update localStorage
    }
}
