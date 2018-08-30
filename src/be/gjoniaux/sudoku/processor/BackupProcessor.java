package be.gjoniaux.sudoku.processor;

import be.gjoniaux.sudoku.model.Backup;

import java.util.HashMap;
import java.util.Map;

public class BackupProcessor {
    private Map<Integer, Backup> backups;

    public BackupProcessor() {
        this.backups = new HashMap<Integer, Backup>();
    }

    public void putBackup(Integer caseId, Backup backup) {
        backups.put(caseId, backup);
    }

    public Backup getBackup(Integer caseId) {
        return backups.get(caseId);
    }

    @Override
    public String toString() {
        return "" + backups;
    }
}
