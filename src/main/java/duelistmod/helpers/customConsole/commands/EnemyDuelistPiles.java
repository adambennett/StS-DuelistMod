package duelistmod.helpers.customConsole.commands;

import basemod.DevConsole;
import basemod.devcommands.ConsoleCommand;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTags;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.abstracts.enemyDuelist.AbstractEnemyDuelist;
import duelistmod.abstracts.enemyDuelist.AbstractEnemyDuelistCard;
import duelistmod.abstracts.enemyDuelist.EnemyCardGroup;
import duelistmod.actions.enemyDuelist.EnemyDrawActualCardsAction;
import duelistmod.cards.pools.dragons.BlueEyes;
import duelistmod.dto.AnyDuelist;
import duelistmod.powers.SummonPower;
import duelistmod.variables.Tags;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EnemyDuelistPiles extends ConsoleCommand {

    private enum Piles {
        DRAW("Draw Pile"),
        DISCARD("Discard Pile"),
        HAND("Hand"),
        EXHAUST("Exhaust Pile"),
        SUMMONS("Summons"),
        CHANNEL("Channel", 3),
        BLOCK("Block", 2),
        DRAW_RARE("Draw Rare", 3),
        DRAW_TAG("Draw Tag", 3),
        ENERGY("Energy"),
        EVOKE("Evoke"),
        INCREMENT("Increment"),
        RELIC("Relic"),
        SPECIAL_SUMMON("Special Summon"),
        SUMMON("Summon"),
        TRIBUTE("Tribute"),
        TEMP_HP("Temporary HP"),
        MAX_HP("Max HP"),
        INVERT("Invert"),
        HP("HP"),
        GRAVEYARD("Graveyard");

        private final String display;
        private final int amountIndex;

        Piles(String display) {
            this(display, 4);
        }

        Piles(String display, int amountIndex) {
            this.display = display;
            this.amountIndex = amountIndex;
        }

        public String display() {
            return this.display;
        }
        public int amountIndex() { return this.amountIndex; }
    }

    private enum PileCommands {
        ADD,
        VIEW,
        CLEAR,
        DISCARD,
        DRAW_CARDS,
        LOSE
    }

    public EnemyDuelistPiles() {
        requiresPlayer = true;
        minExtraTokens = 0;
        maxExtraTokens = 4;
    }

    @Override
    protected void execute(String[] tokens, int depth) {

        if (AbstractEnemyDuelist.enemyDuelist == null) {
            DevConsole.log("Requires enemy duelist");
            return;
        }

        AnyDuelist duelist = AnyDuelist.from(AbstractEnemyDuelist.enemyDuelist);
        Piles pile = Piles.DRAW;
        PileCommands command = PileCommands.VIEW;
        CardGroup pileGroup = null;
        DuelistCard addRemoveCard = new BlueEyes();
        AbstractOrb orb = null;
        String rarityName = null;
        CardRarity rarity = CardRarity.COMMON;
        CardTags tag = Tags.MONSTER;

        if (tokens.length > 1) {
            String pileToken = tokens[1];
            if (pileToken != null && !pileToken.trim().equals("")) {
                switch (pileToken.toLowerCase()) {
                    case "hand":
                        pile = Piles.HAND;
                        break;
                    case "graveyard":
                        pile = Piles.GRAVEYARD;
                        break;
                    case "discard":
                        pile = Piles.DISCARD;
                        break;
                    case "exhaust":
                        pile = Piles.EXHAUST;
                        break;
                    case "summons":
                        pile = Piles.SUMMONS;
                        break;
                    case "channel":
                        pile = Piles.CHANNEL;
                        break;
                    case "block":
                        pile = Piles.BLOCK;
                        break;
                    case "drawrare":
                        pile = Piles.DRAW_RARE;
                        break;
                    case "drawtag":
                        pile = Piles.DRAW_TAG;
                        break;
                    case "energy":
                        pile = Piles.ENERGY;
                        break;
                    case "evoke":
                    case "increment":
                    case "relic":
                    case "specialsummon":
                    case "summon":
                    case "tribute":
                    case "temphp":
                    case "maxhp":
                    case "invert":
                    case "hp":
                        DevConsole.log("Command not yet implemented");
                        return;
                }
            }

            String cmdToken = tokens.length > 2 ? tokens[2] : "";
            int cardsToDraw = 0;
            if (pile == Piles.DRAW) {
                try {
                    cardsToDraw = Integer.parseInt(tokens[2]);
                } catch (Exception ignored) {}
            }
            if (cardsToDraw > 0) {
                command = PileCommands.DRAW_CARDS;
            } else {
                if (cmdToken != null && !cmdToken.trim().equals("")) {
                    switch (cmdToken.toLowerCase()) {
                        case "add":
                            command = PileCommands.ADD;
                            break;
                        case "clear":
                            command = PileCommands.CLEAR;
                            break;
                        case "discard":
                            command = PileCommands.DISCARD;
                            break;
                        case "lose":
                            command = PileCommands.LOSE;
                            break;
                        default:
                            if (pile.equals(Piles.CHANNEL)) {
                                orb = DuelistMod.implementedEnemyDuelistOrbs.getOrDefault(cmdToken, null);
                            } else if (pile.equals(Piles.DRAW_RARE) || pile.equals(Piles.DRAW_TAG)) {
                                rarityName = cmdToken;
                            }
                            break;
                    }
                }
            }

            if (rarityName != null) {
                if (pile.equals(Piles.DRAW_RARE)) {
                    for (CardRarity rar : CardRarity.values()) {
                        if (rar.name().trim().equalsIgnoreCase(rarityName.trim()) || rar.toString().trim().equalsIgnoreCase(rarityName.trim())) {
                            rarity = rar;
                            break;
                        }
                    }
                } else {
                    for (CardTags tg : CardTags.values()) {
                        if (tg.name().trim().equalsIgnoreCase(rarityName.trim()) || tg.toString().trim().equalsIgnoreCase(rarityName.trim())) {
                            tag = tg;
                            break;
                        }
                    }
                }
            }

            int amountToAdd = 1;
            int amountIndex = pile.amountIndex();
            if (tokens.length > amountIndex) {
                try {
                    amountToAdd = Integer.parseInt(tokens[amountIndex]);
                } catch (Exception ignored) {}
            }
            if (command.equals(PileCommands.ADD) || command.equals(PileCommands.DISCARD)) {
                String cardIdToken = tokens.length > 3 ? tokens[3] : "";
                if (cardIdToken != null && !cardIdToken.trim().equals("")) {
                    addRemoveCard = DuelistMod.cardIdMap.getOrDefault(cardIdToken, new BlueEyes());
                }
            }

            addRemoveCard = (DuelistCard) addRemoveCard.makeCopy();
            if (!DuelistMod.implementedEnemyDuelistCards.containsKey(addRemoveCard.cardID)) {
                DevConsole.log("Card not yet implemented for enemy Duelists");
                return;
            }

            switch (pile) {
                case ENERGY:
                    switch (command) {
                        case ADD:
                            duelist.gainEnergy(amountToAdd);
                            break;
                        case LOSE:
                            duelist.gainEnergy(-amountToAdd);
                            break;
                        default:
                            DevConsole.log("Invalid Energy command");
                            return;
                    }
                    break;
                case BLOCK:
                    duelist.block(amountToAdd);
                    break;
                case CHANNEL:
                    if (orb != null) {
                        duelist.channel(orb.makeCopy(), amountToAdd);
                    } else {
                        DevConsole.log("Invalid Channel command");
                        return;
                    }
                    break;
                case DRAW:
                    pileGroup = AbstractEnemyDuelist.enemyDuelist.drawPile;
                    switch (command) {
                        case ADD:
                            for (int i = 0; i < amountToAdd; i++) {
                                AbstractEnemyDuelist.enemyDuelist.addCardToDrawPile(addRemoveCard.makeCopy());
                            }
                            break;
                        case CLEAR:
                            pileGroup.clear();
                            if (AbstractEnemyDuelist.enemyDuelist.drawPile instanceof EnemyCardGroup) {
                                ((EnemyCardGroup)AbstractEnemyDuelist.enemyDuelist.drawPile).updateDrawPilePower();
                            }
                            break;
                        case DRAW_CARDS:
                            AbstractDungeon.actionManager.addToBottom(new EnemyDrawActualCardsAction(AbstractEnemyDuelist.enemyDuelist, cardsToDraw));
                            break;
                    }
                    break;
                case DRAW_RARE:
                    duelist.drawRare(amountToAdd, rarity);
                    break;
                case DRAW_TAG:
                    duelist.drawTag(amountToAdd, tag);
                    break;
                case DISCARD:
                    pileGroup = AbstractEnemyDuelist.enemyDuelist.discardPile;
                    switch (command) {
                        case ADD:
                            for (int i = 0; i < amountToAdd; i++) {
                                AbstractEnemyDuelist.enemyDuelist.addCardToDiscardPile(addRemoveCard.makeCopy());
                            }
                            break;
                        case CLEAR:
                            pileGroup.clear();
                            break;
                    }
                    break;
                case HAND:
                    if (command == PileCommands.ADD) {
                        for (int i = 0; i < amountToAdd; i++) {
                            List<AbstractEnemyDuelistCard> card = new ArrayList<>();
                            card.add(AbstractEnemyDuelist.fromCard(addRemoveCard.makeCopy()));
                            AbstractDungeon.actionManager.addToBottom(new EnemyDrawActualCardsAction(AbstractEnemyDuelist.enemyDuelist, card));
                        }
                    } else if (command == PileCommands.DISCARD) {
                        ArrayList<AbstractCard> newHand = new ArrayList<>();
                        ArrayList<AbstractCard> discarded = new ArrayList<>();
                        boolean foundMatch = false;
                        boolean discardAll = tokens[3].equalsIgnoreCase("all");
                        for (AbstractCard c : AbstractEnemyDuelist.enemyDuelist.hand.group) {
                            if (discardAll) {
                                discarded.add(c);
                                continue;
                            }
                            if (!foundMatch && c.cardID.equals(addRemoveCard.cardID)) {
                                foundMatch = true;
                                discarded.add(c);
                            } else {
                                newHand.add(c);
                            }
                        }
                        AbstractEnemyDuelist.enemyDuelist.hand.group = newHand;
                        for (AbstractCard c : discarded) {
                            AbstractEnemyDuelist.enemyDuelist.addCardToDiscardPile(c.makeCopy());
                        }
                        AbstractDungeon.actionManager.addToBottom(new EnemyDrawActualCardsAction(AbstractEnemyDuelist.enemyDuelist, new ArrayList<>()));
                    } else {
                        DevConsole.log("Invalid Hand command");
                        return;
                    }
                    break;
                case EXHAUST:
                    pileGroup = AbstractEnemyDuelist.enemyDuelist.exhaustPile;
                    switch (command) {
                        case ADD:
                            for (int i = 0; i < amountToAdd; i++) {
                                AbstractEnemyDuelist.enemyDuelist.addCardToExhaust(addRemoveCard.makeCopy());
                            }
                            break;
                        case CLEAR:
                            pileGroup.clear();
                            break;
                    }
                    break;
                case GRAVEYARD:
                    pileGroup = AbstractEnemyDuelist.enemyDuelist.graveyard;
                    switch (command) {
                        case ADD:
                            for (int i = 0; i < amountToAdd; i++) {
                                AbstractEnemyDuelist.enemyDuelist.addCardToGraveyard(addRemoveCard.makeCopy());
                            }
                            break;
                        case CLEAR:
                            pileGroup.clear();
                            break;
                    }
                    break;
                case SUMMONS:
                    if (AbstractEnemyDuelist.enemyDuelist.hasPower(SummonPower.POWER_ID)) {
                        SummonPower pow = (SummonPower) AbstractEnemyDuelist.enemyDuelist.getPower(SummonPower.POWER_ID);
                        CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
                        for (DuelistCard c : pow.getCardsSummoned()) {
                            tmp.addToBottom(c);
                        }
                        if (tmp.isEmpty()) {
                            DevConsole.log("All Summon Zones are empty");
                            return;
                        }
                        pileGroup = tmp;
                    } else {
                        DevConsole.log(AbstractEnemyDuelist.enemyDuelist.name + " does not have Summon Power");
                        return;
                    }

            }
        }

        if (pile.equals(Piles.CHANNEL)) {
            return;
        }

        if (pileGroup == null) {
            pileGroup = AbstractEnemyDuelist.enemyDuelist.drawPile;
        }

        if (command == PileCommands.VIEW) {
            if (!pileGroup.isEmpty()) {
                pileGroup.group.forEach(c -> AbstractEnemyDuelist.fromCard(c).bossLighten());
                DuelistMod.duelistCardViewScreen.open(pileGroup, AbstractEnemyDuelist.enemyDuelist.name + " " + pile.display());
            } else {
                DevConsole.log(AbstractEnemyDuelist.enemyDuelist.name + " " + pile.display() + " is empty");
            }
        }
    }

    @Override
    public ArrayList<String> extraOptions(String[] tokens, int depth) {
        ArrayList<String> options = new ArrayList<>();
        options.add("draw");
        options.add("discard");
        options.add("hand");
        options.add("exhaust");
        options.add("graveyard");
        options.add("summons");
        options.add("channel");

        options.add("block");
        options.add("drawrare");
        options.add("drawtag");
        options.add("energy");
        /*options.add("evoke");
        options.add("increment");
        options.add("relic");
        options.add("specialsummon");
        options.add("summon");
        options.add("tribute");
        options.add("temphp");
        options.add("maxhp");
        options.add("invert");
        options.add("hp");*/

        if (depth == 1 && tokens.length <= 2) {
            return options;
        }

        if (!options.contains(tokens[1])) {
            ConsoleCommand.errormsg = "Invalid pile/command";
            return new ArrayList<>();
        }

        options.clear();
        switch (tokens[1].toLowerCase()) {
            case "energy":
                options.add("add");
                options.add("lose");
                break;
            case "channel":
                options = new ArrayList<>(DuelistMod.implementedEnemyDuelistOrbs.keySet());
                break;
            case "summons":
                options.add("view");
                break;
            case "block":
                options = smallNumbers();
                break;
            case "hand":
                options.add("add");
                options.add("discard");
                break;
            case "draw":
                for (int i = 1; i < 4; i++) {
                    options.add(i+"");
                }
                break;
            case "drawrare":
                for (CardRarity rarity : CardRarity.values()) {
                    options.add(rarity.name().trim().toLowerCase());
                }
                break;
            case "drawtag":
                for (CardTags rarity : CardTags.values()) {
                    options.add(rarity.toString().trim().toLowerCase());
                }
                break;
            default:
                options.add("view");
                options.add("add");
                options.add("clear");
                break;
        }

        if (tokens.length <= 3) {
            return options;
        }

        if (!options.contains(tokens[2])) {
            ConsoleCommand.errormsg = "Invalid command";
            return new ArrayList<>();
        }

        if (tokens.length == 4) {
            boolean needCardId = tokens[2].equalsIgnoreCase("add") || tokens[2].equalsIgnoreCase("discard");
            if (needCardId) {
                if (tokens[2].equalsIgnoreCase("add")) {
                    options = new ArrayList<>(DuelistMod.implementedEnemyDuelistCards.keySet());
                } else {
                    String pile = tokens[1];
                    ArrayList<AbstractCard> list;
                    switch (pile.toLowerCase()) {
                        default:
                            list = AbstractEnemyDuelist.enemyDuelist.drawPile.group;
                            break;
                        case "discard":
                            list = AbstractEnemyDuelist.enemyDuelist.discardPile.group;
                            break;
                        case "hand":
                            list = AbstractEnemyDuelist.enemyDuelist.hand.group;
                            break;
                        case "exhaust":
                            list = AbstractEnemyDuelist.enemyDuelist.exhaustPile.group;
                            break;
                        case "graveyard":
                            list = AbstractEnemyDuelist.enemyDuelist.graveyard.group;
                            break;
                    }
                    options = list.stream().map(c -> c.cardID).collect(Collectors.toCollection(ArrayList::new));
                    options.add("all");
                }
                return options;
            } else if (tokens[1].equalsIgnoreCase("channel") || tokens[1].equalsIgnoreCase("drawrare") || tokens[1].equalsIgnoreCase("drawtag") || tokens[1].equalsIgnoreCase("energy")) {
                return smallNumbers();
            }
        }

        if (tokens.length == 5 && tokens[2].equalsIgnoreCase("add")) {
            return smallNumbers();
        }

        tooManyTokensError();
        return options;
    }
}
