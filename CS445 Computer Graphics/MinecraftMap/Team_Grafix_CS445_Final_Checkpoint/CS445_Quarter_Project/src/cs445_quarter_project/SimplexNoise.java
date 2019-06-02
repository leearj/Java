/*******************************************************************************
*    file: SimplexNoise.java
*    author: Amanda Garcia, John Abdou, Jaeseung Lee, John Vincent Canalita 
*    class: CS 445 – Computer Graphics
*
*    assignment: Final Checkpoint for Quarter Project
*    date last modified: 5/15/2018
*
*    purpose: This class is used to generate a number for the max height of 
*    each column in the chunk. Used to create noise in the terrain
*    
*******************************************************************************/ 

package cs445_quarter_project;

import java.util.Random;

public class SimplexNoise {

    SimplexNoise_octave[] octaves;
    double[] frequencys;
    double[] amplitudes;

    int largestFeature;
    double persistence;
    int seed;
    
    // method: SimplexNoise
    // purpose: constructor used to initialize largest feature, persistance, and seed
    public SimplexNoise(int largestFeature,double persistence, int seed){
        this.largestFeature=largestFeature;
        this.persistence=persistence;
        this.seed=seed;

        //recieves a number (eg 128) and calculates what power of 2 it is (eg 2^7)
        int numberOfOctaves=(int)Math.ceil(Math.log10(largestFeature)/Math.log10(2));

        octaves=new SimplexNoise_octave[numberOfOctaves];
        frequencys=new double[numberOfOctaves];
        amplitudes=new double[numberOfOctaves];

        Random rnd=new Random(seed);

        for(int i=0;i<numberOfOctaves;i++){
            octaves[i]=new SimplexNoise_octave(rnd.nextInt());

            frequencys[i] = Math.pow(2,i);
            amplitudes[i] = Math.pow(persistence,octaves.length-i);

        }

    }

    // method: getNoise
    // purpose: used to get the noise using x and y
    public double getNoise(int x, int y){

        double result=0;

        for(int i=0;i<octaves.length;i++){
          //double frequency = Math.pow(2,i);
          //double amplitude = Math.pow(persistence,octaves.length-i);

          result=result+octaves[i].noise(x/frequencys[i], y/frequencys[i])* amplitudes[i];
        }


        return result;

    }
    
    // method: getNoise
    // purpose: used to get the noise using x, y, and z
    public double getNoise(int x,int y, int z){

        double result=0;

        for(int i=0;i<octaves.length;i++){
          double frequency = Math.pow(2,i);
          double amplitude = Math.pow(persistence,octaves.length-i);

          result=result+octaves[i].noise(x/frequency, y/frequency,z/frequency)* amplitude;
        }


        return result;

    }
} 
