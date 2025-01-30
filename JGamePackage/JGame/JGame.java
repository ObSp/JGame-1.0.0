package JGamePackage.JGame;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import JGamePackage.JGame.Classes.Misc.Camera;
import JGamePackage.JGame.Classes.Misc.StorageNode;
import JGamePackage.JGame.Classes.Misc.UINode;
import JGamePackage.JGame.Classes.Misc.WorldNode;
import JGamePackage.JGame.Classes.Rendering.Renderer;
import JGamePackage.JGame.Classes.Services.InputService;
import JGamePackage.JGame.Classes.Services.TimeService;
import JGamePackage.JGame.Classes.Services.WindowService;
import JGamePackage.JGame.Types.Services.ServiceContainer;
import JGamePackage.JGame.Types.StartParams.StartParams;
import JGamePackage.lib.Promise.Promise;
import JGamePackage.lib.Signal.Signal;
import JGamePackage.lib.Signal.SignalWrapper;

public class JGame {
    public static JGame CurrentGame;
    

    //--NUMBERS--//
    public double SecondsPerTick = 1.0/60.0;

    //--BASE NODES--//
    public final WorldNode WorldNode;
    public final UINode UINode;
    public final StorageNode StorageNode;

    //--SIGNALS--//
    private Signal<Double> ontick = new Signal<>();
    private final SignalWrapper<Double> servicesOnTick = new SignalWrapper<>(ontick);

    //--SERVICES--//
    public final ServiceContainer Services;

    public final InputService InputService;
    public final TimeService TimeService;
    public final WindowService WindowService;

    //--CAMERA--//
    public Camera Camera;

    //--RENDERING--//
    private final Renderer renderer;

    //--WINDOW--//
    private JFrame gameWindow;



    //--CONSTRUCTORS--//
    public JGame() {
        this(new StartParams());
    }

    public JGame(StartParams params) {
        JGame.CurrentGame = this;

        WorldNode = new WorldNode();
        UINode = new UINode();
        StorageNode = new StorageNode();

        gameWindow = new JFrame("JGame");

        Services = new ServiceContainer(servicesOnTick);
        InputService = Services.InputService;
        TimeService = Services.TimeService;
        WindowService = Services.WindowService;

        renderer = new Renderer(this);
        Camera = new Camera();

        Promise.await(this.start(params));
    }


    //--INITIALIZATION--//
    private Promise start(StartParams params) {
        return new Promise(self ->{
            if (params.initializeWindow) {
                gameWindow.setSize(500, 500);
                gameWindow.setExtendedState(JFrame.MAXIMIZED_BOTH);
                gameWindow.setIconImage(new ImageIcon("JGamePackage\\JGame\\Assets\\icon.png").getImage());
                gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                gameWindow.setLocationRelativeTo(null);

                gameWindow.setVisible(true);
            }

            
            gameWindow.add(renderer);

            self.resolve();

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            run();
        });
    }


    //--TICK CYCLE--//
    private void run() {
        double lastTick = curSeconds();

        while (true) {
            double curSeconds = curSeconds();
            if (curSeconds-lastTick>=SecondsPerTick) {
                double delta = curSeconds - lastTick;
                lastTick = curSeconds;
                tick(delta);
            }
        }
    }

    private void tick(double dt) {
        ontick.Fire(dt);
        render();
    }

    private void render() {
        gameWindow.repaint();
    }

    private double curSeconds() {
        return ((double)System.currentTimeMillis())/1000;
    }


    //--MISC--//
    public JFrame GetWindow(){
        return gameWindow;
    }
}