package ru.teamentropy.irisrgb.dataflow;

import javafx.scene.paint.Color;
import ru.teamentropy.irisrgb.dataflow.colors.HSV;
import ru.teamentropy.irisrgb.dataflow.colors.RGB;

import java.util.Random;

public class ColorUtils {

    public static RGB hsv2rgb(HSV hsv) {
        RGB rgb = new RGB();

        double hh = hsv.h / 60;
        int i = ((int) hh) % 6;

        double f = hh - i;
        double p = hsv.v * (1 - hsv.s);
        double q = hsv.v * (1 - f * hsv.s);
        double t = hsv.v * (1 - (1 - f) * hsv.s);

        switch (i) {
            case 0:
                rgb.r = hsv.v; rgb.g = t; rgb.b = q; break;
            case 1:
                rgb.r = q; rgb.g = hsv.v; rgb.b = p; break;
            case 2:
                rgb.r = p; rgb.g = hsv.v; rgb.b = t; break;
            case 3:
                rgb.r = p; rgb.g = q; rgb.b = hsv.v; break;
            case 4:
                rgb.r = t; rgb.g = p; rgb.b = hsv.v; break;
            case 5:
                rgb.r = hsv.v; rgb.g = p; rgb.b = q; break;
            default:
        }

        return rgb;
    }

    public Color createRandom(){
        Random rand = new Random();
        return new Color(rand.nextDouble(1.0), rand.nextDouble(1.0), rand.nextDouble(1.0),1.0);
    }

    public Color createRandomHSV(){
        Random rand = new Random();
        double h = rand.nextDouble(1.0);
        double[] rgb;
        rgb = hsvToRgb(h, 1.0, 1.0);
        return new Color(rgb[0], rgb[1], rgb[2], 1.0);
        }

    public Color createFixedHSV(double hue){
        double[] rgb;
        rgb = hsvToRgb(hue, 1.0, 1.0);
        return new Color(rgb[0], rgb[1], rgb[2], 1.0);
    }

}


