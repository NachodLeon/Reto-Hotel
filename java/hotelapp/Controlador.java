package hotelapp;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

public class Controlador {

    @FXML
    private TextArea areaHabitaciones;

    @FXML
    private TextField campoNumero;

    @FXML
    private Label etiquetaResultado;

    private final Map<String, Habitacion> habitaciones =
            new LinkedHashMap<>();

    @FXML
    public void initialize() {
        cargarHabitaciones();
        mostrarHabitaciones();
    }

    private void cargarHabitaciones() {
        try (InputStream archivo = getClass().getResourceAsStream(
                "/hotelapp/reservas.xml")) {

            if (archivo == null) {
                etiquetaResultado.setText(
                        "Error: no se encuentra reservas.xml.");
                return;
            }

            DocumentBuilderFactory fabrica =
                    DocumentBuilderFactory.newInstance();

            fabrica.setFeature(
                    XMLConstants.FEATURE_SECURE_PROCESSING, true);
            fabrica.setFeature(
                    "http://apache.org/xml/features/"
                    + "disallow-doctype-decl", true);
            fabrica.setXIncludeAware(false);
            fabrica.setExpandEntityReferences(false);

            Document documento =
                    fabrica.newDocumentBuilder().parse(archivo);

            documento.getDocumentElement().normalize();

            NodeList lista =
                    documento.getElementsByTagName("habitacion");

            for (int i = 0; i < lista.getLength(); i++) {
                Element elemento = (Element) lista.item(i);

                String numero = leer(elemento, "numero");
                String tipo = leer(elemento, "tipo");
                String precio = leer(elemento, "precio");
                String estado = leer(elemento, "estado")
                        .toLowerCase().trim();

                if (numero.isEmpty()) {
                    continue;
                }

                if (!estado.equals("disponible")
                        && !estado.equals("ocupada")) {
                    continue;
                }

                Habitacion habitacion =
                        new Habitacion(numero, tipo, precio, estado);

                habitaciones.put(numero, habitacion);
            }

        } catch (Exception e) {
            etiquetaResultado.setText(
                    "Error al leer el XML: " + e.getMessage());
        }
    }

    private String leer(Element elemento, String etiqueta) {
        NodeList lista = elemento.getElementsByTagName(etiqueta);

        if (lista.getLength() == 0) {
            return "";
        }

        return lista.item(0).getTextContent().trim();
    }

    private void mostrarHabitaciones() {
        StringBuilder texto = new StringBuilder();

        for (Habitacion h : habitaciones.values()) {
            texto.append("Habitación: ").append(h.numero)
                    .append(" | Tipo: ").append(h.tipo)
                    .append(" | Precio: ").append(h.precio)
                    .append(" € | Estado: ").append(h.estado)
                    .append("\n");
        }

        areaHabitaciones.setText(texto.toString());
    }

    @FXML
    private void buscarHabitacion() {
        String numero = campoNumero.getText().trim();

        if (numero.isEmpty()) {
            etiquetaResultado.setText(
                    "Introduce el número de una habitación.");
            return;
        }

        Habitacion h = habitaciones.get(numero);

        if (h == null) {
            etiquetaResultado.setText(
                    "La habitación " + numero + " no existe.");
        } else if (h.estado.equals("disponible")) {
            etiquetaResultado.setText(
                    "La habitación " + numero
                    + " está DISPONIBLE. Tipo: " + h.tipo
                    + ". Precio: " + h.precio + " €.");
        } else {
            etiquetaResultado.setText(
                    "La habitación " + numero
                    + " está OCUPADA. Tipo: " + h.tipo + ".");
        }
    }

    private static class Habitacion {
        private final String numero;
        private final String tipo;
        private final String precio;
        private final String estado;

        private Habitacion(String numero, String tipo,
                           String precio, String estado) {
            this.numero = numero;
            this.tipo = tipo;
            this.precio = precio;
            this.estado = estado;
        }
    }
}
