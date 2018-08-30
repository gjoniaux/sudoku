package be.gjoniaux.sudoku.processor;

import be.gjoniaux.sudoku.exception.BadChoiceException;
import be.gjoniaux.sudoku.exception.BadGridException;
import be.gjoniaux.sudoku.exception.NoMorePossibilityException;
import be.gjoniaux.sudoku.model.Backup;
import be.gjoniaux.sudoku.model.Choice;

public class GridProcessor {
    private CaseProcessor caseProcessor;
    private CaseGroupProcessor caseGroupProcessor;
    private ChoiceProcessor choiceProcessor;
    private BackupProcessor backupProcessor;
    private Integer[][] grid;

    public GridProcessor(Integer[][] grid) {
        this.caseProcessor = new CaseProcessor();
        this.caseGroupProcessor = new CaseGroupProcessor();
        this.choiceProcessor = new ChoiceProcessor();
        this.backupProcessor = new BackupProcessor();
        this.grid = grid;
    }

    public void initialize() {
        this.caseProcessor.initialize();
        this.caseGroupProcessor.initialize();
        this.choiceProcessor.initialize();
    }

    public void initialize(Backup backup) {
        this.caseProcessor.initialize(backup.getCases());
        this.caseGroupProcessor.initialize(backup.getGroups());
        this.choiceProcessor.initialize(backup.getCaseSizes());
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
                // Backup when choice has been made
                if (choice.isChoice()) {
                    this.backupProcessor.putBackup(choice.getCaseId(), new Backup(this.caseProcessor.getCases(), this.caseGroupProcessor.getCaseGroups(), this.choiceProcessor.getCaseSizes(), this.choiceProcessor.getChoicesIndex()));
                    //System.out.println(this.backupProcessor);
                }
                fixNumber(choice.getCaseId(), caseProcessor.getNextPossibleNumber(choice.getCaseId(), choice.getIndex()));
                choice = this.choiceProcessor.findNextCase();
            }
            catch (BadChoiceException e) {
                this.initialize(this.backupProcessor.getBackup(this.choiceProcessor.invalidChoice(choice)));
                choice = this.choiceProcessor.findNextCase();
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
