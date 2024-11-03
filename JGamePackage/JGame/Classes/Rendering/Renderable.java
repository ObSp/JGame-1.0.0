package JGamePackage.JGame.Classes.Rendering;

import java.awt.Graphics2D;

import JGamePackage.JGame.Classes.Instance;
import JGamePackage.JGame.Types.PointObjects.BasePoint;

public abstract class Renderable extends Instance {
    public BasePoint Position;
    public BasePoint Size;
    
    public double Rotation = 0;

    public int ZIndex = 0;

    public abstract void render(Graphics2D graphics);
}
