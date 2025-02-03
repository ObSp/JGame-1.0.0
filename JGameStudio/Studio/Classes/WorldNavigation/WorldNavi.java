package JGameStudio.Studio.Classes.WorldNavigation;

import java.awt.Cursor;

import JGamePackage.JGame.JGame;

public class WorldNavi {
    private JGame game;

    private boolean dragging = false;

    private void initScrollBehavior() {
        game.InputService.OnMouseScroll.Connect(dir -> {
            if (game.InputService.IsMouse3Down()) return;
            double factor = ((double) dir*.15);

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

    public WorldNavi() {
        game = JGame.CurrentGame;

        initMiddleWheelBehavior();
        initScrollBehavior();
    }
}
