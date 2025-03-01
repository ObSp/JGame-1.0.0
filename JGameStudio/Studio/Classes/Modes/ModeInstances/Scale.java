package JGameStudio.Studio.Classes.Modes.ModeInstances;

import java.awt.Color;
import java.util.ArrayList;

import JGamePackage.JGame.Classes.Instance;
import JGamePackage.JGame.Classes.Rendering.Renderable;
import JGamePackage.JGame.Classes.UI.UIBase;
import JGamePackage.JGame.Classes.UI.UIButton;
import JGamePackage.JGame.Classes.World.WorldBase;
import JGamePackage.JGame.Types.PointObjects.UDim2;
import JGamePackage.JGame.Types.PointObjects.Vector2;
import JGameStudio.StudioGlobals;
import JGameStudio.Studio.Classes.Modes.Mode;
import JGameStudio.Studio.Modules.Selection;

public class Scale extends Mode {

    private UIButton boxTopLeft;
    private UIButton boxTopRight;
    private UIButton boxBottomLeft;
    private UIButton boxBottomRight;

    private UIButton selectedBox;

    private ArrayList<UIButton> boxes = new ArrayList<>();

    private void setBoxPositions(Renderable selectedInstance) {
        if (selectedInstance instanceof UIBase) {
            UIBase ui = (UIBase) selectedInstance;

            Vector2 absSize = ui.GetAbsoluteSize();
            Vector2 absPos = ui.GetAbsolutePosition();

            boxTopLeft.Position = UDim2.fromAbsolute(absPos.X, absPos.Y);
            boxTopRight.Position = UDim2.fromAbsolute(absPos.X + absSize.X, absPos.Y);
            boxBottomLeft.Position = UDim2.fromAbsolute(absPos.X, absPos.Y + absSize.Y);
            boxBottomRight.Position = UDim2.fromAbsolute(absPos.X + absSize.X, absPos.Y + absSize.Y);

        } else if (selectedInstance instanceof WorldBase) {
            WorldBase worldBase = (WorldBase) selectedInstance;

            Vector2 absSize = worldBase.GetRenderSize();
            Vector2 absPos = worldBase.GetRenderPosition();

            boxTopLeft.Position = UDim2.fromAbsolute(absPos.X, absPos.Y);
            boxTopRight.Position = UDim2.fromAbsolute(absPos.X + absSize.X, absPos.Y);
            boxBottomLeft.Position = UDim2.fromAbsolute(absPos.X, absPos.Y + absSize.Y);
            boxBottomRight.Position = UDim2.fromAbsolute(absPos.X + absSize.X, absPos.Y + absSize.Y);
        }
    }

    private void updateSelectedSize(Renderable selectedInstance) {
        Vector2 mouseWorldDelta = game.InputService.GetMouseWorldDelta();

        if (selectedBox == boxBottomRight) {

            if (selectedInstance instanceof WorldBase) {

                WorldBase inst = (WorldBase)selectedInstance;
                inst.Position = inst.Position.add(inst.Pivot.multiply(mouseWorldDelta));
                inst.Size = inst.Size.add(mouseWorldDelta);

            }

        } else if (selectedBox == boxTopLeft) {

            if (selectedInstance instanceof WorldBase) {
                mouseWorldDelta = mouseWorldDelta.negative();

                WorldBase inst = (WorldBase)selectedInstance;
                inst.Position = inst.Position.add(inst.Pivot.multiply(mouseWorldDelta));
                inst.Size = inst.Size.add(mouseWorldDelta);
                inst.Position = inst.Position.subtract(mouseWorldDelta);

            }

        } else if (selectedBox == boxBottomLeft) {

            if (selectedInstance instanceof WorldBase) {
                mouseWorldDelta = new Vector2(-mouseWorldDelta.X, mouseWorldDelta.Y);

                WorldBase inst = (WorldBase)selectedInstance;
                inst.Position = inst.Position.add(inst.Pivot.multiply(mouseWorldDelta));
                inst.Size = inst.Size.add(mouseWorldDelta);
                inst.Position = inst.Position.subtract(new Vector2(mouseWorldDelta.X, 0));

            }

        } else if (selectedBox == boxTopRight) {

            if (selectedInstance instanceof WorldBase) {
                mouseWorldDelta = new Vector2(mouseWorldDelta.X, -mouseWorldDelta.Y);

                WorldBase inst = (WorldBase) selectedInstance;
                inst.Position = inst.Position.add(inst.Pivot.multiply(mouseWorldDelta));
                inst.Size = inst.Size.add(mouseWorldDelta);
                inst.Position = inst.Position.subtract(new Vector2(0, mouseWorldDelta.Y));
            }

        }
    }

    private void updateBoxVisibility(Instance selectedInstance) {
        if (!selected || selectedInstance == null) {
            boxTopLeft.Visible = false;
            boxTopRight.Visible = false;
            boxBottomLeft.Visible = false;
            boxBottomRight.Visible = false;
        } else {
            boxTopLeft.Visible = true;
            boxTopRight.Visible = true;
            boxBottomLeft.Visible = true;
            boxBottomRight.Visible = true;
        }
    }

    private void onStep() {
        Instance selectedInstance = Selection.getFirst();

        updateBoxVisibility(selectedInstance);

        if (!(selectedInstance instanceof Renderable)) return;

        if (selectedBox != null && game.InputService.IsMouse1Down()) {
            updateSelectedSize((Renderable) selectedInstance);
        }

        setBoxPositions((Renderable) selectedInstance);
    }

    public Scale() {
        boxTopLeft = new UIButton();
        boxTopLeft.Size = UDim2.fromAbsolute(17, 17);
        boxTopLeft.AnchorPoint = Vector2.half;
        boxTopLeft.BackgroundColor = Color.blue;
        boxTopLeft.Visible = false;
        boxTopLeft.SetParent(game.UINode);

        boxTopRight = boxTopLeft.Clone();
        boxTopRight.BackgroundColor = Color.red;
        boxTopRight.SetParent(game.UINode);

        boxBottomLeft = boxTopRight.Clone();
        boxBottomLeft.BackgroundColor = Color.red;
        boxBottomLeft.SetParent(game.UINode);

        boxBottomRight = boxBottomLeft.Clone();
        boxBottomRight.BackgroundColor = Color.blue;
        boxBottomRight.SetParent(game.UINode);

        boxes.add(boxTopLeft);
        boxes.add(boxTopRight);
        boxes.add(boxBottomLeft);
        boxes.add(boxBottomRight);

        game.TimeService.OnTick.Connect(dt->{
            onStep();
        });

        game.InputService.OnMouse1Click.Connect(()->{
            UIBase target = game.InputService.GetMouseUITarget();
            if (boxes.contains(target)) {
                selectedBox = (UIButton) target;
                StudioGlobals.ModeHandler.dragMode.DragMult = Vector2.zero;
            } else {
                selectedBox = null;
                StudioGlobals.ModeHandler.dragMode.DragMult = Vector2.one;
            }
        });
    }

}
