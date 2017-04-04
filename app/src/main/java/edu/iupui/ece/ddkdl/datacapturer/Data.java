package edu.iupui.ece.ddkdl.datacapturer;

/**
 * Class that represents the motion data that is to be captured
 * @author Alvaro Esperanca, Veronica D'Agosta
 * @version 0.1
 * @since 2017-01-24
 */

public class Data {
    private double xAcc;
    private double yAcc;
    private double zAcc;

    private double xRot;
    private double yRot;
    private double zRot;



    public Data(){
        this.xAcc = 0;
        this.yAcc = 0;
        this.zAcc = 0;
        this.xRot = 0;
        this.yRot = 0;
        this.zRot = 0;
    }

    public Data(double xAcc, double yAcc, double zAcc, double xRot, double yRot, double zRot) {
        this.xAcc = xAcc;
        this.yAcc = yAcc;
        this.zAcc = zAcc;
        this.xRot = xRot;
        this.yRot = yRot;
        this.zRot = zRot;
    }

    /**
     * @return value of X acceleration
     */
    public double getxAcc() {
        return xAcc;
    }

    /**
     * sets the x acceleration value given by xAcc
     * @param xAcc Acceleration value in m/s^2
     */
    public void setxAcc(double xAcc) {
        this.xAcc = xAcc;
    }

    /**
     * @return value of X acceleration
     */
    public double getyAcc() {
        return yAcc;
    }

    /**
     * @return value of X acceleration
     */
    public void setyAcc(double yAcc) {
        this.yAcc = yAcc;
    }

    /**
     * @return value of X acceleration
     */
    public double getzAcc() {
        return zAcc;
    }

    /**
     * @return value of X acceleration
     */
    public void setzAcc(double zAcc) {
        this.zAcc = zAcc;
    }

    /**
     * @return value of X acceleration
     */
    public double getxRot() {
        return xRot;
    }

    /**
     * @return value of X acceleration
     */
    public void setxRot(double xRot) {
        this.xRot = xRot;
    }

    /**
     * @return value of X acceleration
     */
    public double getyRot() {
        return yRot;
    }

    /**
     * @return value of X acceleration
     */
    public void setyRot(double yRot) {
        this.yRot = yRot;
    }

    /**
     * @return value of X acceleration
     */
    public double getzRot() {
        return zRot;
    }

    /**
     * @return value of X acceleration
     */
    public void setzRot(double zRot) {
        this.zRot = zRot;
    }

    public String toString(){
        return "x acc: " + xAcc + " y acc: " + yAcc + " z acc: " + zAcc + " x rot: " + xRot + " y rot: " + yRot + " z rot: " + zRot;
    }

    public double[] getArray(){
        return new double[]{xAcc, yAcc, zAcc, xRot, yRot, zRot};
    }
}

