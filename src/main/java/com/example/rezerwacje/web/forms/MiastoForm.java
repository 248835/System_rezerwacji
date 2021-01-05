package com.example.rezerwacje.web.forms;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class MiastoForm {

    @NotNull
    @Size(min=2)
    private String miasto;

    public String getMiasto() {
        return miasto;
    }

    public void setMiasto(String miasto) {
        this.miasto = miasto;
    }
}
