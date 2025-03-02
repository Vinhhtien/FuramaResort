package view;

import java.util.ArrayList;

public interface View<T> {
    void display(ArrayList<T> entities);
     T getADetail();
}
