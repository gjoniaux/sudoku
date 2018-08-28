package be.gjoniaux.processor;

import be.gjoniaux.model.Choice;

import java.util.*;

public class ChoiceProcessor {
    private Map<Integer, List<Integer>> caseSizes;
    private Map<Integer, Integer> choicesIndex;
    private Stack<Integer> choices;

    public ChoiceProcessor() {
        caseSizes = new HashMap<Integer, List<Integer>>();
        choicesIndex = new HashMap<Integer, Integer>();
        choices = new Stack<Integer>();
    }

    public void initialize() {
        this.caseSizes.clear();
        for (int i=1; i<=9; i++) {
            caseSizes.put(i, new ArrayList<Integer>());
        }
        for (int i=0; i<81; i++) {
            caseSizes.get(9).add(i);
        }
    }

    public void fixNumber(Integer caseId, Integer lastSize) {
        caseSizes.get(lastSize).remove(caseId);
    }

    public void removeNumber(Integer caseId, Integer lastSize) {
        // Last size to 1 is noop
        if (lastSize == 1) {
            return;
        }
        caseSizes.get(lastSize).remove(caseId);
        caseSizes.get(lastSize - 1).add(caseId);
    }

    public Choice findNextCase() {
        List<Integer> sizes = null;
        // Cases with only one element are automatically next ones
        if (!(sizes = caseSizes.get(1)).isEmpty()) {
            return new Choice(sizes.get(0), 0);
        }
        // Cases with more than one element, a choice has to be made
        for (int i=2; i<=9; i++) {
            if (!(sizes = caseSizes.get(i)).isEmpty()) {
                Integer caseId = sizes.get(0);
                Integer index = choicesIndex.get(caseId);
                // Choice has not been made yet
                if (index == null) {
                    choicesIndex.put(caseId, 0);
                    choices.push(caseId);
                    index = 0;
                }
                return new Choice(caseId, index);
            }
        }
        return null;
    }

    public void invalidChoice(Choice choice) {
        Integer caseId = choices.pop();
        if (choice.getCaseId().equals(caseId)) {
            choicesIndex.remove(caseId);
            caseId = choices.peek();
        }
        else {
            choices.push(caseId);
        }
        choicesIndex.put(caseId, choicesIndex.get(caseId) + 1);
    }

    public String toString() {
        return "" + caseSizes + "\n" + this.choicesIndex + "\n" + this.choices;
    }
}
