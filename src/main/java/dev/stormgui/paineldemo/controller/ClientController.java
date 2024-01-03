package dev.stormgui.paineldemo.controller;

import dev.stormgui.paineldemo.model.Client;
import dev.stormgui.paineldemo.model.ClienteStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final SimpMessagingTemplate messagingTemplate;
    private List<Client> queue = new ArrayList<>();

    @Autowired
    public ClientController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @PostMapping
    public ResponseEntity<Void> addClient(@RequestBody Client client) {
        queue.add(client);
        enviarAtualizacaoFila();
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/atenderProximo")
    public ResponseEntity<Void> attendNextClient() {
        if (!queue.isEmpty()) {
            Client servedClient = queue.remove(0);
            servedClient.setServed(true);
            enviarAtualizacaoFila();
        }
        return ResponseEntity.ok().build();
    }

    @MessageMapping("/inicializar")
    public void inicializar() {
        enviarAtualizacaoFila();
    }

    private void enviarAtualizacaoFila() {
        List<ClienteStatus> clientesStatus = queue.stream()
                .map(ClienteStatus::fromCliente)
                .collect(Collectors.toList());

        messagingTemplate.convertAndSend("/topic/fila", clientesStatus);
    }
}
