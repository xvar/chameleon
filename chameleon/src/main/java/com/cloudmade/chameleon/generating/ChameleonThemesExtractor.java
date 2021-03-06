package com.cloudmade.chameleon.generating;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ChameleonThemesExtractor {

    private ChameleonThemesExtractor() {}

    static Map<ChameleonThemeEntity, List<String>> extractChameleonThemes(String[] themeSuffixes, int[] themeSuffixesAmount) {
        List<List<String>> themeSuffixesCombinations = getThemeSuffixesCombinationsFromGroups(
                getThemeGroups(themeSuffixes, themeSuffixesAmount));
        return getChameleonThemesMap(themeSuffixesCombinations);
    }

    static private Map<ChameleonThemeEntity, List<String>> getChameleonThemesMap(List<List<String>> themeSuffixesCombinations) {
        Map<ChameleonThemeEntity, List<String>> map = new HashMap<>();
        for (List<String> themeSuffixesCombination : themeSuffixesCombinations) {
            List<String> allThemeSuffixesPermutationsForGroupCombinationsSet = getAllThemeSuffixesPermutationsForGroupCombinationsSet(themeSuffixesCombination);
            map.put(ChameleonThemeEntity.fromList(themeSuffixesCombination), allThemeSuffixesPermutationsForGroupCombinationsSet);
        }
        return map;
    }

    static private String[][] getThemeGroups(String[] themeSuffixes, int[] themeSuffixesAmount) {
        String[][] themeGroups = new String[themeSuffixesAmount.length][];
        for (int i = 0; i < themeSuffixesAmount.length; i++) {
            String[] themeGroupSuffixes = new String[themeSuffixesAmount[i]];
            int sourcePosition = 0;
            for (int j = 0; j < i; j++) {
                sourcePosition += themeSuffixesAmount[j];
            }
            System.arraycopy(themeSuffixes, sourcePosition, themeGroupSuffixes, 0, themeSuffixesAmount[i]);
            themeGroups[i] = themeGroupSuffixes;
        }
        return themeGroups;
    }

    static private List<List<String>> getThemeSuffixesCombinationsFromGroups(String[][] themeGroups) {
        List<List<String>> themeSuffixesCombinations = new ArrayList<>();
        for (String[] themeGroup : themeGroups) {
            List<List<String>> existingThemeSuffixesCombinations = new ArrayList<>(themeSuffixesCombinations);
            themeSuffixesCombinations.clear();
            for (String themeSuffixInGroup : themeGroup) {
                if (existingThemeSuffixesCombinations.isEmpty()) {
                    themeSuffixesCombinations.add(new ArrayList<>(Collections.singleton(themeSuffixInGroup)));
                } else {
                    for (List<String> existingThemeSuffixesCombination : existingThemeSuffixesCombinations) {
                        List<String> themeSuffixesCombinationForCurrentGroup = new ArrayList<>(existingThemeSuffixesCombination);
                        themeSuffixesCombinationForCurrentGroup.add(themeSuffixInGroup);
                        themeSuffixesCombinations.add(themeSuffixesCombinationForCurrentGroup);
                    }
                }
            }
        }
        return themeSuffixesCombinations;
    }

    static private List<String> getAllThemeSuffixesPermutationsForGroupCombinationsSet(List<String> themeSuffixesCombination) {
        List<String> allThemeSuffixesPermutationsForGroupCombinationsSet = new ArrayList<>();
        for (List<String> permutation : getAllThemeSuffixesPermutationsForGroupCombinations(Algorithms.powerSet(themeSuffixesCombination))) {
            if (!permutation.isEmpty()) {
                allThemeSuffixesPermutationsForGroupCombinationsSet.add(Algorithms.joinString(permutation));
            }
        }
        return allThemeSuffixesPermutationsForGroupCombinationsSet;
    }

    @SuppressWarnings("Java8ListSort")
    static private List<List<String>> getAllThemeSuffixesPermutationsForGroupCombinations(List<List<String>> themeSuffixesCombinations) {
        List<List<String>> allThemeSuffixesPermutations = new ArrayList<>();
        for (List<String> combination : themeSuffixesCombinations) {
            allThemeSuffixesPermutations.addAll(Algorithms.permute(combination));
        }
        Collections.sort(allThemeSuffixesPermutations, (l1, l2) -> Integer.compare(l2.size(), l1.size()));
        return allThemeSuffixesPermutations;
    }

}
