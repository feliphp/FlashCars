package aplicacion.android.jotajotavm.flashcards;

public class Card {

    private int VIN;
    private String pregunta;
    private String respuesta;
    private int rating;

    public Card(int VIN, String pregunta, String respuesta, int rating){
        this.VIN = VIN;
        this.pregunta = pregunta;
        this.respuesta = respuesta;
        this.rating = rating;
    }


    public int getVIN() {
        return VIN;
    }

    public void setVIN(int VIN) {
        this.VIN = VIN;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
