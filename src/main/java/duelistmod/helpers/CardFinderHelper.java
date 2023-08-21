package duelistmod.helpers;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.dto.CardForHashSets;
import duelistmod.variables.Tags;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CardFinderHelper {
    public static ArrayList<AbstractCard> find(int amtNeeded,
                                               List<? extends AbstractCard> group,
                                               List<? extends AbstractCard> backupGroup,
                                               Predicate<AbstractCard> predicate) {
        Map<String, AbstractCard> cards = findAll(group, predicate);
        ArrayList<AbstractCard> output = new ArrayList<>(cards.values());
        if (output.size() < amtNeeded && backupGroup != null) {
            output.addAll(find(amtNeeded - output.size(), backupGroup, null,
                    predicate.and(c -> !cards.containsKey(c.cardID))));
        }
        if (output.size() > amtNeeded) {
            Collections.shuffle(output, new Random(AbstractDungeon.cardRandomRng.randomLong()));
            output.subList(amtNeeded, cards.size()).clear();
        }
        return output;
    }

    public static ArrayList<AbstractCard> find(int amtNeeded,
                                               List<List<? extends AbstractCard>> groups,
                                               Predicate<AbstractCard> predicate) {
        if (groups.size() < 1) return new ArrayList<>();

        ArrayList<AbstractCard> output = new ArrayList<>();
        for (List<? extends AbstractCard> list : groups) {
            if (output.size() >= amtNeeded) {
                Collections.shuffle(output, new Random(AbstractDungeon.cardRandomRng.randomLong()));
                output.subList(amtNeeded, output.size()).clear();
                return output;
            }
            ArrayList<AbstractCard> checkGroup = find(amtNeeded, list, null, predicate);
            if (checkGroup.size() + output.size() > amtNeeded) {
                int diff = amtNeeded - output.size();
                output.addAll(checkGroup.subList(0, diff));
            } else if (checkGroup.size() + output.size() <= amtNeeded) {
                output.addAll(checkGroup);
            }
        }
        return output;
    }

    public static ArrayList<DuelistCard> findAsDuelist(int amtNeeded,
                                                       List<List<? extends AbstractCard>> groups,
                                                       Predicate<AbstractCard> predicate) {
        ArrayList<AbstractCard> cards = find(amtNeeded, groups, predicate);
        return cards.stream()
                .filter(DuelistCard.class::isInstance)
                .map(DuelistCard.class::cast)
                .collect(Collectors
                .toCollection(ArrayList::new));
    }

    public static Map<String, AbstractCard> findAll(List<? extends AbstractCard> group, Predicate<AbstractCard> predicate) {
        return group.stream()
                .map(CardForHashSets::new)
                .collect(Collectors.toSet())
                .stream()
                .map(CardForHashSets::card)
                .filter(predicate.and(c -> !c.hasTag(Tags.NEVER_GENERATE)))
                .map(AbstractCard::makeCopy)
                .collect(Collectors.toMap(c -> c.cardID, c -> c));
    }

    public static Predicate<AbstractCard> canResummon() {
        return DuelistCard::allowResummonsWithExtraChecks;
    }

    public static Predicate<AbstractCard> hasTags(AbstractCard.CardTags... tags) {
        return c -> Stream.of(tags).filter(Objects::nonNull).allMatch(c::hasTag);
    }

    public static Predicate<AbstractCard> configExclusion() {
        return c -> (DuelistMod.persistentDuelistData.CardPoolSettings.getRemoveToons() && c.hasTag(Tags.TOON_POOL)) ||
                (DuelistMod.persistentDuelistData.CardPoolSettings.getRemoveOjama() && c.hasTag(Tags.OJAMA)) ||
                (DuelistMod.persistentDuelistData.CardPoolSettings.getRemoveExodia() && c.hasTag(Tags.EXODIA));
    }

    public static Predicate<AbstractCard> withRarity(AbstractCard.CardRarity rarity) {
        return c -> c.rarity.equals(rarity);
    }
}
