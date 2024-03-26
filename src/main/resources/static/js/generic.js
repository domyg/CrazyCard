function sortTable(n, resultTable) {

    var table, rows, switching, i, x, y, shouldSwitch, dir, switchcount = 0;
    table = document.getElementById(resultTable);
    switching = true;
    dir = "asc";
    while (switching) {
        switching = false;
        rows = table.rows;
        for (i = 1; i < (rows.length - 1); i++) {
            shouldSwitch = false;
            x = rows[i].getElementsByTagName("TD")[n];
            y = rows[i + 1].getElementsByTagName("TD")[n];
            if (dir == "asc") {
                if (parseInt(x.innerHTML.toLowerCase())
                    > parseInt(y.innerHTML.toLowerCase())) {
                    shouldSwitch = true;
                    break;
                }
            } else if (dir == "desc") {
                if (parseInt(x.innerHTML.toLowerCase())
                    < parseInt(y.innerHTML.toLowerCase())) {
                    shouldSwitch = true;
                    break;
                }
            }
        }
        if (shouldSwitch) {
            rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
            switching = true;
            switchcount ++;
        } else {

            if (switchcount === 0 && dir === "asc") {
                dir = "desc";
                switching = true;
            }
        }
    }
}

function reverseCol(columnIndex, tableName) {
    var table = document.getElementById(tableName);
    var tbody = table.getElementsByTagName("tbody")[0];
    var rows = tbody.getElementsByTagName("tr");

    var cells = [];
    for (var i = 0; i < rows.length; i++) {
        var row = rows[i];
        var cell = row.getElementsByTagName("td")[columnIndex];
        cells.push(cell);
    }

    for (var j = cells.length - 1; j >= 0; j--) {
        tbody.appendChild(cells[j].parentNode);
    }
}