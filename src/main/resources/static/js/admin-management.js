function editMerchant() {
    document.getElementById('confirm-merchant-button').style.display = 'inline';
    document.getElementById('merchant-select-div').style.display = 'inline';
}

function showEditStore() {
    var selectOpt = document.getElementById('merchant-select').value;
    if(selectOpt === 'updatestore') {
        document.getElementById('store-group').removeAttribute('disabled');
    }
    else
        document.getElementById('store-group').setAttribute('disabled', 'disabled');
}
function editCustomer() {
    document.getElementById('confirm-customer-button').style.display = 'inline';
    document.getElementById('customer-select-div').style.display = 'inline';
}

