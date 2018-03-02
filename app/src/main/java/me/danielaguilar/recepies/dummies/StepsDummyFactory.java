package me.danielaguilar.recepies.dummies;

import java.util.ArrayList;
import java.util.Random;

import me.danielaguilar.recepies.models.Step;

/**
 * Created by danielaguilar on 26-02-18.
 */

public class StepsDummyFactory {

    public static Step getStep(){
        Random randomGenerator = new Random();
        int randomInt = randomGenerator.nextInt(100);
        Step step   = new Step();
        step.setDescription("Description"+randomInt);
        step.setShortDescription("Short Description"+randomInt);
        step.setVideoURL("https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffdbac_9-finished-product-cheesecake/9-finished-product-cheesecake.mp4");
        return step;
    }

    public static ArrayList<Step> getSteps(final int quantity){
        ArrayList<Step> steps = new ArrayList<>(quantity);
        for (int i = 0; i < quantity; i++) {
            steps.add(getStep());
        }

        return steps;
    }
}
