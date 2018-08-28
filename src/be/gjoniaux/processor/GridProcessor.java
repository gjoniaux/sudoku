package be.gjoniaux.processor;

import be.gjoniaux.exception.BadChoiceException;
import be.gjoniaux.exception.BadGridException;
import be.gjoniaux.exception.NoMorePossibilityException;
import be.gjoniaux.model.Choice;

public class GridProcessor {
    private CaseProcessor caseProcessor;
    private CaseGroupProcessor caseGroupProcessor;
    private ChoiceProcessor choiceProcessor;
    private Integer[][] grid;

    public GridProcessor(Integer[][] grid) {
        this.caseProcessor = new CaseProcessor();
        this.caseGroupProcessor = new CaseGroupProcessor();
        this.choiceProcessor = new ChoiceProcessor();
        this.grid = grid;
    }

    public void initialize() {
        this.caseProcessor.initialize();
        this.caseGroupProcessor.initialize();
        this.choiceProcessor.initialize();
    }

    public void process() {
        // Initialize grid with input
        try {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if (grid[i][j] != 0) {
                        fixNumber(i * 9 + j, this.grid[i][j]);
                    }
                }
            }
        }
        catch (BadChoiceException e) {
            System.err.println(e);
            throw new BadGridException("This grid is impossible to start!");
        }

        // Choice has to be made
        Choice choice = this.choiceProcessor.findNextCase();
        while (choice != null) {
            try {
                fixNumber(choice.getCaseId(), caseProcessor.getNextPossibleNumber(choice.getCaseId(), choice.getIndex()));
                choice = this.choiceProcessor.findNextCase();
            }
            catch (BadChoiceException e) {
                this.initialize();
                this.choiceProcessor.invalidChoice(choice);
                this.process();
                choice = null;
            }
        }
    }

    private void fixNumber(Integer caseId, Integer number) {
        // Fix number in case
        boolean updated = this.caseProcessor.fixNumber(caseId, number);
        if (updated) {
            this.choiceProcessor.fixNumber(caseId, this.caseProcessor.getLastSize());
            // Fix case in case groups for correct number
            this.caseGroupProcessor.fixNumber(caseId, number);
            // Remove case in case groups for incorrect numbers
            this.caseGroupProcessor.removeNumber(caseId, number);
            // Remove incorrect numbers from cases
            for (Integer lastRemovedCase : this.caseGroupProcessor.getLastRemovedCases()) {
                updated = this.caseProcessor.removeNumber(lastRemovedCase, number);
                if (updated) {
                    this.choiceProcessor.removeNumber(lastRemovedCase, this.caseProcessor.getLastSize());
                }
            }
        }
    }

    public String toString() {
        return caseProcessor + "\n" + caseGroupProcessor + "\n" + choiceProcessor;
    }
}
