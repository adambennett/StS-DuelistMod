package duelistmod.dto;

import com.megacrit.cardcrawl.orbs.AbstractOrb;
import duelistmod.DuelistMod;
import duelistmod.helpers.RandomOrbHelper;
import duelistmod.metrics.builders.JsonToStringBuilder;

import java.util.ArrayList;
import java.util.List;

public class DuelistOrbInfo {

    private String orb_id;
    private String name;
    private String passive;
    private String evoke;
    private String invert;
    private String version;

    public DuelistOrbInfo() {}

    public DuelistOrbInfo(String id, String name, String passive, String evoke, String invert, String version) {
        this.orb_id = id;
        this.name = name;
        this.passive = passive;
        this.evoke = evoke;
        this.invert = invert;
        this.version = version;
    }

    public static List<DuelistOrbInfo> getInfo() {
        List<AbstractOrb> allDuelistOrbs = RandomOrbHelper.returnOrbList();
        List<DuelistOrbInfo> info = new ArrayList<>();
        for (AbstractOrb o : allDuelistOrbs) {
            String desc = o.description.replaceAll("#b", "").replaceAll("#y", "");
            String[] splice = desc.split("Evoke:");
            String[] secondSplice = splice.length > 1 ? splice[1].split("Inversion:") : null;
            String[] passiveSplit = splice.length > 0 ? splice[0].split("Passive:") : null;
            String passive = passiveSplit != null && passiveSplit.length > 1 ? passiveSplit[1] : splice.length > 0 ? splice[0] : "";
            String evoke = secondSplice != null && secondSplice.length > 0 ? secondSplice[0] : splice.length > 1 ? splice[1] : "";
            String invert = secondSplice != null && secondSplice.length > 1 ? secondSplice[1] : "";
            info.add(new DuelistOrbInfo(o.ID, o.name, passive.trim(), evoke.trim(), invert.trim(), DuelistMod.trueVersion));
        }
        return info;
    }

    @Override
    public String toString() {
        JsonToStringBuilder builder = new JsonToStringBuilder(this);
        builder.append("name", name);
        builder.append("passive", passive);
        builder.append("evoke", evoke);
        builder.append("invert", invert);
        builder.append("version", version);
        return builder.build();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassive() {
        return passive;
    }

    public void setPassive(String passive) {
        this.passive = passive;
    }

    public String getEvoke() {
        return evoke;
    }

    public void setEvoke(String evoke) {
        this.evoke = evoke;
    }

    public String getInvert() {
        return invert;
    }

    public void setInvert(String invert) {
        this.invert = invert;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getOrb_id() {
        return orb_id;
    }

    public void setOrb_id(String orb_id) {
        this.orb_id = orb_id;
    }
}
