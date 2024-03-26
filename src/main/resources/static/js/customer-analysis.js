function changeCardNumber() {
    var cardInput = document.getElementById('card-select').value;
    var cardNumberDate = document.getElementById('card-number-on-date');
    var cardNumberRange = document.getElementById('card-number-on-range');
    var cardNumberAll = document.getElementById('card-number-all');

    cardNumberDate.value = cardInput;
    cardNumberRange.value = cardInput;
    cardNumberAll.value = cardInput;
}

function sendData() {
    var tableData = [];

    var table = document.getElementById('resultTable');

    // Iterazione sulle righe della tabella (ignorando l'intestazione)
    for (var i = 1; i < table.rows.length; i++) {
        var row = table.rows[i];
        var date = row.cells[0].innerText;
        var amount = parseFloat(row.cells[1].innerText);
        var cardNumber = row.cells[2].innerText;
        var storeName = row.cells[3].innerText;

        var transaction = {
            date: date,
            amount: amount,
            cardNumber: cardNumber,
            storeName: storeName
        };

        tableData.push(transaction);
    }

    // Invia i dati al server come file JSON
    var xhr = new XMLHttpRequest();
    xhr.open('POST', '/customer/analysis/data', true);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            console.log('Dati inviati correttamente');
            downloadFile();
        } else {
            console.error('Errore durante l\'invio dei dati');
        }
    };
    xhr.send(JSON.stringify(tableData));
}

function downloadFile() {
    // Effettua una richiesta GET per scaricare il file JSON
    var xhr = new XMLHttpRequest();
    xhr.open('GET', '/customer/analysis/download', true);
    xhr.responseType = 'blob';
    xhr.onload = function() {
        if (xhr.status === 200) {
            // Creazione dell'URL del file scaricato
            var url = window.URL.createObjectURL(xhr.response);

            // Creazione di un link temporaneo con click automatico per download
            var link = document.createElement('a');
            link.href = url;
            link.download = 'data.json';
            link.click();

            // Rimozione dell'URL creato
            window.URL.revokeObjectURL(url);
        }
    };
    xhr.send();
}