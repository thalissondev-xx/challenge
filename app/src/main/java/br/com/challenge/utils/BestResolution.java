package br.com.challenge.utils;

import java.util.List;

import br.com.challenge.models.Images;
import br.com.challenge.models.Resolution;

/**
 * Created by thalissonestrela on 4/17/17.
 */

public class BestResolution {

    static Resolution resolutionAux;
    static int width = Device.getWidth();
    static int diffAux = 50000;

    public static Resolution search(List<Images> images) {
        List<Resolution> resolutions = images.get(0).getResolutions();

        // Add the original source too
        resolutions.add(new Resolution(
                images.get(0).getSource().getUrl(),
                images.get(0).getSource().getWidth(),
                images.get(0).getSource().getHeight()
        ));

        resolutions.forEach(r -> {
            int diff = r.getWidth() - width;

            // Check if is negative
            if (diff < 0) diff *= -1;

            if (diff <= diffAux) {
                diffAux = diff;
                resolutionAux = r;
            }
        });

        return resolutionAux;
    }

}