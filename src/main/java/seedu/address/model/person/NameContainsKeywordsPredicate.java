package seedu.address.model.person;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class NameContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;
    private final String type;

    public NameContainsKeywordsPredicate(List<String> keywords, String type) {
        this.keywords = keywords;
        this.type = type;
    }

    @Override
    public boolean test(Person person) {
        switch(type) {
            case "n/": return testName(person);
            case "s/": return keywords.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getStudentId().value, keyword));
            case "N/": return keywords.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getNusNetworkId().value, keyword));
            case "g/": return keywords.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getGitHubId().value, keyword));
            case "T/": return keywords.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getTutorialId().value, keyword));
            case "p/": return keywords.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getPhone().value, keyword));
            case "a/": return keywords.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getAddress().value, keyword));
            case "t/": return testTags(person, keywords.get(1));
            default: return false;
        }
    }

    public boolean testTags(Person person, String tag) {
        Set<Tag> tags = person.getTags();
        return tags.contains(new Tag(tag));
    }

    public boolean testName(Person person) {
        String name = person.getName().fullName;
        String[] nameList = name.split("\\s");
        for (String n : nameList) {
            System.out.println(n);
        }
        String allKeysNameList = "";
        for (String s : nameList) {
            for (int j = 1; j <= s.length(); j++) {
                allKeysNameList += s.substring(0, j) + " ";
            }
        }

        String finalAllKeysNameList = allKeysNameList;
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(finalAllKeysNameList, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((NameContainsKeywordsPredicate) other).keywords)); // state check
    }
}
