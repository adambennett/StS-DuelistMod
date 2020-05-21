package duelistmod.metrics;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import duelistmod.metrics.builders.*;

class ColorExportData {
    public AbstractCard.CardColor color;
    public String id;
    public String name;
    public ArrayList<CardExportData> cards = new ArrayList<>();
    public ArrayList<RelicExportData> relics = new ArrayList<>();

    public ColorExportData(AbstractCard.CardColor color) {
        this.color = color;
        this.id = color.toString();
        this.name = ExportUploader.colorName(color);
    }

    public static ArrayList<ColorExportData> exportAllColors() {
        ArrayList<ColorExportData> colors = new ArrayList<>();
        for (AbstractCard.CardColor color : AbstractCard.CardColor.values()) {
            colors.add(new ColorExportData(color));
        }
        return colors;
    }

    @Override
    public String toString() {
        JsonToStringBuilder builder = new JsonToStringBuilder(this);
        builder.append("color", color);
        builder.append("color_id", id);
        builder.append("name", name);
        return builder.build();
    }
}
