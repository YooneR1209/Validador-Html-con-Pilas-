package controller.tda.list.stack;

import controller.tda.list.ListEmptyException;

public class Stack<E> {
    private StackOperation<E> stackOperation;
    
    public Stack(Integer cant){
        this.stackOperation = new StackOperation<>(cant);
    }

    public void push(E dato) throws Exception {
        stackOperation.push(dato);
    }

    public Integer getSize() {
        return this.stackOperation.getSize();
    }

    public void clear () {
        this.stackOperation.reset();
    }

    public Integer getTop() {
        return this.stackOperation.getTop();
    }

    public void print() {
        System.out.println("PILA");
        System.out.println(stackOperation.toString());
        System.out.println("******");    
    }

    public E pop() throws Exception {
        return stackOperation.pop();
    }

    public Boolean isEmpty() {
        return stackOperation.isEmpty();
    }
    
    public E getFirst() {
        try {
            return stackOperation.getFirst();
        } catch (ListEmptyException e) {
            // Manejar la excepción si la pila está vacía
            // Por ejemplo, puedes retornar null o lanzar una excepción más específica
            return null;  // o manejar de alguna otra forma
        }
    }

}
