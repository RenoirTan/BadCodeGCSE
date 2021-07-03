package com.renoirtan.badcodegsce;

import java.util.Arrays;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        String task;
        try {
            task = args[0];
        } catch (Exception e) {
            System.out.println("Please provide a task you want to run.");
            return;
        }
        String[] taskArgs = Arrays.copyOfRange(args, 1, args.length + 1);
        switch (task) {
            case "musicquiz":
                com.renoirtan.badcodegsce.musicquiz.App.main(taskArgs);
                break;
            default:
                System.out.println("Unknown task: " + task);
                break;
        }
    }
}
