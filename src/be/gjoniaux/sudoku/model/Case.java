package be.gjoniaux.sudoku.model;

import be.gjoniaux.sudoku.exception.BadChoiceException;
import be.gjoniaux.sudoku.exception.NoMorePossibilityException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Case implements Cloneable {
    private Integer id;
    private Integer correctNumber;
    private List<Integer> numbers;

    public Case(Integer id) {
        this.id = id;
        this.correctNumber = 0;
        this.numbers = new ArrayList<Integer>();
    }

    public Case(Integer id, Integer correctNumber, List<Integer> numbers) {
        this.id = id;
        this.correctNumber = correctNumber;
        this.numbers = new ArrayList<Integer>(numbers);
    }

    public void initialize(CaseGroup... groupArray) {
        this.correctNumber = 0;
        this.numbers.clear();
        this.numbers.addAll(Arrays.asList(1,2,3,4,5,6,7,8,9));
    }

    public Integer getId() {
        return id;
    }

    public Integer getPossibleNumbersSize() {
        return numbers.size();
    }

    public Integer getCorrectNumber() {
        return correctNumber;
    }

    public Integer getNextPossibleNumber(Integer index) {
        if (numbers.size() <= index) {
            throw new NoMorePossibilityException("No next possible number for this case " + this);
        }
        return numbers.get(index);
    }

    public boolean setCorrectNumber(Integer correctNumber) {
        // Setting same value is noop
        if (this.getCorrectNumber() == correctNumber) {
            return false;
        }
        // Setting another value that value already fixed is a bad choice
        if (this.isFixed()) {
            throw new BadChoiceException("Impossible to set number [" + correctNumber + "] for the fixed case " + this + " with correct number " + this.correctNumber);
        }
        // Setting a value when no value already fixed
        this.correctNumber = correctNumber;
        this.numbers = Arrays.asList(correctNumber);
        return true;
    }

    public boolean removeNumber(Integer badNumber) {
        // Remove number already correct for this case is a bad choice
        if (this.getCorrectNumber() == badNumber) {
            throw new BadChoiceException("Impossible to remove correct number " + this.correctNumber + " of the fixed case " + this);
        }
        // Remove the bad number
        return this.numbers.remove(badNumber);
    }

    public boolean isFixed() {
        return getCorrectNumber() != 0;
    }

    @Override
    public Case clone() {
        return new Case(id, correctNumber, numbers);
    }

    public String toString() {
        return "" + getCorrectNumber() + " (" + numbers.size() + ")";
    }
}
