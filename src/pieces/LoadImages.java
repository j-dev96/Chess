package pieces;

import javax.imageio.ImageIO;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;


public final class LoadImages {

    private static final Map<String, Image> images = new HashMap<>();

    private LoadImages() {
    }

    public static Image getTileImage(final String name) {
        Image image = images.get(name);
        if (image != null) {
            return image;
        }

        String file = name+".png";

        try {
            Image i = ImageIO.read(LoadImages.class.getResource(file));
            images.put(name, i);
            return i;
        } catch (java.io.IOException e) {
        } catch (IllegalArgumentException e) {
        }
        return null;
    }
}
