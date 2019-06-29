package duelistmod.events;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

import duelistmod.*;

public class MillenniumItems extends AbstractImageEvent {


    public static final String ID = DuelistMod.makeID("MillenniumItems");
    public static final String IMG = DuelistMod.makeEventPath("MillenniumItems.png");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private int screenNum = 0;
   // private boolean relicSelected = true;
   // private RelicSelectScreen relicSelectScreen;

    public MillenniumItems() {
        super(NAME, DESCRIPTIONS[0], IMG);
        this.noCardsInRewards = true;
        imageEventText.setDialogOption(OPTIONS[0]);
        imageEventText.setDialogOption(OPTIONS[1]);
    }

    @Override
    protected void buttonEffect(int i) {
        switch (screenNum) {
            case 0:
                switch (i) {
                    case 0:
                        //this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[1]);
                        this.imageEventText.clearRemainingOptions();
                        boolean hasEveryMillenniumItem = true;
                        for (AbstractRelic t : Utilities.getAllMillenniumItems(false)) { if (!AbstractDungeon.player.hasRelic(t.relicId)) { hasEveryMillenniumItem = false; }}
                        if (!hasEveryMillenniumItem) 
                        {
                        	AbstractRelic r = Utilities.getRandomMillenniumItem();
                            while (AbstractDungeon.player.hasRelic(r.relicId)) { r = Utilities.getRandomMillenniumItem(); }
                            AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2), r);
                            AbstractCard b = DuelistCardLibrary.getRandomDuelistCurse();
                            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(b, (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));
                        }
                        else if (DuelistMod.debug)
                        {
                        	DuelistMod.logger.info("Triggered hasEveryMillenniumItem boolean, so do you have them all? Coin, Rod, Key, Eye, Ring");
                        }
                        screenNum = 1;
                        break;
                    case 1:
                        //this.imageEventText.updateBodyText(DESCRIPTIONS[4]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[1]);
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 1;
                }
                break;
            case 1:
                openMap();
                break;

        }
    }
}

