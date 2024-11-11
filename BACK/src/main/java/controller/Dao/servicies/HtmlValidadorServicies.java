package controller.Dao.servicies;
import controller.tda.list.LinkedList;
import models.HtmlValidador;
import controller.Dao.HtmlValidadorDao;

public class HtmlValidadorServicies {
    private HtmlValidadorDao obj;

    public HtmlValidadorServicies(){ //Constructor de la clase
        obj = new HtmlValidadorDao(); //Instancia un objeto de la clase FamiliaDao
    }

    public LinkedList<HtmlValidador> listAll(){ //Obtiene la lista de objetos LinkedList<T>
        return obj.getlistAll(); //Invoca el método getlistAll() de la clase FamiliaDao

    }

    public void save(HtmlValidador htmlValidador) throws Exception { //Método para guardar un objeto
        obj.registerHtml(htmlValidador); //Invoca el método registerHtml() de la clase FamiliaDao
    }

    public Boolean delete(int index) throws Exception{ //Elimina un objeto Familia por su índice
        return obj.delete(index); //Invoca el método delete() de la clase FamiliaDao y envía el índice
    }

}