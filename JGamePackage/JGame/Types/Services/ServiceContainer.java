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
        InputService = new InputService();
        TimeService = new TimeService();
        WindowService = new WindowService(onTick);

        Services = new Service[] {InputService, TimeService, WindowService};
    }
}
