package JGameStudio.Studio.Components;

import java.awt.Color;

import JGamePackage.JGame.Classes.UI.UIFrame;
import JGamePackage.JGame.Types.PointObjects.UDim2;
import JGamePackage.JGame.Types.PointObjects.Vector2;

public class DisplayWindow extends UIFrame {

    public DisplayWindow() {
        double windowWidth = game.WindowService.GetWindowWidth();
        this.Size = UDim2.fromScale((windowWidth-350)/windowWidth, .91);
        this.Position = UDim2.fromAbsolute(0, 105);
        this.BackgroundColor = new Color(46, 46, 46);
        this.MouseTargetable = false;
        this.BackgroundTransparency = 1;

        UIFrame top = new UIFrame();
        top.AnchorPoint = new Vector2(1,0);
        top.Position = UDim2.fromScale(1, 0);
        top.Size = UDim2.fromAbsolute(10, 10);
        top.SetParent(this);

        game.InputService.OnWindowResized.Connect(()->{
            double width = game.WindowService.GetWindowWidth();
            this.Size = UDim2.fromScale((width-350)/width, .91);
        });
    }
}
