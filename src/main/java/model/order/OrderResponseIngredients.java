package model.order;

import java.util.ArrayList;

public class OrderResponseIngredients {
    private boolean success;
    private ArrayList<OrderJson> data;

    public OrderResponseIngredients(boolean success, ArrayList<OrderJson> data) {
        this.success = success;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ArrayList<OrderJson> getData() {
        return data;
    }

    public void setData(ArrayList<OrderJson> data) {
        this.data = data;
    }
}