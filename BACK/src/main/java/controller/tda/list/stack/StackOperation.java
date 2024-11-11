package controller.tda.list.stack;

import controller.tda.list.ListEmptyException;
import controller.tda.list.OverFlowException;
import controller.tda.list.LinkedList;

public class StackOperation <E> extends LinkedList<E> {
        private Integer top;
        LinkedList<E> stack;

    public StackOperation(Integer top){ //Constructor de la clase
        this.top = top; //Inicializa la variable top
        this.stack = new LinkedList<E>();
    }

    public Boolean verify() {
        return getSize().intValue() <= top.intValue(); //Verifica si el tamaño de la lista es menor o igual al tope
    }

    public void push(E dato) throws Exception { //Agrega un elemento al tope de la pila
        if (verify()) { //Verifica si la pila no está llena
            stack.add(dato, 0); //Agrega el elemento al vstope de la pila
        } else {
            throw new OverFlowException("Pila llena"); //Lanza una excepción si la pila está llena
        }
    }

    public E pop() throws ListEmptyException { //Elimina el elemento del tope de la pila
        if (isEmpty()) { //Verifica si la pila no está vacía
            throw new ListEmptyException("Pila vacía"); //Lanza una excepción si la pila está vacía
        } else {
            return stack.deleteFirst(); //Elimina el elemento del tope de la pila
        }
    }

    public Integer getTop() { //Obtiene el tope de la pila
        return top; //Devuelve el tope de la pila
    }

    public void setTop(Integer top) { //Establece el tope de la pila
        this.top = top; //Asigna el tope de la pila
    }

    public E getFirst() throws ListEmptyException { //Obtiene el primer elemento de la pila
        System.out.println("Primer elemento de la pila: "+stack.getFirst());
        if (isEmpty()) { //Verifica si la pila no está vacía
            throw new ListEmptyException("No hay nada"); //Lanza una excepción si la pila está vacía
        } else {
            return stack.getFirst(); //Obtiene el primer elemento de la pila
        }
    }

    public Boolean isEmpty() {
        return stack.isEmpty();

    }

    public Integer getSize() {
        return this.stack.getSize();
    }

    
}