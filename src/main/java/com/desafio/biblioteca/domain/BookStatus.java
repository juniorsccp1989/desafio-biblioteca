package com.desafio.biblioteca.domain;

public enum BookStatus {
    AVAILABLE("available"),
    RETIRED("retired");

	private final String value;

    BookStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static BookStatus findByValue(String status) {
        if (status == null) return null;
        for (BookStatus bs : BookStatus.values()) {
            if (bs.getValue().equalsIgnoreCase(status)) {
                return bs;
            }
        }
        return null;
    }
}