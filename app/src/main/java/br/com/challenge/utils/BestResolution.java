package br.com.challenge.utils;

import java.util.List;

import br.com.challenge.models.Images;
import br.com.challenge.models.Resolution;

/**
 * Created by thalissonestrela on 4/17/17.
 */

public class BestResolution {

    public static Resolution search(List<Images> images) {
        List<Resolution> resolutions = images.get(0).getResolutions();
        Resolution resolutionAux = null;

        int width = Device.getWidth();
        int diffAux = 50000;

        for (Resolution r : resolutions) {
            int diff = r.getWidth() - width;

            // Negative?
            if (diff < 0) {
                diff *= -1;
            }

            if (diff <= diffAux) {
                diffAux = diff;
                resolutionAux = r;
            }
        }

        return resolutionAux;
    }

}
