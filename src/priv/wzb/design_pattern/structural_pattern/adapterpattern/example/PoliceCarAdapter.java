package priv.wzb.design_pattern.structural_pattern.adapterpattern.example;

/**
 * @author Satsuki
 * @time 2019/6/18 17:48
 * @description:
 */
public class PoliceCarAdapter extends CarController {
    private PoliceSound sound;
    private PoliceLamp lamp;

    public PoliceCarAdapter() {
        sound = new PoliceSound();
        lamp = new PoliceLamp();
    }

    @Override
    public void phonate() {
        sound.alarmSound();
    }

    @Override
    public void twinkle() {
        lamp.alarmLamp();
    }
}
