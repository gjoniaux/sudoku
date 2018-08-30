package be.gjoniaux.sudoku.processor;

import be.gjoniaux.sudoku.model.Case;

import java.util.ArrayList;
import java.util.List;

public class CaseProcessor {
    private List<Case> cases;
    private Integer lastSize;

    public CaseProcessor() {
        this.cases = new ArrayList<Case>();
    }

    public void initialize() {
        this.cases.clear();
        for (int i = 0; i < 81; i++) {
            Case caze = new Case(i);
            caze.initialize();
            cases.add(caze);
        }
    }

    public void initialize(List<Case> cases) {
        this.cases = cases;
    }

    public Integer getNextPossibleNumber(Integer caseId, Integer index) {
        return cases.get(caseId).getNextPossibleNumber(index);
    }

    public boolean fixNumber(Integer caseId, Integer number) {
        Case caze = this.cases.get(caseId);
        this.lastSize = caze.getPossibleNumbersSize();
        return caze.setCorrectNumber(number);
    }

    public boolean removeNumber(Integer caseId, Integer number) {
        Case caze = this.cases.get(caseId);
        this.lastSize = caze.getPossibleNumbersSize();
        return caze.removeNumber(number);
    }

    public Integer getLastSize() {
        return lastSize;
    }

    public List<Case> getCases() {
        return cases;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 81; i++) {
            builder.append(cases.get(i)).append(" ");
            if (i % 9 == 8) {
                builder.append("\n");
            }
        }
        return builder.toString();
    }
}
