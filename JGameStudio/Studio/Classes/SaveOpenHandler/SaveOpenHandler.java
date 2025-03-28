package JGameStudio.Studio.Classes.SaveOpenHandler;

import java.util.HashMap;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Classes.Instance;
import JGameStudio.StudioUtil;
import JGameStudio.ProjectHandler.ProjectHandler.ProjectData;
import JGameStudio.Studio.Components.DisplayWindow;
import JGameStudio.Studio.Instances.SelectionBorder;

public class SaveOpenHandler {
    public static JGame game;
    public static DisplayWindow displayWindow;
    public static ProjectData projectData;

    public static void SaveWorldToCurrentFile() {
        Instance[] desc = StudioUtil.ConcatInstanceArrays(game.WorldNode.GetDescendants(),
                displayWindow.GetDescendants(), game.StorageNode.GetDescendants(), game.ScriptNode.GetDescendants());

        for (int i = 0; i < desc.length; i++) {
            if (desc[i] instanceof SelectionBorder) {
                desc[i] = null;
            }
        }

        HashMap<Integer, Instance> options = new HashMap<>();
        options.put(game.SerializationService.NODE_UI_IDENTIFIER, displayWindow);

        game.SerializationService.WriteInstanceArrayToFile(desc, projectData.path() + "\\.jgame\\world.json", options);
    }
}
