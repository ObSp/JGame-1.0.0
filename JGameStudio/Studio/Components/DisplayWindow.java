package JGameStudio.Studio.Components;

import java.awt.Color;

import JGamePackage.JGame.Classes.UI.UIFrame;
import JGamePackage.JGame.Classes.World.Box2D;
import JGamePackage.JGame.Types.PointObjects.UDim2;

public class DisplayWindow extends UIFrame {

    public DisplayWindow() {
        double windowWidth = game.WindowService.GetWindowWidth();
        this.Size = UDim2.fromScale((windowWidth-350)/windowWidth, .91);
        this.Position = UDim2.fromAbsolute(0, 105);
        this.BackgroundColor = new Color(46, 46, 46);
        this.MouseTargetable = false;
        this.ZIndex = -1;
        this.BackgroundTransparency = 1;

        game.InputService.OnWindowResized.Connect(()->{
            double width = game.WindowService.GetWindowWidth();
            this.Size = UDim2.fromScale((width-350)/width, this.Size.Y.Scale);
        });
    }
}
