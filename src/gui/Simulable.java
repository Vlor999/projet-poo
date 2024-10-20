package gui;

public interface Simulable {
  void next();
  
  void restart();
  
  default void selectedItem(String paramString) {}
}
