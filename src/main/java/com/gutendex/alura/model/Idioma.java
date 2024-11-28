package com.gutendex.alura.model;

public enum Idioma {

    ES("es"),
    EN("en"),
    FR("fr"),
    PT("pt");

    private String idiomaGutendex;
    Idioma(String idioma) {
        this.idiomaGutendex = idioma;

    }

    public static Idioma fromString(String text) {
        for (Idioma idioma : Idioma.values()) {
            if (idioma.idiomaGutendex.equalsIgnoreCase(text)) {
                return idioma;
            }
        }
        throw new IllegalArgumentException("Idioma no encontrado: " + text);
    }
}
