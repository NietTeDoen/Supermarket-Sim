package Model.Store;

/**
 * Enum die verschillende soorten schappen beschrijft.
 * Elk type heeft een eigen afbeeldingspad, breedte en hoogte.
 * Wordt gebruikt om verschillende schapafbeeldingen en groottes te beheren in de simulatie.
 */
public enum SchapType {

    // 📦 Elk schaptype heeft een uniek afbeeldingspad en vaste afmetingen.
    Kast1("/images/Kast-1.png", 0.1535, 0.1840),       // size × 1.3
    Kast2("/images/kast-2.png", 0.1512, 0.1833),
    Kast3("/images/kast-3.png", 0.1656, 0.1819),
    Liggend_kast1("/images/liggend-kast-1.png", 0.1523, 0.4403),
    Liggend_kast2("/images/liggend-kast-2.png", 0.1172, 0.4083),
    Koelkast("/images/Koelkast.png", 0.1301, 0.2326);  // size × 1.3

    private final String imagePath;
    private final double width;
    private final double height;

    /**
     * Maakt een nieuw {@code SchapType}-object aan met een afbeeldingspad, breedte en hoogte.
     *
     * @param imagePath het pad naar de afbeelding van dit schaptype
     * @param width     de breedte waarmee de afbeelding wordt getekend
     * @param height    de hoogte waarmee de afbeelding wordt getekend
     */
    SchapType(String imagePath, double width, double height) {
        this.imagePath = imagePath;
        this.width = width;
        this.height = height;
    }

    /**
     * Geeft het afbeeldingspad van dit schaptype terug.
     *
     * @return een {@link String} met het pad naar de afbeelding
     */
    public String getImagePath() {
        return imagePath;
    }

    /**
     * Geeft de breedte van dit schaptype terug.
     *
     * @return de breedte in pixels
     */
    public double getWidth() {
        return width;
    }

    /**
     * Geeft de hoogte van dit schaptype terug.
     *
     * @return de hoogte in pixels
     */
    public double getHeight() {
        return height;
    }
}
