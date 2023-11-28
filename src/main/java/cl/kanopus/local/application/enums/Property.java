package cl.kanopus.local.application.enums;

/**
 *
 * @author Pablo Diaz Saavedra
 * @email pabloandres.diazsaavedra@gmail.com
 *
 * This class identifies all properties specified in the file
 * application.properties It is used to access the properties referenced by the
 * class ContextHolder
 *
 */
public enum Property {

    //
    TEMPORAL_FOLDER("kanopus.tmp.folder");

    private final String value;

    Property(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
