package cscc43.assignment.store;

import java.util.Observable;

public class Store<T> extends Observable {
    private T state;

    public Store(T initialState) {
        this.state = initialState;
    }

    public T getState() {
        return this.state;
    }

    public void setState(T state) {
        this.setChanged();
        this.state = state;
        this.notifyObservers(this.state);
    }
}