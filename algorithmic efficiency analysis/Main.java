import javafx.scene.paint.Stop;
import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;
import sun.security.util.ArrayUtil;

import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;



class Main {
    //tries
   static double[] insertionRandom=new double[10];
   static double[] insertionSorted=new double[10];
   static double[] insertionReverse=new double[10];
   static double[] mergeRandom=new double[10];
    static double[] mergeSorted=new double[10];
    static double[] mergeReverse=new double[10];
    static double[] pigeonRandom=new double[10];
    static double[] pigeonSorted=new double[10];
    static double[] pigeonReverse=new double[10];
    static double[] countRandom=new double[10];
    static double[] countSorted=new double[10];
    static double[] countReverse=new double[10];

    public static int isRange(int s){
        int[] Ranges={512,1024,2048,4096,8192,16384,32768,65536,131072,251281};
        for (int i=0;i<10;i++){
            if(s==Ranges[i])
                return s;
        }
        return -1;
    }

    public static void average(double[] timesList){
        for(int i=0;i<10;i++){
            timesList[i]=timesList[i]/10;
        }
    }

    public static void main(String args[]) throws IOException {


        Sorts sort=new Sorts();
        int index=0;
        try {

            File csvFile = new File(args[0]);
            BufferedReader br = new BufferedReader(new FileReader(csvFile));
            String line = "";
            StringTokenizer st = null;

                ArrayList<Integer> data= new ArrayList<Integer>();
                int lineNumber = 0;

            while ((line = br.readLine()) != null && lineNumber < 251281+1) {
                String[] arr = line.split(",");
                if (lineNumber != 0) {
                    data.add(Integer.parseInt(arr[7]));
                    if (isRange(lineNumber) > 0) {
                        for (int k = 0; k < 10; k++) {

                            ArrayList<Integer> insertion = new ArrayList<>(data);
                            ArrayList<Integer> merge = new ArrayList<>(data);
                            ArrayList<Integer> pigeon = new ArrayList<>(data);
                            ArrayList<Integer> count = new ArrayList<>(data);
                            //insertion
                            StopWatch iR = new StopWatch();
                            sort.InsertionSort(insertion);
                            insertionRandom[index] += iR.elapsedTime();

                            StopWatch iS = new StopWatch();
                            sort.InsertionSort(insertion);
                            insertionSorted[index] += iS.elapsedTime();

                            Collections.reverse(insertion);
                            StopWatch iRS = new StopWatch();
                            sort.InsertionSort(insertion);
                            insertionReverse[index] += iRS.elapsedTime();


                            //merge
                            StopWatch mR = new StopWatch();
                            sort.mergeSort(0, data.size() - 1, merge);
                            mergeRandom[index] += mR.elapsedTime();

                            StopWatch mS = new StopWatch();
                            sort.mergeSort(0, data.size() - 1, merge);
                            mergeSorted[index] += mS.elapsedTime();

                            Collections.reverse(merge);
                            StopWatch mRS = new StopWatch();
                            sort.mergeSort(0, data.size() - 1, merge);
                            mergeReverse[index] += mRS.elapsedTime();

                            //pigeon
                            StopWatch pR = new StopWatch();
                            sort.pigeonhole_sort(pigeon, data.size());
                            pigeonRandom[index] += pR.elapsedTime();

                            StopWatch pS = new StopWatch();
                            sort.pigeonhole_sort(pigeon, data.size());
                            pigeonSorted[index] += pS.elapsedTime();

                            Collections.reverse(pigeon);
                            StopWatch pRS = new StopWatch();
                            sort.pigeonhole_sort(pigeon, data.size());
                            pigeonReverse[index] += pRS.elapsedTime();

                            //count
                            StopWatch cR = new StopWatch();
                            sort.counting_sort(count);
                            countRandom[index] += cR.elapsedTime();

                            StopWatch cS = new StopWatch();
                            sort.counting_sort(count);
                            countSorted[index] += cS.elapsedTime();

                            Collections.reverse(count);
                            StopWatch cRS = new StopWatch();
                            sort.counting_sort(count);
                            countReverse[index] += cRS.elapsedTime();
                        }
                        index++;
                    }
                }
                lineNumber++;
            }

            average(insertionRandom);
            average(mergeRandom);
            average(pigeonRandom);
            average(countRandom);
            average(insertionSorted);
            average(mergeSorted);
            average(pigeonSorted);
            average(countSorted);
            average(insertionReverse);
            average(mergeReverse);
            average(pigeonReverse);
            average(countReverse);

        }catch(IOException ex) {
            ex.printStackTrace();
        }


        // X axis data
        int[] inputAxis = {512, 1024, 2048, 4096, 8192, 16384, 32768, 65536, 131072, 251282};

        // Create sample data for linear runtime
        double[][] yAxis = new double[4][10];
        yAxis[0] = insertionRandom; //new double[]{512, 1024, 2048, 4096, 8192, 16384, 32768, 65536, 131072, 251282};
        yAxis[1] = mergeRandom; //new double[]{300, 800, 1800, 3000, 7000, 15000, 31000, 64000, 121000, 231000};
        yAxis[2] =pigeonRandom;
        yAxis[3]=countRandom;


        // Create sample data for linear runtime
        double[][] yAxis2 = new double[4][10];
        yAxis2[0] = insertionSorted; //new double[]{512, 1024, 2048, 4096, 8192, 16384, 32768, 65536, 131072, 251282};
        yAxis2[1] = mergeSorted; //new double[]{300, 800, 1800, 3000, 7000, 15000, 31000, 64000, 121000, 231000};
        yAxis2[2] =pigeonSorted;
        yAxis2[3]=countSorted;


        // Create sample data for linear runtime
        double[][] yAxis3 = new double[4][10];
        yAxis3[0] = insertionReverse; //new double[]{512, 1024, 2048, 4096, 8192, 16384, 32768, 65536, 131072, 251282};
        yAxis3[1] = mergeReverse; //new double[]{300, 800, 1800, 3000, 7000, 15000, 31000, 64000, 121000, 231000};
        yAxis3[2] =pigeonReverse;
        yAxis3[3]=countReverse;


        // Save the char as .png and show it
        showAndSaveChart("Random Data", inputAxis, yAxis);
        showAndSaveChart("Sorted Data",inputAxis,yAxis2);
        showAndSaveChart("Reverse Sorted Data",inputAxis,yAxis3);

    }



    public static void showAndSaveChart(String title, int[] xAxis, double[][] yAxis) throws IOException {

        // Create Chart
        XYChart chart = new XYChartBuilder().width(800).height(600).title(title)
                .yAxisTitle("Time in Milliseconds").xAxisTitle("Input Size").build();

        // Convert x axis to double[]
        double[] doubleX = Arrays.stream(xAxis).asDoubleStream().toArray();

        // Customize Chart
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);

        // Add a plot for a sorting algorithm
        chart.addSeries("Insertion sort", doubleX, yAxis[0]);
        chart.addSeries("Merge sort", doubleX, yAxis[1]);
        chart.addSeries("Pigeon sort", doubleX, yAxis[2]);
        chart.addSeries("Count sort", doubleX, yAxis[3]);

        // Save the chart as PNG
        BitmapEncoder.saveBitmap(chart, title + ".png", BitmapEncoder.BitmapFormat.PNG);

        // Show the chart
        new SwingWrapper(chart).displayChart();
    }

}
