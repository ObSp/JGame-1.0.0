package JGamePackage.JGame;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import JGamePackage.JGame.Classes.Misc.Camera;
import JGamePackage.JGame.Classes.Misc.StorageNode;
import JGamePackage.JGame.Classes.Misc.UINode;
import JGamePackage.JGame.Classes.Misc.WorldNode;
import JGamePackage.JGame.Classes.Rendering.Renderer;
import JGamePackage.JGame.Types.Services.ServiceContainer;
import JGamePackage.lib.Promise.Promise;
import JGamePackage.lib.Signal.Signal;
import JGamePackage.lib.Signal.SignalWrapper;

public class JGame {
    public static JGame CurrentGame;
    

    //--NUMBERS--//
    public double TicksPerSecond = 0.016;

    //--BASE NODES--//
    public final WorldNode WorldNode;
    public final UINode UINode;
    public final StorageNode StorageNode;

    //--SIGNALS--//
    private Signal<Double> ontick = new Signal<>();
    private final SignalWrapper<Double> servicesOnTick = new SignalWrapper<>(ontick);

    //--SERVICES--//
    public final ServiceContainer Services;

    //--CAMERA--//
    public Camera Camera;

    //--RENDERING--//
    private final Renderer renderer;

    //--WINDOW--//
    private JFrame gameWindow;



    //--CONSTRUCTORS--//
    public JGame() {
        JGame.CurrentGame = this;

        WorldNode = new WorldNode();
        UINode = new UINode();
        StorageNode = new StorageNode();

        Services = new ServiceContainer(servicesOnTick);
        renderer = new Renderer(this);
        Camera = new Camera();

        Promise.await(this.start());
    }


    //--INITIALIZATION--//
    private Promise start() {
        return new Promise(self ->{
            gameWindow = new JFrame("JGame");

            gameWindow.setExtendedState(JFrame.MAXIMIZED_BOTH);
            gameWindow.setIconImage(new ImageIcon("JGamePackage\\JGame\\Assets\\icon.png").getImage());
            gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            gameWindow.add(renderer);

            gameWindow.setVisible(true);

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
            if (curSeconds-lastTick>=TicksPerSecond) {
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