package be.gjoniaux.processor;

import be.gjoniaux.model.CaseGroup;

import java.util.*;

public class CaseGroupProcessor {
    private List<CaseGroup> caseGroups;
    private Set<Integer> lastRemovedCases;

    public CaseGroupProcessor() {
        this.caseGroups = new ArrayList<CaseGroup>();
        this.lastRemovedCases = new HashSet<Integer>();
    }

    public void initialize() {
        // Create and initialize case groups :
        //  grid[0] are lines
        //  grid[1] are columns
        //  grid[2] are squares
        this.caseGroups.clear();
        for (int i = 0; i < 27; i++) {
            CaseGroup caseGroup = new CaseGroup();
            caseGroup.initialize();
            caseGroups.add(caseGroup);
        }

        // Fill in case groups with correct case ids
        for (int i = 0; i < 81; i++) {
            caseGroups.get(i / 9).addCase(i);
            caseGroups.get(9 + i % 9).addCase(i);
            caseGroups.get(18 + ((i / 27) * 3) + ((i / 3) % 3)).addCase(i);
        }
    }

    public void fixNumber(Integer caseId, Integer number) {
        // Fix correct case in groups
        for (Integer groupId : getGroupIdsContainingACase(caseId)) {
            this.caseGroups.get(groupId).setCorrectCase(caseId, number);
        }
    }

    public void removeNumber(Integer caseId, Integer number) {
        // Remove incorrect case from other numbers
        lastRemovedCases.clear();
        for (Integer groupId : getGroupIdsContainingACase(caseId)) {
            lastRemovedCases.addAll(this.caseGroups.get(groupId).removeIncorrectCaseFromOtherNumbers(caseId, number));
        }
    }

    private List<Integer> getGroupIdsContainingACase(Integer caseId) {
        return Arrays.asList(caseId / 9, 9 + caseId % 9, 18 + ((caseId / 27) * 3) + ((caseId / 3) % 3));
    }

    public Set<Integer> getLastRemovedCases() {
        return lastRemovedCases;
    }

    public boolean processNextNumber() {
        return false;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            builder.append(caseGroups.get(i)).append("\n");
        }
        return builder.toString();
    }
}
