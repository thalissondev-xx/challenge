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

        int width = Global.getInstance().getDeviceWidth();
        int diffAux = 50000;

        Resolution sourceResolution = new Resolution();
        sourceResolution.setUrl(images.get(0).getSource().getUrl());
        sourceResolution.setWidth(images.get(0).getSource().getWidth());
        sourceResolution.setHeight(images.get(0).getSource().getHeight());

        // Add the original source too
        resolutions.add(sourceResolution);

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
