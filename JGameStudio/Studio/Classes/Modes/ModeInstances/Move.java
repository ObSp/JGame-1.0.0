package JGameStudio.Studio.Classes.Modes.ModeInstances;

import java.util.ArrayList;

import JGamePackage.JGame.Classes.Instance;
import JGamePackage.JGame.Classes.Rendering.Renderable;
import JGamePackage.JGame.Classes.UI.UIBase;
import JGamePackage.JGame.Classes.UI.UIImageButton;
import JGamePackage.JGame.Classes.World.WorldBase;
import JGamePackage.JGame.Types.Constants.Constants;
import JGamePackage.JGame.Types.PointObjects.UDim2;
import JGamePackage.JGame.Types.PointObjects.Vector2;
import JGameStudio.StudioGlobals;
import JGameStudio.Studio.Classes.Modes.Mode;
import JGameStudio.Studio.Modules.Selection;

public class Move extends Mode {
    private UIImageButton arrowLeft;
    private UIImageButton arrowTop;
    private UIImageButton arrowRight;
    private UIImageButton arrowBottom;

    private UIImageButton selectedArrow;

    private ArrayList<UIImageButton> arrows = new ArrayList<>();

    private void positionArrows() {
        Instance selectedInstance = Selection.getFirst();

        if (!selected || selectedInstance == null) {
            arrowLeft.Visible = false;
            arrowTop.Visible = false;
            arrowRight.Visible = false;
            arrowBottom.Visible = false;
        } else {
            arrowLeft.Visible = true;
            arrowTop.Visible = true;
            arrowRight.Visible = true;
            arrowBottom.Visible = true;
        }

        if (!(selectedInstance instanceof Renderable)) return;

        if (selectedInstance instanceof UIBase) {
            UIBase ui = (UIBase) selectedInstance;

            Vector2 absSize = ui.GetAbsoluteSize();
            Vector2 absPos = ui.GetAbsolutePosition();

            arrowLeft.Position = UDim2.fromAbsolute(absPos.X, absPos.Y + absSize.Y/2);
            arrowRight.Position = UDim2.fromAbsolute(absPos.X + absSize.X, absPos.Y + absSize.Y/2);
            arrowBottom.Position = UDim2.fromAbsolute(absPos.X + absSize.X/2, absPos.Y + absSize.Y);
            arrowTop.Position = UDim2.fromAbsolute(absPos.X + absSize.X/2, absPos.Y);
        } else if (selectedInstance instanceof WorldBase) {
            WorldBase worldBase = (WorldBase) selectedInstance;

            Vector2 absSize = worldBase.GetRenderSize();
            Vector2 absPos = worldBase.GetRenderPosition();

            arrowLeft.Position = UDim2.fromAbsolute(absPos.X, absPos.Y + absSize.Y/2);
            arrowRight.Position = UDim2.fromAbsolute(absPos.X + absSize.X, absPos.Y + absSize.Y/2);
            arrowBottom.Position = UDim2.fromAbsolute(absPos.X + absSize.X/2, absPos.Y + absSize.Y);
            arrowTop.Position = UDim2.fromAbsolute(absPos.X + absSize.X/2, absPos.Y);
        }

        if (selectedArrow != null) {
            StudioGlobals.ModeHandler.dragMode.DragMult = (selectedArrow.GetCProp("Dir") == Constants.Vector2Axis.X) ? Vector2.right : Vector2.down;
        }
    }

    public Move() {
        arrowLeft = new UIImageButton();
        arrowLeft.SetCProp("Dir", Constants.Vector2Axis.X);
        arrowLeft.BackgroundTransparency = 1;
        arrowLeft.SetImage("JGameStudio\\Assets\\Icons\\arrowX.png");
        arrowLeft.Size = UDim2.fromAbsolute(60, 25);
        arrowLeft.ZIndex = 1;
        arrowLeft.Rotation = Math.toRadians(180);
        arrowLeft.AnchorPoint = new Vector2(1, .5);
        arrowLeft.SetParent(game.UINode);

        arrowRight = arrowLeft.Clone();
        arrowRight.Rotation = 0;
        arrowRight.SetCProp("Dir", Constants.Vector2Axis.X);
        arrowRight.Position = UDim2.fromAbsolute(100, 0);
        arrowRight.AnchorPoint = new Vector2(0, .5); 
        arrowRight.SetParent(game.UINode);

        arrowBottom = arrowRight.Clone();
        arrowBottom.Size = UDim2.fromAbsolute(25, 60);
        arrowBottom.SetImage("JGameStudio\\Assets\\Icons\\arrowY.png");
        arrowBottom.AnchorPoint = new Vector2(.5, 0);
        arrowBottom.SetCProp("Dir", Constants.Vector2Axis.Y);
        arrowBottom.SetParent(game.UINode);

        arrowTop = arrowBottom.Clone();
        arrowTop.Rotation = Math.toRadians(180);
        arrowTop.AnchorPoint = new Vector2(.5, 1);
        arrowTop.SetCProp("Dir", Constants.Vector2Axis.Y);
        arrowTop.SetParent(game.UINode);

        arrows.add(arrowLeft);
        arrows.add(arrowRight);
        arrows.add(arrowTop);
        arrows.add(arrowBottom);

        game.TimeService.OnTick.Connect(dt->{
            positionArrows();
        });

        game.InputService.OnMouse1Click.Connect(()->{
            UIBase mouseUITarget = game.InputService.GetMouseUITarget();
            if (arrows.contains(mouseUITarget)) {
                selectedArrow = (UIImageButton) mouseUITarget;
            } else {
                selectedArrow = null;
                StudioGlobals.ModeHandler.dragMode.DragMult = Vector2.one;
            }
        });
    }

}
