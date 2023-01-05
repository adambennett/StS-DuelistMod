package duelistmod.events;

import java.util.ArrayList;

import basemod.eventUtil.util.Condition;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.RoomEventDialog;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.relics.*;

import duelistmod.DuelistMod;
import duelistmod.abstracts.CombatDuelistEvent;
import duelistmod.abstracts.DuelistEvent;
import duelistmod.characters.TheDuelist;
import duelistmod.helpers.Util;
import duelistmod.monsters.*;

public class BattleCity extends CombatDuelistEvent
{
	public static final String ID = DuelistMod.makeID("BattleCity");
	private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
	private static final String NAME = eventStrings.NAME;
	private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
	private static final String[] OPTIONS = eventStrings.OPTIONS;
    private CurScreen screen;
    private boolean playerIsKaiba = true;
    
    public BattleCity() {
        super(ID, NAME);
        Condition bothConditions = () ->
                AbstractDungeon.player != null &&
                (AbstractDungeon.actNum > 1 || Util.getChallengeLevel() > 4);
        this.spawnCondition = bothConditions;
        this.bonusCondition = bothConditions;
        if (AbstractDungeon.player != null) {
            this.screen = CurScreen.INTRO;
            this.initializeImage(DuelistMod.makeEventPath("sphereClosed.png"), 1120.0f * Settings.scale, AbstractDungeon.floorY - 50.0f * Settings.scale);
            this.hasDialog = true;
            this.hasFocus = true;
            this.playerIsKaiba = TheDuelist.getDuelist().equals("Kaiba");
            if (playerIsKaiba) { AbstractDungeon.getCurrRoom().monsters = new MonsterGroup(new SuperYugi()); }
            else { AbstractDungeon.getCurrRoom().monsters = new MonsterGroup(new SuperKaiba()); }
            if (!playerIsKaiba)
            {
                this.roomEventText.addDialogOption(BattleCity.OPTIONS[0]);
                this.roomEventText.addDialogOption(BattleCity.OPTIONS[1]);
                this.body = DESCRIPTIONS[0];
            }
            else
            {
                this.roomEventText.addDialogOption(BattleCity.OPTIONS[3]);
                this.roomEventText.addDialogOption(BattleCity.OPTIONS[1]);
                this.body = DESCRIPTIONS[3];
            }
        }
    }
    
    @Override
    public void update() {
        super.update();
        if (!RoomEventDialog.waitForInput) {
            this.buttonEffect(this.roomEventText.getSelectedOption());
        }
    }
    
    @Override
    protected void buttonEffect(final int buttonPressed) {
        Label_0254: {
            switch (this.screen) {
                case INTRO: {
                    switch (buttonPressed) 
                    {
                        case 0: {
                            this.screen = CurScreen.PRE_COMBAT;
                            if (!this.playerIsKaiba) { this.roomEventText.updateBodyText(BattleCity.DESCRIPTIONS[1]); }
                            else { this.roomEventText.updateBodyText(BattleCity.DESCRIPTIONS[4]); }
                            this.roomEventText.updateDialogOption(0, BattleCity.OPTIONS[2]);
                            this.roomEventText.clearRemainingOptions();
                            logDuelistMetric(NAME, "Fight");
                            return;
                        }
                        case 1: {
                            this.screen = CurScreen.END;
                            this.roomEventText.updateBodyText(BattleCity.DESCRIPTIONS[2]);
                            this.roomEventText.updateDialogOption(0, BattleCity.OPTIONS[1]);
                            this.roomEventText.clearRemainingOptions();
                            logDuelistMetric(NAME, "Leave");
                            return;
                        }
                        default: {
                            break Label_0254;
                        }
                    }
                }
                case PRE_COMBAT: {
                    if (Settings.isDailyRun) {
                        AbstractDungeon.getCurrRoom().addGoldToRewards(AbstractDungeon.miscRng.random(50));
                    }
                    else {
                        AbstractDungeon.getCurrRoom().addGoldToRewards(AbstractDungeon.miscRng.random(45, 55));
                    }
                    ArrayList<String> relicsAdded = new ArrayList<String>();
                    ArrayList<AbstractRelic> relics = new ArrayList<AbstractRelic>();
                    while (relics.size() < 3)
                    {
                    	AbstractRelic re = AbstractDungeon.returnRandomScreenlessRelic(AbstractRelic.RelicTier.RARE);
                    	while (!(re instanceof Circlet) && relicsAdded.contains(re.name)) { re = AbstractDungeon.returnRandomScreenlessRelic(AbstractRelic.RelicTier.RARE); }
                    	relics.add(re);
                    	relicsAdded.add(re.name);
                    }
                    for (AbstractRelic r : relics) {  AbstractDungeon.getCurrRoom().addRelicToRewards(r); }
                    if (this.img != null) {
                        this.img.dispose();
                        this.img = null;
                    }
                    this.img = ImageMaster.loadImage(DuelistMod.makeEventPath("sphereOpen.png"));
                    this.enterCombat();
                    break;
                }
                case END: {
                    this.openMap();
                    break;
                }
            }
        }
    }

    private enum CurScreen
    {
        INTRO, 
        PRE_COMBAT, 
        END;
    }
}
