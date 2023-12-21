package duelistmod.events;

import java.util.ArrayList;
import basemod.IUIElement;
import basemod.ModLabel;
import basemod.eventUtil.util.Condition;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.RoomEventDialog;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Circlet;
import duelistmod.DuelistMod;
import duelistmod.abstracts.CombatDuelistEvent;
import duelistmod.dto.DuelistConfigurationData;
import duelistmod.dto.EventConfigData;
import duelistmod.helpers.Util;
import duelistmod.monsters.OppositeDuelistEnemy;
import duelistmod.ui.configMenu.DuelistDropdown;
import duelistmod.ui.configMenu.DuelistLabeledToggleButton;

public class BattleCity extends CombatDuelistEvent
{
    public static final String ID = DuelistMod.makeID("BattleCity");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private CurScreen screen;
    private boolean playerIsKaiba = true;
    private final int relicRewards;

    public static final String RELIC_REWARDS_KEY = "Relic Rewards";
    public static final int DEFAULT_RELIC_REWARDS = 3;
    
    public BattleCity() {
        super(ID, NAME);
        Condition bothConditions = () ->
                !this.getActiveConfig().getIsDisabled() &&
                AbstractDungeon.player != null &&
                DuelistMod.persistentDuelistData.GameplaySettings.getEnemyDuelists() &&
                (AbstractDungeon.actNum > 1 || Util.getChallengeLevel() > 4);
        this.relicRewards = (int) this.getConfig(RELIC_REWARDS_KEY, DEFAULT_RELIC_REWARDS);
        this.spawnCondition = bothConditions;
        this.bonusCondition = bothConditions;
        if (AbstractDungeon.player != null && AbstractDungeon.getCurrMapNode() != null && AbstractDungeon.getCurrRoom() != null) {
            this.screen = CurScreen.INTRO;
            this.initializeImage(DuelistMod.makeEventPath("sphereClosed.png"), 1120.0f * Settings.scale, AbstractDungeon.floorY - 50.0f * Settings.scale);
            this.hasDialog = true;
            this.hasFocus = true;
            OppositeDuelistEnemy enemy = new OppositeDuelistEnemy(true, this);
            AbstractDungeon.getCurrRoom().monsters = new MonsterGroup(enemy);
            this.playerIsKaiba = !enemy.isKaiba();
            String fightText = OPTIONS[playerIsKaiba ? 3 : 0] + this.relicRewards + OPTIONS[4];
            this.roomEventText.addDialogOption(fightText);
            this.roomEventText.addDialogOption(OPTIONS[1]);
            this.body = fightText;
        }
    }

    @Override
    public EventConfigData getDefaultConfig() {
        EventConfigData config = new EventConfigData();
        config.put(RELIC_REWARDS_KEY, DEFAULT_RELIC_REWARDS);
        return config;
    }

    @Override
    public void update() {
        super.update();
        if (!RoomEventDialog.waitForInput) {
            this.buttonEffect(this.roomEventText.getSelectedOption());
        }
    }

    public boolean isInCombat() {
        return this.screen == CurScreen.COMBAT;
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
                    ArrayList<String> relicsAdded = new ArrayList<>();
                    ArrayList<AbstractRelic> relics = new ArrayList<>();
                    while (relics.size() < this.relicRewards)
                    {
                        AbstractRelic re = AbstractDungeon.returnRandomScreenlessRelic(AbstractRelic.RelicTier.RARE);
                        if (re instanceof Circlet) break;
                        while (!(re instanceof Circlet) && relicsAdded.contains(re.name)) { re = AbstractDungeon.returnRandomScreenlessRelic(AbstractRelic.RelicTier.RARE); }
                        if (re instanceof Circlet) break;
                        relics.add(re);
                        relicsAdded.add(re.name);
                    }
                    for (AbstractRelic r : relics) {  AbstractDungeon.getCurrRoom().addRelicToRewards(r); }
                    if (this.img != null) {
                        this.img.dispose();
                        this.img = null;
                    }
                    this.img = ImageMaster.loadImage(DuelistMod.makeEventPath("sphereOpen.png"));
                    this.screen = CurScreen.COMBAT;
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

    @Override
    public DuelistConfigurationData getConfigurations() {
        RESET_Y(); LINEBREAK(); LINEBREAK(); LINEBREAK(); LINEBREAK();
        ArrayList<IUIElement> settingElements = new ArrayList<>();
        EventConfigData onLoad = this.getActiveConfig();

        String tooltip = "When enabled, allows you encounter this event during runs. Enabled by default.";
        settingElements.add(new DuelistLabeledToggleButton("Event Enabled", tooltip,DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, !onLoad.getIsDisabled(), DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            EventConfigData data = this.getActiveConfig();
            data.setIsDisabled(!button.enabled);
            this.updateConfigSettings(data);
        }));

        LINEBREAK();

        settingElements.add(new ModLabel("Relic Rewards", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
        ArrayList<String> relicRewardOptions = new ArrayList<>();
        for (int i = 0; i < 1001; i++) { relicRewardOptions.add(i+""); }
        tooltip = "Modify number of Relics given as a reward for defeating the enemy Duelist. Set to #b3 by default.";
        DuelistDropdown relicRewardsSelector = new DuelistDropdown(tooltip, relicRewardOptions, Settings.scale * (DuelistMod.xLabPos + 650), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
            EventConfigData data = this.getActiveConfig();
            data.put(RELIC_REWARDS_KEY, i);
            this.updateConfigSettings(data);
        });
        relicRewardsSelector.setSelected(onLoad.getProperties().getOrDefault(RELIC_REWARDS_KEY, DEFAULT_RELIC_REWARDS).toString());
        settingElements.add(relicRewardsSelector);
        return new DuelistConfigurationData(this.title, settingElements, this);
    }

    private enum CurScreen
    {
        INTRO, 
        PRE_COMBAT,
        COMBAT,
        END
    }
}
