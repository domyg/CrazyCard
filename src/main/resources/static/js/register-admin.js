function changeRoleInput() {
    var roleInput = document.getElementById('role-select').value;
    var storeInput = document.getElementById('store-select');

    if (roleInput == 'merchant') {
        storeInput.style.display = 'block';
    } else {
        storeInput.style.display = 'none';
    }
}