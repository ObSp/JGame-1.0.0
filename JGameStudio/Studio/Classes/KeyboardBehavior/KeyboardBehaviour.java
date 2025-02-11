package JGameStudio.Studio.Classes.KeyboardBehavior;

import java.awt.event.KeyEvent;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Classes.Instance;
import JGameStudio.Studio.Modules.Selection;

public class KeyboardBehaviour {
    
    public KeyboardBehaviour(JGame game) {
        game.InputService.OnKeyPress.Connect(kv->{
            if (kv.getKeyCode() == KeyEvent.VK_BACK_SPACE || kv.getKeyCode() == KeyEvent.VK_DELETE) {
                for (Instance v : Selection.get()) {
                    v.Destroy();
                }
            }
        });
    }
}
