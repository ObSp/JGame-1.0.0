package JGameStudio.Studio.Classes.MouseBehavior;

import java.awt.event.KeyEvent;
import java.util.Arrays;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Classes.Instance;
import JGamePackage.JGame.Classes.UI.UIBase;
import JGamePackage.JGame.Classes.World.WorldBase;
import JGameStudio.Studio.Components.DisplayWindow;
import JGameStudio.Studio.Components.Explorer;
import JGameStudio.Studio.Modules.Selection;

public class MouseBehaviorHandler {
    private JGame game;

    private void selectInstance(Instance v) {
        boolean ctrlDown = game.InputService.IsKeyDown(KeyEvent.VK_CONTROL);

        if (ctrlDown) {
            if (Selection.contains(v)) {
                Selection.remove(v);
            } else {
                Selection.add(v);
            }
        } else {
            Selection.set(v);
        }
    }

    private void initMouseSelection() {
        Explorer explorer = game.UINode.GetChild("Sidebar").GetChildOfClass(Explorer.class);
        DisplayWindow uiWindow = game.UINode.GetChildOfClass(DisplayWindow.class);

        game.InputService.OnMouse1Click.Connect(()->{
            UIBase uiTarget = game.InputService.GetMouseUITarget();
            if (uiTarget == null) {
                WorldBase worldTarget = game.InputService.GetMouseWorldTarget();
                if (worldTarget != null) {
                    selectInstance(worldTarget);
                } else {
                    Selection.clear();
                }
                return;
            }

            if (uiTarget.Name == "DropdownArrow" || (!uiTarget.IsDescendantOf(explorer) && !uiTarget.IsDescendantOf(uiWindow))) {
                return;
            }

            if (uiTarget.IsDescendantOf(explorer)) {
                Selection.clear();
                return;
            }

            System.out.println("select ui");
            selectInstance(uiTarget);
        });
    }

    public MouseBehaviorHandler() {
        game = JGame.CurrentGame;

        initMouseSelection();
    }
}
