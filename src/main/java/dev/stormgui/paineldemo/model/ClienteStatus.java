package dev.stormgui.paineldemo.model;

public class ClienteStatus {
    private String nome;
    private boolean atendido;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isAtendido() {
        return atendido;
    }

    public void setAtendido(boolean atendido) {
        this.atendido = atendido;
    }

    // construtores, getters, setters

    // Métodos de fábrica para criar instâncias de ClienteStatus a partir de instâncias de Cliente
    public static ClienteStatus fromCliente(Client cliente) {
        ClienteStatus clienteStatus = new ClienteStatus();
        clienteStatus.setNome(cliente.getName());
        clienteStatus.setAtendido(cliente.isServed());
        return clienteStatus;
    }
}
