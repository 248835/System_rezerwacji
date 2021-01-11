package com.example.rezerwacje.web.forms;

import javax.validation.constraints.Size;

public class HasloForm {
    @Size(min=5, max=20, message="{haslo.size}")
    private String haslo;

    public String getHaslo() {
        return haslo;
    }

    public void setHaslo(String haslo) {
        this.haslo = haslo;
    }
}
