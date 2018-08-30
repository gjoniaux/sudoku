package be.gjoniaux.sudoku.model;

public class Choice implements Cloneable {
    private Integer caseId;
    private Integer index;
    private Integer size;
    private boolean choice;

    public Choice(Integer caseId, Integer index, Integer size) {
        this(caseId, index, size, false);
    }

    public Choice(Integer caseId, Integer index, Integer size, boolean choice) {
        this.caseId = caseId;
        this.index = index;
        this.size = size;
        this.choice = choice;
    }

    public Integer getCaseId() {
        return caseId;
    }

    public Integer getIndex() {
        return index;
    }

    public Integer getSize() {
        return size;
    }

    public boolean isChoice() {
        return choice;
    }

    public String toString() {
        return "Case " + caseId + " - Index " + index + " - Size " + size;
    }
}
