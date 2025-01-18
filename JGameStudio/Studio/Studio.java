package JGameStudio.Studio;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Types.StartParams.StartParams;
import JGameStudio.StudioGlobals;
import JGameStudio.Studio.Pages.Init;

public class Studio {
    JGame game = new JGame(new StartParams(false));
    public Studio() {
        StudioGlobals.construct();
        Init.showLoadingFrame(game).Wait();
    }

}
