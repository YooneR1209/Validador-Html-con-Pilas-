package controller.Dao;

import controller.Dao.implement.AdapterDao;
import models.HtmlValidador;
import controller.tda.list.stack.Stack;
import controller.tda.list.LinkedList;

public class HtmlValidadorDao extends AdapterDao<HtmlValidador> {
    private LinkedList listAll;

    public HtmlValidadorDao() {
        super(HtmlValidador.class);
    }

    // Palabras reservadas
    private static final String[] WORDS_RESERVED = { "html", "head", "body", "input", "select" };

    // Método para verificar si la etiqueta es una palabra reservada
    private boolean isReservedWord(String possibleWord) {
        for (String reserved : WORDS_RESERVED) {
            if (reserved.equals(possibleWord)) {
                return true;
            }
        }
        return false;
    }

    public boolean validateHtml(HtmlValidador validador) {
        Stack<String> PT = new Stack<>(validador.getHtmlCode().length()); // Pila de apertura/cierre
        Stack<String> PC = new Stack<>(validador.getHtmlCode().length()); // Pila de etiquetas
        String expression = validador.getHtmlCode();
    
        for (int i = 0; i < expression.length(); i++) {
            if (expression.charAt(i) == '<') {
                i++; // Nos movemos al siguiente carácter después del '<'
    
                StringBuilder word = new StringBuilder();
                boolean isClosingWord = false;
    
                if (i < expression.length() && expression.charAt(i) == '/') {    // Verificamos si es una etiqueta de cierre (</ >)

                    isClosingWord = true;
                    i++; 
                    System.out.println("Llegamos aquí 1");
                }
    
                while (i < expression.length() && expression.charAt(i) != '>') {     // Extraemos el nombre de la etiqueta hasta '>'

                    word.append(expression.charAt(i));
                    i++;
                    System.out.println("Llegamos aquí 2");
                }
    
                String possibleReservedWord = word.toString();     // Limpiamos y obtenemos el nombre de la etiqueta en String

                System.out.println("Llegamos aquí 3 esta es la palabra: " + possibleReservedWord);
    
                if (isReservedWord(possibleReservedWord)) {      // Verificamos si es una palabra reservada

                    try {
                        if (isClosingWord) {         // Manejamos si la palabra reservada es de cierre o apertura
                            System.out.println("Llegamos aquí 4, agregamos: </");
                            PT.push("</");
                        } else {
                            System.out.println("Llegamos aquí 5, agregamos: <");
                            PT.push("<"); 
                        }
                    } catch (Exception e) {
                        System.out.println("Error al hacer push en la pila PT: " + e.getMessage());
                        validador.setIsValid(false);
                        return false;
                    }
    
                    if (isClosingWord) {         // De ser una etiqueta de cierre...
                        System.out.println("Procesando  la palabra reservada de cierre: " + possibleReservedWord);
                        try {
                            if (PC.isEmpty()) {
                                System.out.println("Pila de etiquetas de apertura vacía.");
                                validador.setIsValid(false);
                                return false;
                            }

                            String topTag = PC.getFirst();
                            String topSymbol = PT.getFirst();
                            System.out.println("Top tag PC: " + topTag);
                            System.out.println("Top simbolo PT: " + topSymbol);
    
                            if (!topTag.equals(possibleReservedWord) || !topSymbol.equals("</")) {           // Verificamos si la etiqueta de cierre coincide con la ultima etiqueta de apertura
                                System.out.println("Etiqueta de cierre no coincide: " + possibleReservedWord);
                                validador.setIsValid(false);
                                return false;
                            }
    
                            PC.pop();        // Elimino la palabra reservada     
                            PT.pop();        // Elimino el símbolo de cierre
                            PT.pop();        // Elimino el símbolo de apertura

                            System.out.println("Etiqueta de cierre eliminada: " );
                            System.out.println("Símbolo de cierre eliminado:" );
    
                        } catch (Exception e) {
                            System.out.println("Error al procesar la etiqueta de cierre: " + e.getMessage());
                            validador.setIsValid(false);
                            return false;
                        } 
    
                    } else {
                        try {
                            System.out.println("Agregando etiqueta de apertura: " + possibleReservedWord);
                            PC.push(possibleReservedWord);
                        } catch (Exception e) {
                            System.out.println("Error al agregar la etiqueta a la pila de apertura.");
                            validador.setIsValid(false);
                            return false;
                        }
                    }
    
                } else {
                    validador.setIsValid(false);
                    return false;
                }
            }
        }
    
        System.out.println("Tamaño de la pila de palabras reservadas (PC): " + PC.getSize());
        System.out.println("Tamaño de la pila de símbolos (PT): " + PT.getSize());
        
        if (!PC.isEmpty() || !PT.isEmpty()) {         // Validamos si ambas pilas quedaron vacías, si sobra algún elemento en las pilas la expresión es incorrecta
            System.out.println("Pilas no vacías al final del análisis.");
            validador.setIsValid(false);
            return false;
        } else {
            validador.setIsValid(true);
            return true;
        }
    
    }


    public void registerHtml(HtmlValidador obj) throws Exception {     // Método para registrar y validar el HTML
        HtmlValidador validador = new HtmlValidador(obj.getHtmlCode());
        boolean isValid = validateHtml(validador);
        validador.setIsValid(isValid);
        Integer id = getlistAll().getSize()+1; //Obtiene el tamaño de la lista y le suma 1 para asignar un nuevo id
        validador.setId(id); //Asigna el id al validador
        this.persist(validador);  // Llamada al método que puede lanzar una excepción
    }

    public LinkedList getlistAll(){  //Obtiene la lista de objetos
        if (listAll == null) { //Si la lista es nula 
            this.listAll = listAll(); //Invoca el método listAll() para obtener la lista de objetos
        }
        return listAll; //Devuelve la lista de objetos de la variable listAll
    }
    
    public Boolean delete(int index) throws Exception { //Elimina un objeto Familia por su índice
        this.supreme(index);
        this.listAll = listAll(); // Actualiza la lista de objetos
        return true; // Retorna verdadero si se eliminó correctamente
    }

}
