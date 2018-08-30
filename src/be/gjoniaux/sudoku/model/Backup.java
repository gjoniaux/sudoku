package be.gjoniaux.sudoku.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Backup {
    private List<Case> cases;
    private List<CaseGroup> groups;
    private Map<Integer, List<Integer>> caseSizes;

    public Backup(List<Case> cases, List<CaseGroup> groups, Map<Integer, List<Integer>> caseSizes, Map<Integer, Integer> choicesIndex) {
        this.cases = new ArrayList<Case>();
        cases.stream().forEach(c -> this.cases.add(c.clone()));
        this.groups = new ArrayList<CaseGroup>();
        groups.stream().forEach(g -> this.groups.add(g.clone()));
        this.caseSizes = new HashMap<Integer, List<Integer>>();
        caseSizes.entrySet().stream().forEach(cs -> this.caseSizes.put(cs.getKey(), new ArrayList<Integer>(cs.getValue())));
    }

    public List<Case> getCases() {
        return cases;
    }

    public List<CaseGroup> getGroups() {
        return groups;
    }

    public Map<Integer, List<Integer>> getCaseSizes() {
        return caseSizes;
    }

    public String toString() {
        return "";
    }
}
