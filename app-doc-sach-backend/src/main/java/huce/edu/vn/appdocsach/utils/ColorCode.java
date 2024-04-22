package huce.edu.vn.appdocsach.utils;

public enum ColorCode {
    RED("\u001B[31m"), GREEN("\u001B[32m"), BLUE("\u001B[34m"), YELLOW("\u001B[33m"), RESET("\u001B[0m");
    private String value;
    private ColorCode(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
