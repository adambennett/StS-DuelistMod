package duelistmod.events;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

import duelistmod.*;
import duelistmod.abstracts.*;
import duelistmod.cards.incomplete.*;
import duelistmod.helpers.Util;
import duelistmod.potions.MillenniumElixir;

public class MillenniumItems extends DuelistEvent {


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
        imageEventText.setDialogOption(OPTIONS[1], Util.getChallengeLevel() > -1);
    }

    @Override
    protected void buttonEffect(int i) {
        switch (screenNum) {
            case 0:
                switch (i) {
                    case 0:
                    	String nameOfItem = "";
                        //this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[1]);
                        this.imageEventText.clearRemainingOptions();
                        boolean hasEveryMillenniumItem = true;
                        for (AbstractRelic t : Util.getAllMillenniumItems(false)) { if (!AbstractDungeon.player.hasRelic(t.relicId)) { hasEveryMillenniumItem = false; }}
                        if (!hasEveryMillenniumItem) 
                        {
                        	ArrayList<Object> mills = new ArrayList<Object>();
                        	for (AbstractRelic r : Util.getAllMillenniumItems(false)) { if (!(AbstractDungeon.player.hasRelic(r.relicId))) { mills.add(r.makeCopy()); }}
                        	//mills.add(new MillenniumElixir());    
                        	mills.add(new MillenniumSpellbook());
                        	mills.add(new Metronome());
                        	Object randMill = mills.get(AbstractDungeon.eventRng.random(mills.size() - 1));
                        	if (randMill instanceof AbstractCard) { nameOfItem = ((AbstractCard)randMill).name; randMill = mills.get(AbstractDungeon.eventRng.random(mills.size() - 1)); }
                        	
                        	if (randMill instanceof AbstractRelic)
                        	{
                        		 AbstractCard b = DuelistCardLibrary.getRandomDuelistCurse();
                                 AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(b, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                        		 AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2), (AbstractRelic) randMill);
                        		 Util.removeRelicFromPools(((AbstractRelic) randMill).relicId);
                        		 nameOfItem = ((AbstractRelic)randMill).name;
                        	}
                        	
                        	else if (randMill instanceof AbstractPotion)
                        	{
                        		AbstractCard b = DuelistCardLibrary.getRandomDuelistCurse();
                                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(b, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                        		AbstractDungeon.player.obtainPotion((AbstractPotion) randMill);
                        		nameOfItem = ((AbstractPotion)randMill).name;
                        	}
                        	
                        	else if (randMill instanceof DuelistCard)
                        	{
                        		AbstractCard b = DuelistCardLibrary.getRandomDuelistCurse();
                                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(b, Settings.WIDTH * 0.6F, Settings.HEIGHT / 2.0F));
                        		AbstractDungeon.effectList.add(new ShowCardAndObtainEffect((AbstractCard) randMill, Settings.WIDTH * 0.3F, Settings.HEIGHT / 2.0F));
                        		nameOfItem = ((AbstractCard)randMill).name;
                        	}
                        	
                        	else if (DuelistMod.debug && randMill != null)
                        	{
                        		DuelistMod.logger.info("Millennium Items event generated a random object from the object array that wasn't a potion, relic or card? Hmm. How did that happen? The object: " + randMill.toString());
                        	}
                        }
                        else
                        {
                        	if (DuelistMod.debug)
                            {
                            	DuelistMod.logger.info("Triggered hasEveryMillenniumItem boolean, so do you have them all? Coin, Rod, Key, Eye, Ring, Necklace, Scale, Token");
                            }
                        	
                        	ArrayList<Object> mills = new ArrayList<Object>();
                        	mills.add(new MillenniumElixir());    
                        	mills.add(new MillenniumSpellbook());
                        	mills.add(new Metronome());
                        	Object randMill = mills.get(AbstractDungeon.eventRng.random(mills.size() - 1));
                        	if (randMill instanceof AbstractPotion)
                        	{
                        		AbstractCard b = DuelistCardLibrary.getRandomDuelistCurse();
                                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(b, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                        		AbstractDungeon.player.obtainPotion((AbstractPotion) randMill);
                        		nameOfItem = ((AbstractPotion)randMill).name;
                        	}
                        	
                        	else if (randMill instanceof DuelistCard)
                        	{
                        		AbstractCard b = DuelistCardLibrary.getRandomDuelistCurse();
                                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(b, Settings.WIDTH * 0.6F, Settings.HEIGHT / 2.0F));
                        		AbstractDungeon.effectList.add(new ShowCardAndObtainEffect((AbstractCard) randMill, Settings.WIDTH * 0.3F, Settings.HEIGHT / 2.0F));
                        		nameOfItem = ((AbstractCard)randMill).name;
                        	}
                        	
                        	else if (DuelistMod.debug && randMill != null)
                        	{
                        		DuelistMod.logger.info("Millennium Items event generated a random object from the object array that wasn't a potion, relic or card? Hmm. How did that happen? The object: " + randMill.toString());
                        	}
                        }
                        logMetric(NAME, "Took item - Received: " + nameOfItem);
                        screenNum = 1;
                        break;
                    case 1:
                        this.imageEventText.updateDialogOption(0, OPTIONS[1]);
                        this.imageEventText.clearRemainingOptions();
                        logMetric(NAME, "Leave");
                        screenNum = 1;
                }
                break;
            case 1:
                openMap();
                break;

        }
    }
}

