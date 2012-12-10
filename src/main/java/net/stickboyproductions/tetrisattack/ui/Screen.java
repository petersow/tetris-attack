package net.stickboyproductions.tetrisattack.ui;

import net.stickboyproductions.tetrisattack.model.Quad;
import net.stickboyproductions.tetrisattack.model.Shape;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.awt.*;
import java.io.InputStream;

import static net.stickboyproductions.tetrisattack.config.ScreenConfig.*;
import static org.lwjgl.opengl.GL11.*;

/**
 * User: Pete
 * Date: 27/10/12
 * Time: 17:32
 */
@Singleton
public class Screen {

  private TextureStore textureStore;

  private UnicodeFont font;

  @Inject
  public Screen(TextureStore textureStore) {
    this.textureStore = textureStore;
  }

  public void create() throws Exception {
    Display.setDisplayMode(new DisplayMode(SCREEN_WIDTH, SCREEN_HEIGHT));
    Display.create();

    // init OpenGL
    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();
    glOrtho(0, SCREEN_WIDTH, 0, SCREEN_HEIGHT, 1, -1);
    glMatrixMode(GL_MODELVIEW);

    glEnable(GL_TEXTURE_2D);
    glBlendFunc(GL_SRC_ALPHA, GL_ONE);
    glEnable(GL_BLEND);

    // Init the font
    Font awtFont = new Font("", Font.PLAIN, 32);
    font = new UnicodeFont(awtFont);
    font.addAsciiGlyphs();
    font.addGlyphs(400, 600);
    font.getEffects().add(new ColorEffect(java.awt.Color.white));
    try {
      font.loadGlyphs();
    } catch (SlickException e) {
    }
    ;

    InputStream inputStream = Screen.class.getResourceAsStream("/fonts/AgentOrange.ttf");
  }

  public void clear() {
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
  }

  public void update() {
    Display.update();
    Display.sync(60);
  }

  public void destroy() {
    Display.destroy();
  }

  public void drawText(String text) {
    font.drawString(500, 500, text, Color.orange);
  }

  public void drawQuad(Quad quad) {
    glPushMatrix();
    GL11.glColor4f(0.5f, 0.5f, 1.0f, 1.0f);

    glBegin(GL_QUADS);
    glVertex2d(quad.getBottomLeft().getX(), quad.getBottomLeft().getY());
    glVertex2d(quad.getBottomRight().getX(), quad.getBottomRight().getY());
    glVertex2d(quad.getTopRight().getX(), quad.getTopRight().getY());
    glVertex2d(quad.getTopLeft().getX(), quad.getTopLeft().getY());
    glEnd();
    glPopMatrix();
  }

  public void drawShape(int x, int y, int offsetX, int offsetY,
                        Shape shape, double framePosition, boolean dark) {

    Quad quad = buildQuad(x, y, offsetX, offsetY);
    drawSprite(quad, shape.name().toLowerCase(), framePosition, dark);
  }

  private Quad buildQuad(int x, int y, int offsetX, int offsetY) {
    return new Quad(new Point((x * CELL_WIDTH) + offsetX, (y * CELL_HEIGHT) + offsetY),
      new Point(((x + 1) * CELL_WIDTH) + offsetX, (y * CELL_HEIGHT) + offsetY),
      new Point(((x + 1) * CELL_WIDTH) + offsetX, ((y + 1) * CELL_HEIGHT) + offsetY),
      new Point((x * CELL_WIDTH) + offsetX, ((y + 1) * CELL_HEIGHT) + offsetY));
  }

  public void drawShape(Quad quad, Shape shape, double framePosition, boolean dark) {
    drawSprite(quad, shape.name().toLowerCase(), framePosition, dark);
  }

  public void drawSprite(Quad quad, String spriteName, double framePosition) {
    drawSprite(quad, spriteName, framePosition, false);
  }

  public void drawSprite(int x, int y, String spriteName, double framePosition) {
    drawSprite(buildQuad(x, y, 0, 0), spriteName, framePosition, false);
  }

  public void drawSprite(Quad quad, String spriteName, double framePosition, boolean dark) {
    glPushMatrix();

    textureStore.get(spriteName).bind();
    glBegin(GL_QUADS);//

    if (dark) {
      glColor4d(0.2, 0.2, 0.2, 1);
    } else {
      glColor4d(1, 1, 1, 1);
    }
    glTexCoord2d(framePosition, 0);
    glVertex2d(quad.getBottomLeft().getX(), quad.getBottomLeft().getY());
    glTexCoord2d(framePosition + 0.25, 0);
    glVertex2d(quad.getBottomRight().getX(), quad.getBottomRight().getY());
    glTexCoord2d(framePosition + 0.25, 1);
    glVertex2d(quad.getTopRight().getX(), quad.getTopRight().getY());
    glTexCoord2d(framePosition, 1);
    glVertex2d(quad.getTopLeft().getX(), quad.getTopLeft().getY());

    glEnd();

    glPopMatrix();
  }
}
