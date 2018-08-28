package be.gjoniaux.model;

public class Choice {
    private Integer caseId;
    private Integer index;

    public Choice(Integer caseId, Integer index) {
        this.caseId = caseId;
        this.index = index;
    }

    public Integer getCaseId() {
        return caseId;
    }

    public Integer getIndex() {
        return index;
    }

    public void updateIndex() {
        index++;
    }

    public String toString() {
        return "Case " + caseId + " - Index " + index;
    }
}
