package JGameStudio.Studio.Classes.WorldNavigation;

import java.awt.Cursor;
import java.awt.event.KeyEvent;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Types.PointObjects.Vector2;

public class WorldNavi {
    private JGame game;

    private boolean dragging = false;

    private void initScrollBehavior() {
        game.InputService.OnMouseScroll.Connect(dir -> {
            if (game.InputService.IsMouse3Down() || !game.InputService.IsMouseInUIBaseBounds(game.UINode.GetChild("DisplayWindow"))) return;
            double factor = ((double) dir*.1);

            game.Camera.DepthFactor += factor;
        });
    }

    private void initMiddleWheelBehavior() {
        game.TimeService.OnTick.Connect(dt->{
            if (!game.InputService.IsMouse3Down()) {
                if (dragging) {
                    dragging = false;
                    game.WindowService.SetMouseCursor(Cursor.getDefaultCursor().getType());
                }
                return;
            }

            if (!dragging) {
                dragging = true;
                game.WindowService.SetMouseCursor(Cursor.MOVE_CURSOR);
            }

            game.Camera.Position = game.Camera.Position.add(game.InputService.GetMouseDelta().multiply(-game.Camera.DepthFactor));
        });
    }

    private void initWASDBehavior() {
        game.TimeService.OnTick.Connect(dt->{
            if (game.InputService.IsTyping() || game.InputService.IsKeyDown(KeyEvent.VK_CONTROL)) return;
            game.Camera.Position = game.Camera.Position.add(new Vector2(game.InputService.GetInputHorizontal(), -game.InputService.GetInputVertical()).multiply(5));
        });
    }

    public WorldNavi() {
        game = JGame.CurrentGame;

        initMiddleWheelBehavior();
        initScrollBehavior();
        initWASDBehavior();
    }
}
