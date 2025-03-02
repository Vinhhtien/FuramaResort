
package Service;

public interface Service<T> {
    T findbyId(String id);
    void display();
    void add (T entity);
    void save();
    
}
