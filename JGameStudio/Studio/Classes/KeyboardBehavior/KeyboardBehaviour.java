package JGameStudio.Studio.Classes.KeyboardBehavior;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Classes.Instance;
import JGameStudio.Studio.Modules.Selection;

public class KeyboardBehaviour {
    ArrayList<String> destroyIgnore = new ArrayList<>(Arrays.asList("WorldNode", "DisplayWindow", "UINode", "ScriptNode", "StorageNode", "Camera"));
    
    public KeyboardBehaviour(JGame game) {
        game.InputService.OnKeyPress.Connect(kv->{
            if (game.InputService.IsTyping()) return;
            if (kv.getKeyCode() == KeyEvent.VK_BACK_SPACE || kv.getKeyCode() == KeyEvent.VK_DELETE) {
                for (Instance v : Selection.get()) {
                    if (destroyIgnore.contains(v.getClass().getSimpleName())) continue;
                    v.Destroy();
                }
            }
        });
    }
}
