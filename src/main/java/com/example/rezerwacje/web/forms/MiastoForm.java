package com.example.rezerwacje.web.forms;

import javax.validation.constraints.Size;

public class MiastoForm {
    @Size(min=2, max = 60, message = "{miasto.size}")
    private String miasto;

    public String getMiasto() {
        return miasto;
    }

    public void setMiasto(String miasto) {
        this.miasto = miasto;
    }
}
