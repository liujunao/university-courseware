package game.views;

import game.controller.GameController;
import game.model.Resources;

import javax.swing.*;
import java.awt.*;

/**
 * @author lenovo
 * @date 2017/11/21
 */
public class GameFrame extends JFrame {

    public GameFrame(int stage) {
        GameController controller = new GameController(stage);
        controller.setFocusable(true);
        controller.requestFocus();
        this.add(controller, BorderLayout.CENTER);
        setLocationRelativeTo(null);
        setSize(1000, 600);
        setIconImage(Resources.tombPNG);
        setTitle("fly_free");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
