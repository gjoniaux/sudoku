package be.gjoniaux.sudoku.model;

import be.gjoniaux.sudoku.exception.BadChoiceException;

import java.util.*;

public class CaseGroup {
    private Map<Integer, List<Integer>> caseGroup;

    public CaseGroup() {
        caseGroup = new HashMap<Integer, List<Integer>>();
    }

    public CaseGroup(Map<Integer, List<Integer>> caseGroup) {
        this.caseGroup = new HashMap<Integer, List<Integer>>();
        caseGroup.entrySet().forEach(cg -> this.caseGroup.put(cg.getKey(), new ArrayList<Integer>(cg.getValue())));
    }

    public void initialize() {
        for (int i = 1; i <= 9; i++) {
            caseGroup.put(i, new ArrayList<Integer>());
        }
    }

    public void addCase(Integer caseId) {
        caseGroup.values().forEach(cases -> cases.add(caseId));
    }

    public void setCorrectCase(Integer correctCase, Integer number) {
        List<Integer> numberPossibleCases = caseGroup.get(number);
        // If the case is not in the list of the possibilities for the correct number, it's a bad choice
        if (!numberPossibleCases.contains(correctCase)) {
            throw new BadChoiceException("Impossible to set correct number " + number + " to possibilities " + numberPossibleCases);
        }
        // Fix case as correct for that number
        caseGroup.put(number, Arrays.asList(correctCase));

    }

    public List<Integer> removeIncorrectCaseFromOtherNumbers(Integer incorrectCase, Integer number) {
        List<Integer> remainingCaseIds = new ArrayList<Integer>();
        // Remove the case from other number possibilities
        for (int i = 1; i <= 9; i++) {
            if (!number.equals(i)) {
                List<Integer> possibleCases = caseGroup.get(i);
                possibleCases.remove(incorrectCase);
                remainingCaseIds.addAll(possibleCases);
            }
        }
        return remainingCaseIds;
    }

    public Integer getCorrectCaseId(Integer number) {
        List<Integer> numberPossibleCases = caseGroup.get(number);
        return numberPossibleCases.size() == 1 ? numberPossibleCases.get(0) : -1;
    }

    @Override
    public CaseGroup clone() {
        return new CaseGroup(caseGroup);
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<Integer, List<Integer>> entry : caseGroup.entrySet()) {
            builder.append(entry.getKey()).append(" (").append(entry.getValue().size()).append(") ");
        }
        return builder.toString();
    }
}
