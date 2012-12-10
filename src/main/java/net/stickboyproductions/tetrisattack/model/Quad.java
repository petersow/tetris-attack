package net.stickboyproductions.tetrisattack.model;

import java.awt.*;

/**
 * A POJO to represent 4 points that make up a quad
 *
 * User: Pete
 * Date: 28/10/12
 * Time: 15:24
 */
public class Quad {

  Point bottomLeft;
  Point bottomRight;
  Point topRight;
  Point topLeft;

  public Quad(Point bottomLeft, Point bottomRight, Point topRight, Point topLeft) {
    this.bottomLeft = bottomLeft;
    this.bottomRight = bottomRight;
    this.topRight = topRight;
    this.topLeft = topLeft;
  }

  public void offset(int xOffset, int yOffset) {
    bottomLeft.setLocation(bottomLeft.getX() + xOffset, bottomLeft.getY() + yOffset);
    bottomRight.setLocation(bottomRight.getX() + xOffset, bottomRight.getY() + yOffset);
    topRight.setLocation(topRight.getX() + xOffset, topRight.getY() + yOffset);
    topLeft.setLocation(topLeft.getX() + xOffset, topLeft.getY() + yOffset);
  }

  public Quad getWithOffset(int xOffset, int yOffset) {
    return new Quad(new Point((int) bottomLeft.getX() + xOffset,(int) bottomLeft.getY() + yOffset),
    new Point((int) bottomRight.getX() + xOffset, (int) bottomRight.getY() + yOffset),
    new Point((int) topRight.getX() + xOffset, (int) topRight.getY() + yOffset),
    new Point((int) topLeft.getX() + xOffset, (int) topLeft.getY() + yOffset));
  }

  public Point getBottomLeft() {
    return bottomLeft;
  }

  public void setBottomLeft(Point bottomLeft) {
    this.bottomLeft = bottomLeft;
  }

  public Point getBottomRight() {
    return bottomRight;
  }

  public void setBottomRight(Point bottomRight) {
    this.bottomRight = bottomRight;
  }

  public Point getTopRight() {
    return topRight;
  }

  public void setTopRight(Point topRight) {
    this.topRight = topRight;
  }

  public Point getTopLeft() {
    return topLeft;
  }

  public void setTopLeft(Point topLeft) {
    this.topLeft = topLeft;
  }
}
