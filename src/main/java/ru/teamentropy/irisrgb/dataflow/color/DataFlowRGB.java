package ru.teamentropy.irisrgb.dataflow.color;

import javafx.scene.paint.Color;

public class DataFlowRGB {
    private int red;
    private int green;
    private int blue;

    public DataFlowRGB(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public DataFlowRGB(double[] normalizedColor){
        this.red = (int) (normalizedColor[0] * 255);
        this.green = (int) (normalizedColor[1] * 255);
        this.blue = (int) (normalizedColor[2] * 255);
    }

    public DataFlowRGB(double redRatio, double greenRatio, double blueRatio){
        this.red = (int) (redRatio * 255);
        this.green = (int) (greenRatio * 255);
        this.blue = (int) (blueRatio * 255);
    }

    public DataFlowRGB(float redRatio, float greenRatio, float blueRatio){
        this.red = (int) (redRatio * 255);
        this.green = (int) (greenRatio * 255);
        this.blue = (int) (blueRatio * 255);
    }

    public int getRed() {
        return red;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public int getGreen() {
        return green;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public int getBlue() {
        return blue;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }

    private DataFlowRGB fx2DF(Color color){
        return new DataFlowRGB((int)(color.getRed() * 255), (int)(color.getRed() * 255), (int)(color.getRed() * 255));
    }

    private Color DF2fx(DataFlowRGB color){
        return new Color((double) color.getRed() / 255, (double) color.getRed() / 255, (double) color.getRed() / 255, 1.0D);
    }

    public double[] normalize(){

        double[] output = new double[3];
        output[0] = (double) this.getRed() / 255;
        output[1] = (double) this.getGreen() / 255;
        output[2] = (double) this.getBlue() / 255;

        return output;
    }

    public byte[] toBytes(){
        return (this.getRed() + " " + this.getGreen() + " " +
                this.getBlue() + " ").getBytes();
    }

    public DataFlowRGB interpolate(DataFlowRGB flowRGB, double ratio) {
        double[] input = flowRGB.normalize();
        double[] curColor = this.normalize();

        if (ratio <= 0.0) {
            return this;
        } else if (ratio >= 1.0) {
            return flowRGB;
        } else {
            float var4 = (float)ratio;
            return new DataFlowRGB(curColor[0] + (input[0] - curColor[0]) * var4,
                    curColor[1] + (input[1] - curColor[1]) * var4,
                    curColor[2] + (input[2] - curColor[2]) * var4);
        }
    }


}
