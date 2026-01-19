package khady.abfinal;

import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * Esta clase implementa una pila siguiendo la política LIFO
 * (Last In, First Out). Se utiliza LinkedList como estructura
 * interna para permitir crecimiento dinámico y operaciones
 * en tiempo constante.
 */
public class PilaBasica {

    // Estructura interna de la pila
    private LinkedList<Integer> elementos;

    /**
     * Constructor.
     * Inicializa una pila vacía.
     */
    public PilaBasica() {
        this.elementos = new LinkedList<>();
    }

    /**
     * Comprueba si la pila está vacía.
     *
     * @return true si no contiene elementos
     */
    public boolean isEmpty() {
        return elementos.isEmpty(); // O(1)
    }

    /**
     * Inserta un elemento en la parte superior de la pila (push).
     *
     * @param elemento valor a apilar
     */
    public void push(int elemento) {
        elementos.addFirst(elemento); // O(1)
    }

    /**
     * Extrae y devuelve el elemento superior de la pila (pop).
     *
     * @return elemento superior
     * @throws NoSuchElementException si la pila está vacía
     */
    public int pop() {
        if (isEmpty()) {
            throw new NoSuchElementException("No se puede hacer pop: la pila está vacía");
        }
        return elementos.removeFirst(); // O(1)
    }

    /**
     * Devuelve el elemento superior sin eliminarlo (top / peek).
     *
     * @return elemento superior
     * @throws NoSuchElementException si la pila está vacía
     */
    public int top() {
        if (isEmpty()) {
            throw new NoSuchElementException("No se puede consultar top: la pila está vacía");
        }
        return elementos.getFirst(); // O(1)
    }

    /**
     * Devuelve el número de elementos almacenados en la pila.
     *
     * @return tamaño de la pila
     */
    public int size() {
        return elementos.size(); // O(1)
    }

    /**
     * Ejemplo de uso de la pila.
     */
    public static void main(String[] args) {
        PilaBasica pila = new PilaBasica();

        // Apilar elementos (simulan paquetes cargados en la furgoneta)
        pila.push(10);
        pila.push(20);
        pila.push(30);

        System.out.println("Elemento superior: " + pila.top()); // 30

        // Desapilar elementos
        while (!pila.isEmpty()) {
            System.out.println("Pop: " + pila.pop());
        }
    }
}
