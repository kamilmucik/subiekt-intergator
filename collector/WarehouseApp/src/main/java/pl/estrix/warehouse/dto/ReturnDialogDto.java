package pl.estrix.warehouse.dto;

public class ReturnDialogDto<T> {

    T object;

    int status;

    String message;

    public T get() {
        return object;
    }

    public void set(T object) {
        this.object = object;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}