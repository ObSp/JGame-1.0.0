package JGamePackage.JGame.Classes.Services;

import JGamePackage.lib.Signal.Signal;
import JGamePackage.lib.Signal.SignalWrapper;

public class TimeService extends Service {
    private double elapsedSeconds = 0;
    private int elapsedTicks = 0;
    
    private Signal<Double> onTickSignal = new Signal<>();
    public SignalWrapper<Double> OnTick = new SignalWrapper<>(onTickSignal);

    public TimeService(SignalWrapper<Double> onTick) {
        onTick.Connect(dt->{
            elapsedSeconds += dt;
            elapsedTicks += 1;

            onTickSignal.Fire(dt);
        });
    }

    public void WaitTicks(int ticks) {
        for (int t = 0; t < ticks; t++) {
            OnTick.Wait();
        }
    }

    public void WaitSeconds(double seconds) {
        double startSeconds = elapsedSeconds;

        while (elapsedSeconds-startSeconds < seconds) {
            OnTick.Wait();
        }
    }

    public int GetElapsedTicks() {
        return elapsedTicks;
    }

    public double GetElapsedSeconds() {
        return elapsedSeconds;
    }
}
