package cl.kanopus.local.service.to;

import javax.validation.constraints.NotBlank;

/**
 *
 * @author Pablo Diaz Saavedra
 * @email pabloandres.diazsaavedra@gmail.com
 */
public class Document {

    @NotBlank
    private String dataBase64;

    public String getDataBase64() {
        return dataBase64;
    }

    public void setDataBase64(String dataBase64) {
        this.dataBase64 = dataBase64;
    }

}
