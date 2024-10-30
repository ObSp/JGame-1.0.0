package JGamePackage.JGame.Classes.Services;

import java.awt.Color;

import javax.swing.ImageIcon;

import JGamePackage.JGame.Types.PointObjects.Vector2;
import JGamePackage.lib.Signal.SignalWrapper;

public class WindowService extends Service {
    public Color BackgroundColor = Color.gray;

    private Vector2 screenSize;

    public WindowService(SignalWrapper<Double> onTick) {
        onTick.Connect(dt->{
            screenSize = new Vector2(GetScreenWidth(), GetScreenHeight());
        });
    }

    public Vector2 GetScreenSize(){
        return screenSize != null ? screenSize : new Vector2(GetScreenWidth(), GetScreenHeight());
    }

    public int GetScreenHeight(){
        return game.GetWindow().getContentPane().getHeight();
    }

    public int GetScreenWidth(){
        return game.GetWindow().getContentPane().getWidth();
    }

    public void SetWindowTitle(String newTitle){
        game.GetWindow().setTitle(newTitle);
    }

    public void SetWindowIcon(String path){
        game.GetWindow().setIconImage(new ImageIcon(path).getImage());
    }
}
