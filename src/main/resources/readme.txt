Il Sistema CrazyCard viene proposto con le seguenti funzionalità suddivise per ruolo:

CHIUNQUE:

1) Login
2) Registrazione come CUSTOMER
3) Controllo Credito Residuo di una Carta noto il Numero

ADMIN:

1) Registrazione di Nuovi Utenti con Ruolo [ADMIN] o [MERCHANT]
2) Gestione degli Utenti Registrati nel Sistema in base al Ruolo:
    - ADMIN --> Nessuna Modifica
    - MERCHANT --> Elimina Account / Cambia Negozio
    - CUSTOMER --> Elimina Account / Disabilita Carte
3) Creazione di Nuove Carte dato l'Importo
4) Blocco / Sblocco dello Stato di una Carta noto il Numero
5) Abilitazione / Disabilitazione Negozi
6) Creazione / Eliminazione Negozi
7) Generazione e Download di Report secondo criteri differenti

MERCHANT:

1) Registrazione Carta per:
    - Utente già Registrato
    - Nuovo Utente
2) Addebbito / Accredito su Carta tramite Numero e PIN
3) Generazione e Download di Report con le Operazioni svolte nel Negozio

CUSTOMER:

1) Generazione e Download della Lista Movimenti della Carta selezionata tra quelle possedute


----

All'interno della directory 'resources' sono presenti, oltre questo, tre file fondamentali:

1) schema.sql
    - Script SQL che consente la creazione del Database secondo i vincoli imposti per il corretto funzionamento della
      Web Application

2) insert.sql
    - Script SQL che consente di riempire il Database con valori che simulano un sistema già attivo e che comprende,
      oltre che utenti, negozi e ruoli, anche delle carte già registrate e delle transazioni già effettuate

3) account.txt
    - File testuale che comprende le credenziali di accesso per i diversi utenti già registrati nel sistema.