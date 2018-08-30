package be.gjoniaux.sudoku;

import be.gjoniaux.sudoku.model.Choice;
import be.gjoniaux.sudoku.processor.GridProcessor;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static final Integer[][] GRID =
            {
                    {8,0,0, 0,0,0, 0,0,0},
                    {0,0,3, 6,0,0, 0,0,0},
                    {0,7,0, 0,9,0, 2,0,0},

                    {0,5,0, 0,0,7, 0,0,0},
                    {0,0,0, 0,4,5, 7,0,0},
                    {0,0,0, 1,0,0, 0,3,0},

                    {0,0,1, 0,0,0, 0,6,8},
                    {0,0,8, 5,0,0, 0,1,0},
                    {0,9,0, 0,0,0, 4,0,0}};

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        GridProcessor gridProcessor = new GridProcessor(GRID);
        gridProcessor.initialize();
        gridProcessor.process();

        System.out.println("Perfs : " + (System.currentTimeMillis() - start) + " ms\n");
        System.out.println(gridProcessor);
    }
}
