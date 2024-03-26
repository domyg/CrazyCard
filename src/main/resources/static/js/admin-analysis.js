function sendData(tableName) {
    var tableData = [];

    var table = document.getElementById(tableName);
    console.log(tableName)
    var row;

    var restUrl;
    var restUrlDownload;

    var date
    var amount
    var cardNumber
    var storeName
    var transaction

    if(tableName === 'resultTableTrans') {
        restUrl = '/admin/analysis/data/trans'
        restUrlDownload = '/admin/analysis/data/trans/download'
        // Iterazione sulle righe della tabella (ignorando l'intestazione)
        for (var i = 1; i < table.rows.length; i++) {
            row = table.rows[i];
            date = row.cells[0].innerText;
            amount = parseFloat(row.cells[1].innerText);
            cardNumber = row.cells[2].innerText;
            storeName = row.cells[3].innerText;

            transaction = {
                date: date,
                amount: amount,
                cardNumber: cardNumber,
                storeName: storeName
            };

            tableData.push(transaction);
        }
    }
    else if(tableName === 'resultTableStore') {
        restUrl = '/admin/analysis/data/store'
        restUrlDownload = '/admin/analysis/data/store/download'
        // Iterazione sulle righe della tabella (ignorando l'intestazione)
        for (var i = 1; i < table.rows.length; i++) {
            row = table.rows[i];
            date = row.cells[0].innerText;
            amount = parseFloat(row.cells[1].innerText);
            cardNumber = row.cells[2].innerText;
            storeName = row.cells[3].innerText;

            transaction = {
                date: date,
                amount: amount,
                cardNumber: cardNumber,
                storeName: storeName
            };

            tableData.push(transaction);
        }
    }

    else if(tableName === 'resultTableCard') {
        restUrl = '/admin/analysis/data/card'
        restUrlDownload = '/admin/analysis/data/card/download'
        for (var j = 1; j < table.rows.length; j++) {
            row = table.rows[j];
            var card = row.cells[0].innerText;
            var state = row.cells[1].innerText;
            var owner = row.cells[2].innerText;
            var transactionsNumber = row.cells[3].innerText;
            var totalSpent = parseFloat(row.cells[4].innerText);
            var averageSpent = parseFloat(row.cells[5].innerText);

            transaction = {
                card: card,
                state: state,
                owner: owner,
                transactionsNumber: transactionsNumber,
                totalSpent: totalSpent,
                averageSpent: averageSpent
            };

            tableData.push(transaction);
        }
    }

    // Invia i dati al server come file JSON
    var xhr = new XMLHttpRequest();
    xhr.open('POST', restUrl, true);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            console.log('Dati inviati correttamente');
            downloadFile(restUrlDownload);
        } else {
            console.error('Errore durante l\'invio dei dati');
        }
    };
    xhr.send(JSON.stringify(tableData));
}

function downloadFile(restUrlDownload) {
    // Effettua una richiesta GET per scaricare il file JSON
    var xhr = new XMLHttpRequest();
    xhr.open('GET', restUrlDownload, true);
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