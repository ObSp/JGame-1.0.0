package JGameStudio.Studio.Classes.Modes.ModeInstances;

import JGamePackage.JGame.Classes.Instance;
import JGamePackage.JGame.Classes.Rendering.Renderable;
import JGamePackage.JGame.Classes.UI.UIBase;
import JGamePackage.JGame.Classes.UI.UIImageButton;
import JGamePackage.JGame.Types.PointObjects.UDim2;
import JGamePackage.JGame.Types.PointObjects.Vector2;
import JGameStudio.Studio.Classes.Modes.Mode;
import JGameStudio.Studio.Modules.Selection;

public class Move extends Mode {
    private UIImageButton arrowLeft;
    private UIImageButton arrowTop;
    private UIImageButton arrowRight;
    private UIImageButton arrowBottom;

    private void positionArrows() {
        Instance selected = Selection.getFirst();
        if (!(selected instanceof Renderable)) return;

        if (selected instanceof UIBase) {
            UIBase ui = (UIBase) selected;

            Vector2 absSize = ui.GetAbsoluteSize();
            Vector2 absPos = ui.GetAbsolutePosition();

            arrowLeft.Position = UDim2.fromAbsolute(absPos.X, absPos.Y + absSize.Y/2);
            arrowRight.Position = UDim2.fromAbsolute(absPos.X + absSize.X, absPos.Y + absSize.Y/2);
            arrowBottom.Position = UDim2.fromAbsolute(absPos.X + absSize.X/2, absPos.Y + absSize.Y);
            arrowTop.Position = UDim2.fromAbsolute(absPos.X + absSize.X/2, absPos.Y);
        }
    }

    public Move() {
        arrowLeft = new UIImageButton();
        arrowLeft.BackgroundTransparency = 1;
        arrowLeft.SetImage("JGameStudio\\Assets\\Icons\\arrowX.png");
        arrowLeft.Size = UDim2.fromAbsolute(60, 25);
        arrowLeft.ZIndex = 100;
        arrowLeft.Rotation = Math.toRadians(180);
        arrowLeft.AnchorPoint = new Vector2(1, .5);
        arrowLeft.SetParent(game.UINode);

        arrowRight = arrowLeft.Clone();
        arrowRight.Rotation = 0;
        arrowRight.Position = UDim2.fromAbsolute(100, 0);
        arrowRight.AnchorPoint = new Vector2(0, .5); 
        arrowRight.SetParent(game.UINode);

        arrowBottom = arrowRight.Clone();
        arrowBottom.Size = UDim2.fromAbsolute(25, 60);
        arrowBottom.SetImage("JGameStudio\\Assets\\Icons\\arrowY.png");
        arrowBottom.AnchorPoint = new Vector2(.5, 0);
        arrowBottom.SetParent(game.UINode);

        arrowTop = arrowBottom.Clone();
        arrowTop.Rotation = Math.toRadians(180);
        arrowTop.AnchorPoint = new Vector2(.5, 1);
        arrowTop.SetParent(game.UINode);


        game.TimeService.OnTick.Connect(dt->{
            positionArrows();
        });
    }

}
