package JGameStudio.Studio.Classes.Modes.ModeInstances;

import JGamePackage.JGame.Classes.Instance;
import JGamePackage.JGame.Classes.UI.UIBase;
import JGamePackage.JGame.Classes.World.WorldBase;
import JGamePackage.JGame.Types.PointObjects.UDim2;
import JGamePackage.JGame.Types.PointObjects.Vector2;
import JGameStudio.Studio.Classes.Modes.Mode;
import JGameStudio.Studio.Modules.Selection;
import JGameStudio.Studio.Modules.Util;

public class Drag extends Mode {
    private boolean dragging = false;

    public Vector2 DragMult = Vector2.one;

    private void moveCurrentElement() {
        if (!dragging || !selected) return;

        Vector2 mouseDeltaRaw = game.InputService.GetMouseDelta().multiply(DragMult);
        Vector2 mouseDeltaWorld = game.InputService.GetMouseWorldDelta().multiply(DragMult);

        for (Instance selected : Selection.get()) {
            if (selected instanceof WorldBase) {
                WorldBase worldInst = (WorldBase) selected;
                worldInst.Position = worldInst.Position.add(mouseDeltaWorld);
            } else if (selected instanceof UIBase) {
                UIBase uiInst = (UIBase) selected;

                if (Util.shouldUseScaleForUDim2Transformations(uiInst.Position)) {

                } else {
                    uiInst.Position = uiInst.Position.add(UDim2.fromAbsolute(mouseDeltaRaw.X, mouseDeltaRaw.Y));
                }
            }
        }
    }

    public Drag() {
        game.TimeService.OnTick.Connect(dt->{
            moveCurrentElement();
        });

        game.InputService.OnMouse1Click.Connect(()->{
            dragging = true;
        });

        game.InputService.OnMouse1Up.Connect(()->{
            dragging = false;
        });
    }

}
