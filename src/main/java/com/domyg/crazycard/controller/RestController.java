package com.domyg.crazycard.controller;

import com.domyg.crazycard.dto.CardTransactionDto;
import com.domyg.crazycard.dto.TransactionDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@org.springframework.web.bind.annotation.RestController
public class RestController {


    private List<TransactionDto> transactionsDto;
    private List<CardTransactionDto> cardTransactionsDto;

    @PostMapping("/merchant/analysis/data")
    public ResponseEntity<String> receiveDataMerchant(@RequestBody List<TransactionDto> transactionsDto) {
        // Salva i dati della tabella nella tua struttura dati
        this.transactionsDto = transactionsDto;

        // Restituisci una risposta di successo
        return ResponseEntity.ok("Dati ricevuti correttamente");
    }

    @GetMapping("/merchant/analysis/download")
    public ResponseEntity<Resource> downloadDataMerchant() {

        // Creazione del file JSON
        String jsonContent;
        try {
            jsonContent = new ObjectMapper().writeValueAsString(transactionsDto);
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        // Creazione di un oggetto ByteArrayResource dal contenuto JSON
        ByteArrayResource resource = new ByteArrayResource(jsonContent.getBytes());

        // Impostazione dell'header per il download del file JSON
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=data.json");

        // Restituzione del file JSON come risposta
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(jsonContent.length())
                .contentType(MediaType.APPLICATION_JSON)
                .body(resource);

    }

    @PostMapping("/customer/analysis/data")
    public ResponseEntity<String> receiveDataCustomer(@RequestBody List<TransactionDto> transactionsDto) {
        // Salva i dati della tabella nella tua struttura dati
        this.transactionsDto = transactionsDto;
        return ResponseEntity.ok("Dati ricevuti correttamente");
    }

    @GetMapping("/customer/analysis/download")
    public ResponseEntity<Resource> downloadDataCustomer() {

        // Creazione del file JSON
        String jsonContent;
        try {
            jsonContent = new ObjectMapper().writeValueAsString(transactionsDto);
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        // Creazione di un oggetto ByteArrayResource dal contenuto JSON
        ByteArrayResource resource = new ByteArrayResource(jsonContent.getBytes());

        // Impostazione dell'header per il download del file JSON
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=data.json");

        // Restituzione del file JSON come risposta
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(jsonContent.length())
                .contentType(MediaType.APPLICATION_JSON)
                .body(resource);

    }

    @PostMapping("/admin/analysis/data/trans")
    public ResponseEntity<String> receiveDataAdminTrans(@RequestBody List<TransactionDto> transactionsDto) {
        // Salva i dati della tabella nella tua struttura dati
        this.transactionsDto = transactionsDto;
        return ResponseEntity.ok("Dati ricevuti correttamente");
    }

    @GetMapping("/admin/analysis/data/trans/download")
    public ResponseEntity<Resource> downloadDataAdminTrans() {

        // Creazione del file JSON
        String jsonContent;
        try {
            jsonContent = new ObjectMapper().writeValueAsString(transactionsDto);
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        // Creazione di un oggetto ByteArrayResource dal contenuto JSON
        ByteArrayResource resource = new ByteArrayResource(jsonContent.getBytes());

        // Impostazione dell'header per il download del file JSON
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=data.json");

        // Restituzione del file JSON come risposta
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(jsonContent.length())
                .contentType(MediaType.APPLICATION_JSON)
                .body(resource);

    }

    @PostMapping("/admin/analysis/data/card")
    public ResponseEntity<String> receiveDataAdminCard(@RequestBody List<CardTransactionDto> cardTransactionsDto) {
        // Salva i dati della tabella nella tua struttura dati
        this.cardTransactionsDto = cardTransactionsDto;
        return ResponseEntity.ok("Dati ricevuti correttamente");
    }

    @GetMapping("/admin/analysis/data/card/download")
    public ResponseEntity<Resource> downloadDataAdminCard() {

        // Creazione del file JSON
        String jsonContent;
        try {
            jsonContent = new ObjectMapper().writeValueAsString(cardTransactionsDto);
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        // Creazione di un oggetto ByteArrayResource dal contenuto JSON
        ByteArrayResource resource = new ByteArrayResource(jsonContent.getBytes());

        // Impostazione dell'header per il download del file JSON
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=data.json");

        // Restituzione del file JSON come risposta
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(jsonContent.length())
                .contentType(MediaType.APPLICATION_JSON)
                .body(resource);

    }

    @PostMapping("/admin/analysis/data/store")
    public ResponseEntity<String> receiveDataAdminStore(@RequestBody List<TransactionDto> transactionsDto) {
        // Salva i dati della tabella nella tua struttura dati
        this.transactionsDto = transactionsDto;
        return ResponseEntity.ok("Dati ricevuti correttamente");
    }

    @GetMapping("/admin/analysis/data/store/download")
    public ResponseEntity<Resource> downloadDataAdminStore() {

        // Creazione del file JSON
        String jsonContent;
        try {
            jsonContent = new ObjectMapper().writeValueAsString(transactionsDto);
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        // Creazione di un oggetto ByteArrayResource dal contenuto JSON
        ByteArrayResource resource = new ByteArrayResource(jsonContent.getBytes());

        // Impostazione dell'header per il download del file JSON
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=data.json");

        // Restituzione del file JSON come risposta
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(jsonContent.length())
                .contentType(MediaType.APPLICATION_JSON)
                .body(resource);

    }

}
