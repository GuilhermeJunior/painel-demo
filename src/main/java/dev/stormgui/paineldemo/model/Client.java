package dev.stormgui.paineldemo.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class Client implements Serializable {

    @Serial
    private static final long serialVersionUID = -3969352858203924755L;
    private String name;
    private boolean served;

    public Client(String name, boolean served) {
        this.name = name;
        this.served = served;
    }

    public Client() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isServed() {
        return served;
    }

    public void setServed(boolean served) {
        this.served = served;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Client) obj;
        return Objects.equals(this.name, that.name) &&
                this.served == that.served;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, served);
    }

    @Override
    public String toString() {
        return "Client[" +
                "name=" + name + ", " +
                "served=" + served + ']';
    }

}
