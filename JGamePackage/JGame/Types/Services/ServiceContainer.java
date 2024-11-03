package JGamePackage.JGame.Types.Services;

import JGamePackage.JGame.Classes.Services.InputService;
import JGamePackage.JGame.Classes.Services.Service;
import JGamePackage.JGame.Classes.Services.TimeService;
import JGamePackage.JGame.Classes.Services.WindowService;
import JGamePackage.lib.Signal.SignalWrapper;

public class ServiceContainer {
    public final Service[] Services;

    public final InputService InputService;
    public final TimeService TimeService;
    public final WindowService WindowService;

    public ServiceContainer(SignalWrapper<Double> onTick) {
        TimeService = new TimeService(onTick);
        WindowService = new WindowService(onTick);
        InputService = new InputService();

        Services = new Service[] {InputService, TimeService, WindowService};
    }
}
