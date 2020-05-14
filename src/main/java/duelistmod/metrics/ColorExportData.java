package duelistmod.metrics;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;

class ColorExportData {
    public AbstractCard.CardColor color;
    public String id;
    public String name;
    public ArrayList<CardExportData> cards = new ArrayList<>();
    public ArrayList<RelicExportData> relics = new ArrayList<>();

    @Override
    public String toString() {
        JsonToStringBuilder builder = new JsonToStringBuilder(this);
        builder.append("color", color);
        builder.append("color_id", id);
        builder.append("name", name);
        return builder.build();
    }

    public ColorExportData(ExportHelper export, AbstractCard.CardColor color) {
        this.color = color;
        this.id = color.toString();
        this.name = Exporter.colorName(color);
    }

    public static ArrayList<ColorExportData> exportAllColors(ExportHelper export) {
        ArrayList<ColorExportData> colors = new ArrayList<>();
        for (AbstractCard.CardColor color : AbstractCard.CardColor.values()) {
            colors.add(new ColorExportData(export,color));
        }
        return colors;
    }
}
